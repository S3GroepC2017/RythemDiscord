package login_server;

import login_server.LoginChecker;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RythemDiscord
 *
 * @author Groep C#
 */
public class LoginServerLauncher {
    private static final int portNumber = 1099;
    private static final String bindingName = "LoginServer";
    private Registry registry = null;
    private LoginChecker loginChecker;

    public LoginServerLauncher(){
        try{
            loginChecker = new LoginChecker();
            System.out.println("Server: LoginChecker created");
        } catch (RemoteException e) {
            System.out.println("Server: cannot create loginchecker \n"
                    + "Server: RemoteException " + e.getMessage());
            e.printStackTrace();
            loginChecker = null;
        }

        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        } catch (RemoteException e) {
            System.out.println("Server: Cannot create registry\n" +
                    "Server: RemoteException: " +
                    e.getMessage());
            e.printStackTrace();
            registry = null;
        }

        try {
            registry.rebind(bindingName, loginChecker);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind LoginChecker");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }

    }

    public static void main (String[] arg) {

    }
}
