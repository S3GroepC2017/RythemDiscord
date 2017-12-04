package com.csharp.game.logic.tests;

import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import com.csharp.sharedclasses.fontyspublisher.IRemotePropertyListener;

public class IServerGameStub implements IServerGame
{
    public KeyPressedResult lastReceivedResultKeyPressResult = KeyPressedResult.NONE;

    @Override
    public void keyPressed(KeyPressedResult result)
    {
        lastReceivedResultKeyPressResult = result;
    }

    @Override
    public boolean joinPlayer(Player player)
    {
        return false;
    }

    @Override
    public int getGameId()
    {
        return 0;
    }

    @Override
    public void subscribe(IRemotePropertyListener listener, String propertyName)
    {

    }

    @Override
    public void startGame(Player player)
    {

    }
}
