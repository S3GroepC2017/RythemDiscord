package com.csharp.sharedclasses;

import java.io.Serializable;
import java.util.Arrays;

public class Player implements Serializable
{
    private String name;
    private char[] nodeList = null;

    public String getName()
    {
        return name;
    }

    /**
     * Returns the node at the specified index in the nodeList
     * @param position 0 based index of the desired character
     * @return the character based on the parameter, if none exists or the index is outside the array '\u0000' is returned,
     *          ' ' is used to represent an empty space in the array.
     */
    public char getNode(int position)
    {
        if (nodeList == null || position >= nodeList.length || position < 0)
        {
            return '\u0000';
        }

        return nodeList[position];
    }

    public void setNodeList(char[] nodeList)
    {
        this.nodeList = nodeList;
    }

    public char[] getNodeList()
    {
        return nodeList;
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

    @Override
    public String toString()
    {
        String str = "";
        if (name != null)
        {
            str += "Player: " + name;
        }

        if (nodeList != null)
        {
            str += " Nodes:";
            for (char c : nodeList)
            {
                str += " ";
                str += c;
            }
        }

        return str;
    }
}
