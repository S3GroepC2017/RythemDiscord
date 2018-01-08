package gameserver_tests;

import com.csharp.game.server.NodeGenerator;
import com.csharp.sharedclasses.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NodeGeneratorTest
{
    NodeGenerator nodeGenerator;
    NodeGenerator nodeGenerator2;
    NodeGenerator nodeGenerator3;
    List<Player> players = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        nodeGenerator = new NodeGenerator();
        nodeGenerator2 = new NodeGenerator(2);
        nodeGenerator3 = new NodeGenerator(3);
        players.add(new Player("TestPlayer1"));
        players.add(new Player("TestPlayer2"));
    }

    @Test
    public void generateNode() throws Exception
    {
        List<Player> gamePlayers = nodeGenerator.generateNode(players);
        char[] nodes = new char[5];
        int index = 0;
        for(Player player : gamePlayers)
        {
            for(char node : player.getNodeList())
            {
                nodes[index] = node;
            }
        }
        assertEquals("Test nodeGenerator with no paramater constructor", 5, nodes.length);
        checkArrayForEmptySlots(nodes);
        gamePlayers = nodeGenerator2.generateNode(players);
        nodes = new char[2];
        index = 0;
        for(Player player : gamePlayers)
        {
            for(char node : player.getNodeList())
            {
                nodes[index] = node;
            }
        }
        assertEquals("Test nodeGenerator with 2 as paramater constructor", 2, nodes.length);
        checkArrayForEmptySlots(nodes);
        gamePlayers = nodeGenerator3.generateNode(players);
        nodes = new char[3];
        index = 0;
        for(Player player : gamePlayers)
        {
            for(char node : player.getNodeList())
            {
                nodes[index] = node;
            }
        }
        assertEquals("Test nodeGenerator with 3 as paramater constructor", 3, nodes.length);
        checkArrayForEmptySlots(nodes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateNodeFail1()
    {
        nodeGenerator = new NodeGenerator(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateNodeFail2()
    {
        nodeGenerator = new NodeGenerator(0);
    }

    private void checkArrayForEmptySlots(char[] nodes)
    {
        for(char node : nodes)
        {
            assertNotEquals("Check for empty array slots", null, node);
        }
    }
}