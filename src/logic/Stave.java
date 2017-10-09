package logic;

import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class Stave {
    private List<Node> nodes;
    private Player player;

    public Stave(List<Node> nodes, Player player) {
        this.nodes = nodes;
        this.player = player;
    }

    public List<Node> getNodes()
    {
        return nodes;
    }

    public Player getPlayer()
    {
        return player;
    }
}
