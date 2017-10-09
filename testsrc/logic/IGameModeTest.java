package logic;

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.junit.Assert.*;

public class IGameModeTest
{
    IGameMode defaultGameMode = new DefaultGameMode("Beschrijving");
    IGameMode ffaGameMode = new FFAGameMode("Beschrijving");
    IGameMode hotSeatGameMode = new HotSeatGameMode("Beschrijving");

    @Test (expected = NotImplementedException.class)
    public void generateNodes() throws Exception
    {
        char[] defaultResult = defaultGameMode.generateNodes();
        char[] ffaResult = ffaGameMode.generateNodes();
        char[] hotSeatResult = hotSeatGameMode.generateNodes();
    }

    @Test
    public void getDescription() throws Exception
    {
        assertSame("Beschrijving", defaultGameMode.getDescription());
        assertSame("Beschrijving", ffaGameMode.getDescription());
        assertSame("Beschrijving", hotSeatGameMode.getDescription());
    }
}