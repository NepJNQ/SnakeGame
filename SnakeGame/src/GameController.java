import javafx.scene.control.skin.TextInputControlSkin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener, Runnable {

    private final Map map;
    private final GameView gameView;

    private int keyCode = KeyEvent.VK_RIGHT;

    private boolean running;

    public GameController(Map map, GameView gameView){
        this.map = map;
        this.gameView = gameView;
        this.running = true;
    }

    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e){
        boolean result = true;
        //防止逆行
        if((Math.abs(keyCode - e.getKeyCode()) != KeyEvent.VK_DOWN - KeyEvent.VK_UP)
                && (Math.abs(keyCode - e.getKeyCode()) != KeyEvent.VK_RIGHT - KeyEvent.VK_LEFT)){
            keyCode = e.getKeyCode();
        }
        switch (keyCode){
            case KeyEvent.VK_UP:
                result = map.move(Directions.UP.getDirectionsCode());
                break;
            case KeyEvent.VK_DOWN:
                result = map.move(Directions.DOWN.getDirectionsCode());
                break;
            case KeyEvent.VK_LEFT:
                result = map.move(Directions.LEFT.getDirectionsCode());
                break;
            case KeyEvent.VK_RIGHT:
                result = map.move(Directions.RIGHT.getDirectionsCode());
                break;
        }
        if (result){
            gameView.draw();
        }else {
            gameView.showGameOverMessage();
        }

    }

    @Override
    public void run(){
        while (running){
            try{
                Thread.sleep(Settings.DEFAULT_MOVE_INTERVAL);
            }catch (InterruptedException e){
                break;
            }
            if(map.moveAuto()){
                gameView.draw();
            }else{
                gameView.showGameOverMessage();
                break;
            }
        }
    }

}
