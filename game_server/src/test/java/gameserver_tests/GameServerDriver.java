package gameserver_tests;

import com.csharp.game.server.GameServerLauncher;
import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import com.csharp.sharedclasses.fontyspublisher.IPropertyListener;
import com.csharp.sharedclasses.fontyspublisher.IRemotePropertyListener;

import java.awt.image.Kernel;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class GameServerDriver implements IRemotePropertyListener
{
    public IServerGame serverGame;

    public boolean resultJoinPlayer;
    public int resultGetPlayerId;
    public PropertyChangeEvent lastPropertyChangeEvent;

    public GameServerDriver(IServerGame serverGame)
    {
        this.serverGame = serverGame;
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

    void keyPressed(KeyPressedResult result) throws RemoteException
    {
        serverGame.keyPressed(result);
    }

    boolean joinPlayer(Player player) throws RemoteException
    {
        resultJoinPlayer = serverGame.joinPlayer(player);
        return resultJoinPlayer;
    }
    int getGameId() throws RemoteException
    {
        resultGetPlayerId = serverGame.getGameId();
        return resultGetPlayerId;
    }
    void subscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException
    {
        serverGame.subscribe(listener, propertyName);
    }
    void startGame(Player player) throws RemoteException
    {
        serverGame.startGame(player);
    }
    /**
     * Used to unsubscirbe from properties of the publisher
     * @param listener The listener that should be unsubscribed
     * @param propertyName The target property to unsubscribe
     * @throws RemoteException
     */
    void unsubscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException
    {
        serverGame.unsubscribe(listener, propertyName);
    }

    /**
     * Before calling this the client should have unsubscribed from all active subscriptions.
     * Will remove the player from the game.
     * @param player
     */
    void leave(Player player) throws RemoteException
    {
        serverGame.leave(player);
    }
}
