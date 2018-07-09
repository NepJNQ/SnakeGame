# README
&emsp;&emsp;该贪吃蛇app设计思路来源于知乎用户David的文章，大家可以先去看看文章，Coding遇到问题时回来看看，[Java贪吃蛇应用的设计与实现](https://zhuanlan.zhihu.com/p/23316639),我对一些类的设计和代码（主要是GameView中Graphics API的使用）进行了借鉴，刚结束大一的Java入门学习，可能有很多问题，希望大家理解。
***
## 设计模式：MVC
贪吃蛇游戏采用MVC模式：
* Model包括：Snake，Map，Node类，是游戏的主要数据逻辑部分
* View包括：GameView类，用于绘制地图、蛇、食物，SnakeApp类，绘制主界面
* Controller包括：GameController类，接收用户按键处理交互
* 其他：Directions类，枚举类，定义了方向；Settings类，规定了两个静态变量：Node的大小和每次移动距离
***
## 类的介绍
### Node
Snake类的基础，由x,y标记出每一个Node在Map中的位置
### Snake
Snake本质就是LinkedList&lt;Node&gt;
### Map
Map具有length和weigth的限制，Snake和food应出现在Map上，food的初始和Snake的移动都在Map中完成
### GameView
调用Java中Graphics API绘制Snake，Map，food
### GameController
实现KeyListener, Runnable接口，前者实现监听，作出交互，后者另外启动线程让Snake在移动时有间隔，如果不另外启动线程，当sleep时，用户的按键输入无法被接收，产生冲突
### Directions
枚举类，为四个方向设置Code
### Settings
规定两个静态变量：Node的大小和每次移动距离
***
## 个人Coding时遇到的问题和解决  
### 1.用snake.head.equals(food)判断是否吃到食物，发现无效
如果snake.head和food相同，则吃到食物，蛇身加长，继续生成食物，但忽视Object.equals()是比较对象内存地址，而非x，y
#### 解决
在Node类中编写eqals(Node node)方法
```Java
public boolean equals(Node node){
      return (x == node.getX() && y == node.getY());
  }
```

### 2.想当然调用LinkedList.contains()判断Snake是否撞到自己，发现无效
如果判断出下一个节点已经包含在Snake.body中，证明撞到自己，游戏结束，调用Snake.body.contains()发现游戏继续，Snake直接无视body，查看LinkedList.contains()源码，发现问题
```Java
public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }//LinkedList.contains()
```
contains()通过index判断是否包含Node，而我本意通过比较x，y即Node在Map中的坐标判断是否包含该节点
#### 解决
在Snake类中编写内部类myLinkedList继承LinkedList<Node>后，重写contains（）传入Node，通过x，y判断是否包含，将Snake.body创建为myLinkedList对象
```Java
 public class myLinkedList extends LinkedList<Node>{
       public boolean contains(Node node){
           for (Node nodeTemp : this) {
               if (node.equals(nodeTemp))
                   return true;
           }
           return false;
       }
   }
```
  
### 3.设定每个Node的x，y差值
开始认为Snake的节点依次相邻连接，就设定每次水平移动：y±1，垂直移动：x±1，结果发现绘制出的Snake基本只有一个Node的大小，查看GameView.drawSquare()
```Java
private void drawSquare(Graphics graphics, Node square, Color color){
        graphics.setColor(color);
        int size = Settings.DEFAULT_NODE_SIZE;
        graphics.fillRect(square.getX(), square.getY(), size - 1, size - 1);
    }
```
一个Node的Size就是10-1（Settings中设定），x，y每次只变化1，基本相当于原地画了所有Node
#### 解决
设定水平/垂直移动：y/x±size

### 4.永远吃不到food
即便重写了正确的equals（）方法也无法判断snake.equals(food)，查看initfood（）
```Java
private void initFood(){   
        int x = (int) (Math.random()*length );
        int y = (int) (Math.random()*width );
        while (snake.body.contains(new Node(x,y))||x > length||y > width){
            //循环使生成食物不与蛇身重合
            x = (int) (Math.random()*length);
            y = (int) (Math.random()*width);
        }
        food = new Node(x,y);
    }
```
food通过随机数生成，对比最初Node构造函数和Snake构造函数
```Java
Node(int x, int y) {
        this.x = x;
        this.y = y;
    }//Node构造函数
Snake(int x, int y){
        body = new myLinkedList();
        head = new Node(x,y);
        body.add(head);
        for (int i = 0; i < 3; i++){
            x = x - Settings.DEFAULT_NODE_SIZE;
            Node bodyNode = new Node(x,y);
            body.add(bodyNode);
        }
    }//Snake构造函数且初始参数（250，250）    
```
通过Node（）和Snake（）可以知道原因，Snake自初始后每一个节点都是10的整数倍，head可能是坐标（240，350），food可能是坐标（241，350），就算看起来吃到了食物，实际上没有相等
#### 解决
让food坐标也是10的倍数，只需要对Node（）做改动，具有丢弃个位数的功能
```Java
Node(int x, int y) {
        this.x = (x/10)*10;
        this.y = (y/10)*10;
    }
```








 

  
 
