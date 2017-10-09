package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class DefaultGameMode implements IGameMode {

    private String description;

    public DefaultGameMode(String description)
    {
        this.description = description;
    }

    @Override
    public List<Node> generateNodes() {
        throw new NotImplementedException();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
