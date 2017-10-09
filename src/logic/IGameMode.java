package logic;

import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public interface IGameMode {
    List<Node> generateNodes();
    String getDescription();
}
