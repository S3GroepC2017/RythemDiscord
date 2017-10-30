package oplevering;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import logic.GameManager;
import logic.ILogic;

public class Controller
{
    private ILogic logic;

    public Controller()
    {
        this.logic = new GameManager();
        startGame();
    }

    @FXML private Label mainKey;
    @FXML private Label futKey1;
    @FXML private Label futKey2;
    @FXML private Label futKey3;

    @FXML private Pane mainPane;

    @FXML protected void keyPressed(KeyEvent event)
    {
        char c = event.getCharacter().toCharArray()[0];
        System.out.println(c);

        //TODO
        boolean succes = logic.keyPressed(c);
        System.out.println(succes);
    }

    @FXML protected void initialize()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                mainPane.requestFocus();
                setKeys();
            }
        });
    }

    protected void startGame()
    {
        logic.startGame();
    }

    private void setKeys()
    {
        char[] keys = logic.getNodes();

        mainKey.setText(Character.toString(keys[0]));
        futKey1.setText(Character.toString(keys[0 + 1]));
        futKey2.setText(Character.toString(keys[0 + 2]));
        futKey3.setText(Character.toString(keys[0 + 3]));
    }
}
