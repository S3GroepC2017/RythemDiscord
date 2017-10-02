package logic;

/**
 * Created by Lars on 25-9-2017.
 */
public class Stave {
    private char[] node;
    private NodeType type;
    private Player player;

    public Stave(char[] node, NodeType type, Player player) {
        this.node = node;
        this.type = type;
        this.player = player;
    }

    public char[] getNode()
    {
        return node;
    }

    public Player getPlayer()
    {
        return player;
    }

    public NodeType getType()
    {
        return type;
    }
}
