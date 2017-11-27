package com.csharp.game.server;

import java.io.Serializable;

public class Player implements Serializable
{
    private String name;
    private char[] nodeList;

    public String getName()
    {
        return name;
    }

    /**
     * Returns the node at the specified index in the nodeList
     * @param position 0 based index of the desired character
     * @return the character based on the parameter, if none exists or the index is outside the array ' ' is returned
     */
    public char getNode(int position)
    {
        if (position >= nodeList.length || position < 0)
        {
            return ' ';
        }
        return nodeList[position];
    }

    public void setNodeList(char[] nodeList)
    {
        this.nodeList = nodeList;
    }

    public Player(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return getName().equals(player.getName());
    }

    @Override
    public int hashCode()
    {
        return getName().hashCode();
    }
}
