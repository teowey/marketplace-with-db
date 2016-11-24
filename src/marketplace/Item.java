/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketplace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Ruthe
 */
public class Item extends UnicastRemoteObject implements ItemInterface{
    public String name;
    public float price;
    
    public Item (String name, float price)throws RemoteException{
        this.name = name;
        this.price = price;
    }
    public Item(ItemInterface item)throws RemoteException{
        this.name= item.getName();
        this.price= item.getPrice();
    }
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name= name;
    }
    public float getPrice(){
        return price;
    }
    public void setPrice(float price){
        this.price= price;
    }

    
    
}
