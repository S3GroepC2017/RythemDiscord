package logic;

import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class GameManager  implements ILogic{
    @Override
    public int createGame(String playerNameHost, IGameMode gameMode, GameDifficulty gameDifficulty) {
        return 0;
    }

    @Override
    public boolean joinLobby(String playerName, int lobbyNumber) {
        return false;
    }

    @Override
    public boolean leaveLobby(Player player) {
        return false;
    }

    @Override
    public List<Team> getTeams(Lobby lobby)
    {
        return null;
    }

    @Override
    public boolean joinTeam(Player player, Team team)
    {
        return false;
    }

    @Override
    public Stave startGame() {
        return null;
    }

    @Override
    public char[] showNotes(Player player) {
        return new char[0];
    }

    @Override
    public boolean playNote(char pressedKey, Player player) {
        return false;
    }

    @Override
    public boolean pauseGame() {
        return false;
    }

    @Override
    public boolean resumeGame() {
        return false;
    }

    @Override
    public boolean quitGame() {
        return false;
    }
}
