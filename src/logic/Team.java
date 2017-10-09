package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class Team implements IPlayable {
    private String name;
    private int score;
    private List<Player> players;

    public Team(String name, Player player) {
        this.name = name;
        players = new ArrayList<>();
        players.add(player);
    }

    public boolean addPlayer(Player player)
    {
        if(player == null)
        {
            return false;
        }
        return players.add(player);
    }

    public boolean removePlayer(Player player)
    {
        if(player == null)
        {
            return false;
        }
        return players.remove(player);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public boolean addPoints(int amountOfPoints) {
        if (amountOfPoints <= 0) {
            return false;
        }
        score += amountOfPoints;
        return true;
    }
}
