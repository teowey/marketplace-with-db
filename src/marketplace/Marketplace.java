/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketplace;

import bank.RejectedException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ruthe
 */
public class Marketplace extends UnicastRemoteObject implements MarketplaceInterface{
    private String name;
    List<ClientInterface> clients = new ArrayList<ClientInterface>();
    List<SellItem> items = new ArrayList<SellItem>();
    List<WishItem> wishItems = new ArrayList<WishItem>();
    
    public Marketplace(String name) throws RemoteException{
        super();
        this.name= name;
    }

    @Override
    public void register(ClientInterface client) throws RemoteException {
        if(!clients.contains(client))
            clients.add(client);
    }

    @Override
    public void ungregister(ClientInterface client) throws RemoteException {
        if(clients.contains(client))
            clients.remove(client);
    }

    @Override
    public void sell(ItemInterface item, ClientInterface client) throws RemoteException {
       
        if(clients.contains(client)){
            try{
                WishItem actItem = getWishItem(item);
                
                if(actItem != null){
                    actItem.getwisher().notifyWish(item);
                }
            
            items.add(new SellItem(item,client));
            }catch(RemoteException e){
                System.out.println(e);
                    }
        }else
            System.out.println("Client is not registered in the marketplace. Please register.");
    }

    @Override
    public void buy(ItemInterface item, ClientInterface client) throws RemoteException {
        SellItem  actItem = this.getSellItem(item, true);
        try{
            if(actItem != null){
                client.getBankAccount().withdraw(actItem.getPrice());
                ClientInterface seller = actItem.getseller();
                seller.notifySold(actItem);
                seller.getBankAccount().deposit(actItem.getPrice()); 
                items.remove(actItem);
            }
        }catch(RemoteException |RejectedException e){
            System.out.println(e);
        }
      
    }

    @Override
    public void wishtobuy(ClientInterface client, ItemInterface item) throws RemoteException {
        SellItem actItem = this.getSellItem(item, false);
        try{
            if(actItem != null){
                client.notifyWish(actItem);
            }else{
                wishItems.add(new WishItem(item, client));
            }
        }catch (RemoteException e){
            System.out.println(e);
        }
    }

    @Override
    public ArrayList<ItemInterface> getAvailable() throws RemoteException {
        ArrayList<ItemInterface> available = new ArrayList<ItemInterface>();
        for(int i = 0; i < items.size(); i++){
            try{
                available.add( new Item(items.get(i)));
            }catch(RemoteException e){
                System.out.println();
            }
        }
        return available;
    }
    private WishItem getWishItem(ItemInterface item){
        try{
            for(int i = 0; i< wishItems.size(); i++){
                WishItem actItem = wishItems.get(i);
                if(item.getName().equals(actItem.getName()) && (item.getPrice() <= actItem.getPrice())){
                    return actItem;
                }
            }
        }catch (RemoteException e){
            System.out.println(e);
        }
        return null;
        
    }
    private SellItem getSellItem(ItemInterface item, boolean exactPrice){
        try{
            for(int i = 0; i < items.size(); i++){
                SellItem actItem = items.get(i);
                if(item.getName().equals(actItem.getName())){
                    if(exactPrice && item.getPrice() == actItem.getPrice()){
                        return actItem;
                    }else if (exactPrice && item.getPrice() >= actItem.getPrice()){
                        return actItem;
                    }
                }
            }
        }catch (RemoteException e){
            System.out.println(e);
        }
        return null;
    }
    
}
