package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class Lobby {
    private List<IPlayable> teams;
    private int number;

    public Lobby(int number, IPlayable team) {
        this.number = number;
        teams = new ArrayList<>();
        teams.add(team);
    }

    public boolean joinLobby(Player player) {
        throw new NotImplementedException();
    }

    public boolean leaveLobby(IPlayable team) {
        throw new NotImplementedException();
    }

    public int getLobbyNumber()
    {
        return number;
    }

    public List<IPlayable> getTeams()
    {
        return teams;
    }
}
