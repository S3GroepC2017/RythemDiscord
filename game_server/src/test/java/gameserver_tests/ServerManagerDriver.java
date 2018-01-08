package gameserver_tests;

import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.IServerManager;
import com.csharp.sharedclasses.fontyspublisher.IRemotePropertyListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class ServerManagerDriver implements IRemotePropertyListener
{
    public IServerManager serverManager;

    public PropertyChangeEvent lastPropertyChangeEvent;
    public String resultcreateGame = null;

    public ServerManagerDriver(IServerGame serverGame) throws RemoteException
    {
        this.serverManager = serverManager;

        //        serverGame.subscribe(this, "players");
        //        serverGame.subscribe(this, "noteListIndex");
        serverGame.subscribe(this, "dtoProperty");
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

    String createGame() throws RemoteException
    {
        resultcreateGame = serverManager.createGame();
        return resultcreateGame;
    }
}
