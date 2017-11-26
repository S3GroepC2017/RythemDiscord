package login_server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILogin extends Remote {

    Boolean checkLogin(String username, String hashedpassword) throws RemoteException;

}
