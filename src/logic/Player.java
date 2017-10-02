package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Lars on 25-9-2017.
 */
public class Player implements IPlayable {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
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
