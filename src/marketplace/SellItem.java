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
public class SellItem extends Item{
    
    private ClientInterface seller;
    
    public SellItem (String name, float price, ClientInterface seller)throws RemoteException{
        super(name,price);
        this.seller= seller;
    }
    public SellItem(ItemInterface item,ClientInterface seller) throws RemoteException{
        super(item);
        this.seller= seller;
    }
    public ClientInterface getseller(){
        return seller;
    }
    public void setseller(ClientInterface seller){
        this.seller= seller;
    }
}
