package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Lars on 25-9-2017.
 */
public class FFAGameMode implements IGameMode{

    private String description;

    @Override
    public char[] generateNodes() {
        throw new NotImplementedException();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
