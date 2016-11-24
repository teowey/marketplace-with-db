/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketplace;

import java.rmi.RemoteException;

/**
 *
 * @author Ruthe
 */
public class WishItem extends Item{
    private ClientInterface wisher;
    
    public WishItem(String name, float price, ClientInterface wisher) throws RemoteException{
        super(name, price);
        this.wisher = wisher;
    }
    public WishItem (ItemInterface item, ClientInterface wisher) throws RemoteException{
        super(item);
        this.wisher = wisher;
    }
    public ClientInterface getwisher(){
        return wisher;
    }
    public void setWisher(ClientInterface wisher){
        this.wisher = wisher;
    }
}
