/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketplace;

import bank.Bank;
import bank.BankImpl;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author Ruthe
 */
public class Server {
    
    public static void main (String [] args){
    try{
        MarketplaceInterface market = new Marketplace("Marketplace");
        Bank bank = new BankImpl("Bank");
        
        try{
            LocateRegistry.getRegistry(1099).list();
        }
        catch(RemoteException e){
            LocateRegistry.createRegistry(1099);
        }
        Naming.rebind("Bank", bank);
        System.out.println("Bank is connected.");
        Naming.rebind("Marketplace", market);
        System.out.println("Marketplace is connected. ");
    }
    catch(Exception e){
        System.out.println("Connection Error "+ e);
    }
    }
}
