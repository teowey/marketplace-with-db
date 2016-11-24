/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketplace;

import bank.Account;
import bank.Bank;
import bank.RejectedException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//To execute a SQL statement on your table, you set up a Statement object. So add this import line to the top of your code:
import java.sql.*;

import java.sql.ResultSet;

import java.io.Console;

/**
 *
 * @author Ruthe
 */
public class Client extends UnicastRemoteObject implements ClientInterface {

    private static Bank bankObj;
    private static MarketplaceInterface marketObj;

    private String clientName;
    private Account bankAccount;
    private Bank bank;

    public Client(String clientName, float amount) throws RemoteException, RejectedException {
        this.clientName = clientName;
        bankAccount = bankObj.newAccount(clientName);
        bankAccount.deposit(amount);
    }

    @Override
    public void notifySold(ItemInterface item) throws RemoteException {
        try {
            System.out.println("To client: " + clientName + " and product: " + item.getName() + " with price: " + item.getPrice());
        } catch (RemoteException e) {
            System.out.println(e);
        }
    }

    @Override
    public void notifyWish(ItemInterface item) throws RemoteException {
        try {
            System.out.println("To client: " + clientName + " the following items are available: " + item.getName() + " price: " + item.getPrice());
        } catch (RemoteException e) {
            System.out.println(e);
        }
    }

    @Override
    public Account getBankAccount() throws RemoteException {
        return bankAccount;
    }

    public void setBankAccount() throws RemoteException {
        this.bankAccount = bankAccount;
    }

    
    
