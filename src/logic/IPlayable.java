package logic;

/**
 * Created by Lars on 25-9-2017.
 */
public interface IPlayable {
    String getName();
    int getScore();
    boolean addPoints(int amountOfPoints);
}
