public class Map {
    private int length;
    private int width;
    //direction保存当前方向，使得无操作时持续直行
    private int direction = Directions.RIGHT.getDirectionsCode();

    private Node food;
    private Snake snake;

    Map(int length, int width){
        this.length = length;
        this.width = width;

        snake = new Snake(length/2,width/2);
        initFood();
    }

    public int getLength(){
        return length;
    }
    public int getWidth(){
        return width;
    }


    /*
    方法中x，y不是map数组的索引，而是人为按顺序
    构造出链表节点的坐标
     */
    public boolean move(int directionsCode){
        direction = directionsCode;
        int x = snake.head.getX();
        int y = snake.head.getY();
        switch (directionsCode){
            case 0:
                if(canMove(x, y - Settings.DEFAULT_NODE_SIZE))
                    return true;
                break;
            case 1:
                if(canMove(x, y + Settings.DEFAULT_NODE_SIZE))
                    return true;
                break;
            case 2:
                if(canMove(x - Settings.DEFAULT_NODE_SIZE, y))
                    return true;
                break;
            case 3:
                if(canMove(x + Settings.DEFAULT_NODE_SIZE, y))
                    return true;
                break;
        }
        return false;
    }


    /*
    判断移动是否有效：
    1.不能超出地图长度宽度
    2.是否吃到食物
    3.不能撞到自己
    4.x，y可以为负数L，注意if条件
    5.LinkedList.contains()通过链表参数节点index返回值判断是否包含，
    需要重写，由x，y判断
     */
    private boolean canMove(int x, int y){
        Node tail = snake.body.getLast();//记录尾节点，在吃到食物时直接恢复
        //不能超出棋盘且不能撞到自己
        if (!(((x > length || x < 0 || y > width || y < 0))
                || (snake.body.contains(new Node(x,y))))){
            snake.body.addFirst(snake.head = new Node(x,y));
            snake.body.removeLast();
            if (snake.head.equals(food)){//判断是否吃到食物
                snake.body.addLast(tail);
                initFood();
            }
            return  true;
        }
        return false;
    }

    /*
    生成食物：
    1.在棋盘范围内
    2.不能与蛇身重合
     */
    private void initFood(){
        //random()生成[0,1)范围double数字，强转部分应全部括起，否则只针对random，一直为0
        int x = (int) (Math.random()*length );
        int y = (int) (Math.random()*width );
        while (snake.body.contains(new Node(x,y))||x > length||y > width){
            //循环使生成食物不与蛇身重合
            x = (int) (Math.random()*length);
            y = (int) (Math.random()*width);
        }
        food = new Node(x,y);
    }

    public boolean moveAuto(){
        return move(direction);
    }

    public Snake getSnake(){
        return snake;
    }

    public Node getFood(){
        return food;
    }



}
