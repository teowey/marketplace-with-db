/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Ruthe
 */
public  interface ItemInterface extends Remote{
    
    public String getName() throws RemoteException;
    
    public void setName(String name) throws RemoteException;
    
    public float getPrice() throws RemoteException;
    
    public void setPrice( float price) throws RemoteException;
}
