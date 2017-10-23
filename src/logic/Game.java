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
    }

    public char[] getNodes(){
        return nodeGenerator.generateNode();
    }

    public boolean checkKeyPressed(char keyPressed){
        throw new NotImplementedException();
    }

}
