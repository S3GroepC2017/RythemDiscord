package logic;

/**
 * Created by Lars on 25-9-2017.
 */
public interface ILogic {
    int createGame(String playerNameHost, IGameMode gameMode, GameDifficulty gameDifficulty);
    boolean joinLobby(String playerName, int lobbyNumber);
    boolean leaveLobby(Player player);
    Stave startGame();
    char[] showNotes(Player player);
    boolean playNote(char pressedKey, Player player);
    boolean pauseGame();
    boolean resumeGame();
    boolean quitGame();
}
