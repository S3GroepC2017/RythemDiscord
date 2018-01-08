package gameserver_tests;

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
    public List<Player> players = null;
    public int nodeListIndex = -1;
    public KeyPressedResult keyPressedResult = null;

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        if(evt.getPropertyName().equals("Player"))
        {
            players = (List<Player>)evt.getNewValue();
        }
        else if(evt.getPropertyName().equals("noteListIndex"))
        {
            nodeListIndex = (int) evt.getNewValue();
        }
        else if(evt.getPropertyName().equals("lastKeyPressResult"))
        {
            keyPressedResult = (KeyPressedResult) evt.getNewValue();
        }
    }
}
