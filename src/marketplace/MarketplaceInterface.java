/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Ruthe
 */
public interface MarketplaceInterface extends Remote{
    
        public void register(ClientInterface client) throws RemoteException;
        public void ungregister(ClientInterface client) throws RemoteException;
        public void sell (ItemInterface item, ClientInterface client ) throws RemoteException;
        public void buy ( ItemInterface item, ClientInterface client) throws RemoteException;
        public void wishtobuy (ClientInterface client, ItemInterface item) throws RemoteException;
        ArrayList<ItemInterface> getAvailable () throws RemoteException;
        

}
