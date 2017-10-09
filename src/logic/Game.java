package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Lars on 25-9-2017.
 */
public class Game
{
    private GameDifficulty difficulty;
    private GameStatus status;

    public Game(GameDifficulty difficulty, GameStatus status)
    {
        this.difficulty = difficulty;
        this.status = status;
    }

    public boolean startGame()
    {
        return false;
    }

    public boolean pauseGame()
    {
        return false;
    }

    public boolean resumeGame()
    {
        return false;
    }

    public boolean quitGame()
    {
        return false;
    }

    public Stave[] getNodes()
    {
        return null;
    }

    public Stave getNodes(Player player)
    {
        return null;
    }

    public void distributeNodes()
    {
        throw new NotImplementedException();
    }

    public boolean checkInputNode(char node, Player player)
    {
        return false;
    }
}
