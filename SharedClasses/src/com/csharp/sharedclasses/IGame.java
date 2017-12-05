package com.csharp.sharedclasses;

import com.csharp.sharedclasses.fontyspublisher.IPropertyListener;
import com.csharp.sharedclasses.fontyspublisher.IRemotePropertyListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IGame extends IRemotePropertyListener {
    //Returns the  nodes of this current sequence.
    List<Player> getNodes();

    //Checks if the pressed key was correct.
    KeyPressedResult checkKeyPressed(char keyPressed);

    int getNodeListPosition();
}
