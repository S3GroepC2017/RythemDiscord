package logic;

/**
 * Created by Lars on 9-10-2017.
 */
public class Node
{
    private char key;
    private NodeType type;

    public Node(char key, NodeType type)
    {
        this.key = key;
        this.type = type;
    }

    public char getKey()
    {
        return key;
    }

    public NodeType getType()
    {
        return type;
    }

}
