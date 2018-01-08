package login_server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RythemDiscord
 *
 * @author Groep C#
 */
public class LoginServerLauncher
{
    private static final int portNumber = 1099;
    private static final String bindingName = "LoginServer";
    private static final String PROTOCOL = "java.rmi.server.hostname";
    private static final String HOST = "192.168.0.100";
    private Registry registry = null;
    private LoginChecker loginChecker;

    public LoginServerLauncher()
    {
        try
        {
            loginChecker = new LoginChecker();
            System.out.println("Server: LoginChecker created");
        }
        catch (RemoteException e)
        {
            System.out.println("Server: cannot create loginchecker \n" + "Server: RemoteException " + e.getMessage());
            e.printStackTrace();
            loginChecker = null;
        }

        try
        {

            System.setProperty(PROTOCOL,HOST);
            System.out.println("java.rmi.server.hostname = " + System.getProperty("java.rmi.server.hostname"));
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("registry:" + registry.toString());
            System.out.println("Server: Registry created on port number " + portNumber);
        }
        catch (RemoteException e)
        {
            System.out.println("Server: Cannot create registry\n" + "Server: RemoteException: " + e.getMessage());
            e.printStackTrace();
            registry = null;
        }

        try
        {
            registry.rebind(bindingName, loginChecker);
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot bind LoginChecker");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }

    }

    public static void main(String[] arg)
    {

        System.out.println("SERVER USING REGISTRY");

        LoginServerLauncher server = new LoginServerLauncher();
    }
}