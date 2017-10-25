package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Lars on 25-9-2017.
 */
public class Game
{
    private char[] nodeList;
    private int nodeListPosition = 0;
    private NodeGenerator nodeGenerator;

    public Game()
    {
        nodeGenerator = new NodeGenerator();
        nodeList = nodeGenerator.generateNode();
    }

    public char[] getNodes()
    {
        if (nodeListPosition == nodeList.length)
        {
            nodeList = nodeGenerator.generateNode();
        }
        return  nodeList;
    }

    public boolean checkKeyPressed(char keyPressed)
    {
        if (nodeListPosition == nodeList.length)
        {
            return false;
        }
        if (nodeList[nodeListPosition] == keyPressed)
        {
            nodeListPosition++;
            return true;
        }
        else
        {
            nodeListPosition = 0;
            return false;
        }
    }

}
