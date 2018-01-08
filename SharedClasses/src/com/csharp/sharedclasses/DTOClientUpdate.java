package com.csharp.sharedclasses;

import java.io.Serializable;
import java.util.List;

public class DTOClientUpdate implements Serializable
{
    private int newNodeListPosition;
    private KeyPressedResult newKeyPressResult;
    private List<Player> newPlayerList;

    public int getNewNodeListPosition()
    {
        return newNodeListPosition;
    }

    public KeyPressedResult getNewKeyPressResult()
    {
        return newKeyPressResult;
    }

    public List<Player> getNewPlayerList()
    {
        return newPlayerList;
    }

    public DTOClientUpdate(int newNodeListPosition, KeyPressedResult newKeyPressResult)
    {
        this(newNodeListPosition, newKeyPressResult, null);
    }

    public DTOClientUpdate(int newNodeListPosition, KeyPressedResult newKeyPressResult, List<Player> newPlayerList)
    {
        this.newNodeListPosition = newNodeListPosition;
        this.newKeyPressResult = newKeyPressResult;
        this.newPlayerList = newPlayerList;
    }
}
