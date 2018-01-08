package login_server;

import com.csharp.sharedclasses.ILogin;
import com.csharp.sharedclasses.fontyspublisher.IRemotePropertyListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class LoginServerDriver implements IRemotePropertyListener
{
    ILogin loginServer;

    PropertyChangeEvent lastPropertyChangeEvent;

    boolean resultCheckLogin;

    public LoginServerDriver(ILogin loginServer)
    {
        this.loginServer = loginServer;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        lastPropertyChangeEvent = evt;
        //        if(evt.getPropertyName().equals("Player"))
        //        {
        //            players = (List<Player>)evt.getNewValue();
        //        }
        //        else if(evt.getPropertyName().equals("noteListIndex"))
        //        {
        //            nodeListIndex = (int) evt.getNewValue();
        //        }
        //        else if(evt.getPropertyName().equals("lastKeyPressResult"))
        //        {
        //            keyPressedResult = (KeyPressedResult) evt.getNewValue();
        //        }
    }

    boolean checkLogin(String username, String hashedPassword) throws RemoteException
    {
        resultCheckLogin = loginServer.checkLogin(username, hashedPassword);
        return resultCheckLogin;
    }
}
