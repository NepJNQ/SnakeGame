import javax.swing.*;
import java.awt.*;

public class SnakeApp {
    private Map map;
    private GameView gameView;
    private GameController gameController;


    public void init(){
        map = new Map(500,500);

        JFrame frame = new JFrame("snakeGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();

        gameView = new GameView(map);
        gameView.init();

        gameView.getCanvas().setPreferredSize(new Dimension(500,500));
        contentPane.add(gameView.getCanvas(),BorderLayout.CENTER);

        gameController = new GameController(map,gameView);
        frame.addKeyListener(gameController);

        new Thread(gameController).start();

        frame.pack();
        frame.setVisible(true);


    }
}
