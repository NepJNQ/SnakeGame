
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class GameView {

    private final Map map;
    private JPanel canvas;

    public GameView(Map map){
        this.map = map;
    }

    public void init(){
        canvas = new JPanel(){
            //为匿名类重写方法，省去继承
            @Override
            //paintComponent()绘制此容器中的每个组件
            public void paintComponent(Graphics graphics){
                super.paintComponent(graphics);
                setBackground(Color.BLACK);
                drawSnake(graphics,map.getSnake());
                drawFood(graphics,map.getFood());
            }
        };//重写方法需要在结尾加括号
    }

    public void drawSnake(Graphics graphics, Snake snake){
        for(Iterator i = snake.body.iterator(); i.hasNext();){
            Node bodyNode = (Node)i.next();
            drawSquare(graphics, bodyNode, Color.cyan);
        }
    }

    public void drawFood(Graphics graphics, Node food){
        drawCircle(graphics, food, Color.ORANGE);
    }

    public void showGameOverMessage(){
        int willContinue = JOptionPane.showConfirmDialog(null,"游戏结束，是否重新开始？",null,JOptionPane.YES_NO_OPTION);
        if(willContinue == 0){
            SnakeApp snakeGame = new SnakeApp();
            snakeGame.init();
        }else{
            System.exit(0);
        }

    }

    /*
    两个帮助画图的辅助方法
    遇到问题：只有一个方块，解决：没有考虑到Square的边长远大于Node移动距离，所以近似在一处绘图
    在Snake类中把Node的间距设置成和Square的size相同
     */
    private void drawSquare(Graphics graphics, Node square, Color color){
        graphics.setColor(color);
        int size = Settings.DEFAULT_NODE_SIZE;
        graphics.fillRect(square.getX(), square.getY(), size - 1, size - 1);
    }
    private void drawCircle(Graphics graphics, Node circle, Color color){
        graphics.setColor(color);
        int size = Settings.DEFAULT_NODE_SIZE;
        graphics.fillOval(circle.getX(), circle.getY(), size, size );
    }

    public void draw(){
        canvas.repaint();
    }

    public JPanel getCanvas(){
        return canvas;
    }
}