    public static void login() {
        String username;
        String password;
        
        
        Scanner login = new Scanner(System.in);
        
//        System.out.print("Enter the username: ");
//        username = login.next();
//        //login.nextLine();
//        System.out.print("Enter the password: ");
//        password = login.next();
        
        
        
        while(true) {
           
            System.out.print("Enter the username: ");
            username = login.nextLine();
            //login.nextLine();
            System.out.print("Enter the password: ");
            password = login.nextLine();

           if (username.length() == 0 || password.length() == 0) {
               System.out.println("Empty fields detected! Please fill up all fields");
//               System.out.print("Enter the username: ");
//               username = login.next();
//               //login.nextLine();
//               System.out.print("Enter the password: ");
//               password = login.next();
               
            } else {
               if(validateLogin(username, password)) {
                   System.out.println("Correct");
                   break;
               }
               else{
                   System.out.println("Invalid username and/or password.");
                   
//                   System.out.print("Enter the username: ");
//                   username = login.next();
//                   //login.nextLine();
//                   System.out.print("Enter the password: ");
//                   password = login.next();
                   //break;
               }
            }
 
        } 
        //break;
        //System.out.println("User: " + username + " has password: " + password);
    }
    public static boolean validateLogin(String user, String pass) {
        try {
            String host = "jdbc:derby://localhost:1527/homework3";
            String userDB = "test";
            String passDB = "java";
            Connection con = DriverManager.getConnection(host, userDB, passDB);
            PreparedStatement pst = con.prepareStatement("Select * from login where username=? and password=?");
            pst.setString(1, user);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void userInput() throws RejectedException, IOException {
        ArrayList<Client> clients = new ArrayList<Client>();

        int option = 0;
        Scanner in = new Scanner(System.in);
        String clientStr;
        Client client = null;

        while (option < 7) {
            System.out.println("\nWelcome to the marketplace! Choose an option:\n(1) Register\n(2) Unregister\n(3) Sell an item\n"
                    + "(4) Buy an item\n(5) Wish an item\n(6) See all products\n(7) Close");
            option = in.nextInt();
            in.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Name: ");
                    String name = in.nextLine();
                    System.out.println("Amount: ");
                    Float money = in.nextFloat();
                    client = new Client(name, money);
                    clients.add(client);
                    marketObj.register(client);
                    in.nextLine();
                    break;
                case 2:
                    if (clients.size() < 1) {
                        System.out.println("No clients to unregister ");
                        break;
                    }
                    clientStr = clientAsStrings(clients);
                    System.out.println("Choose what client to unregister: " + clientStr + " : ");
                    int unRegisterClientNum = in.nextInt();
                    marketObj.ungregister(clients.get(unRegisterClientNum));
                    clients.remove(unRegisterClientNum);
                    in.nextLine();
                    break;
                case 3:
                    if (clients.size() < 1) {
                        System.out.println("You must register first.");
                        break;
                    }
                    System.out.println("Name of the item: ");
                    String itemName = in.nextLine();
                    System.out.println("Price of the item: ");
                    float itemPrice = in.nextFloat();
                    clientStr = clientAsStrings(clients);
                    System.out.println("What seller is selling the item: " + clientStr + " : ");
                    int sellingClientNum = in.nextInt();
                    marketObj.sell(new Item(itemName, itemPrice), clients.get(sellingClientNum));
                    in.nextLine();
                    break;
                case 4:
                    if (clients.size() < 1) {
                        System.out.println("You must register first.");
                        break;
                    }
                    System.out.println("Name of the item: ");
                    String buyName = in.nextLine();
                    System.out.println(" ");
                    float buyPrice = in.nextFloat();
                    clientStr = clientAsStrings(clients);
                    System.out.println("Vilken kund köper varan: " + clientStr + " : ");
                    int buyingClientNum = in.nextInt();
                    marketObj.buy(new Item(buyName, buyPrice), clients.get(buyingClientNum));
                    in.nextLine();
                    break;
                case 5:
                    if (clients.size() < 1) {
                        System.out.println("Du måste registrera dig först.");
                        break;
                    }
                    System.out.println("Namn på varan att bevaka: ");
                    String wishName = in.nextLine();
                    System.out.println("Max pris på varan: ");
                    float wishPrice = in.nextFloat();
                    clientStr = clientAsStrings(clients);
                    System.out.println("Vilken kund önskar varan: " + clientStr + " : ");
                    int wishingClientNum = in.nextInt();
                    marketObj.wishtobuy(clients.get(wishingClientNum), new Item(wishName, wishPrice));
                    in.nextLine();
                    break;
                case 6:
                    ArrayList<ItemInterface> available = marketObj.getAvailable();
                    if (available.size() < 1) {
                        System.out.println("Finns inga varor.");
                        break;
                    }
                    String availableStr = " ";
                    for (int i = 0; i < available.size(); i++) {
                        availableStr += available.get(i).getName() + " for " + available.get(i).getPrice() + " , ";
                    }
                    System.out.println(availableStr.substring(0, availableStr.length() - 2));
                    break;
                default:
                    break;
            }
        }
    }

    private static String clientAsStrings(ArrayList<Client> clients) {
        String str = "";
        for (int i = 0; i < clients.size(); i++) {
            str += i + " " + clients.get(i).clientName + " , ";

        }
        //System.out.println("String before -2: " + str);
        return str.substring(0, str.length() - 2);
    }

    public static void main(String[] args) {

        try {
            marketObj = (MarketplaceInterface) Naming.lookup("Marketplace");
            System.out.println("Marketplace connected");
            bankObj = (Bank) Naming.lookup("Bank");
            System.out.println("Bank connected");
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
       try {
//
//            String host = "jdbc:derby://localhost:1527/homework3";
//            String user = "test";
//            String pass = "java";
//
//            Connection con = DriverManager.getConnection(host, user, pass);
////            Statement stmt = con.createStatement();
////            String SQL = "SELECT * FROM LOGIN";
////            ResultSet rs = stmt.executeQuery(SQL);
////            
//        } catch (SQLException err) {
//            System.out.println(err.getMessage());
//        }
       
            
            login();
            userInput();
        } catch (RejectedException | IOException e) {
            System.out.println("userInput  " + e);
        }
    }

}
