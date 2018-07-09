import java.util.LinkedList;
import java.util.ListIterator;

public class Snake {
    enum Directions{
        UP(0),
        DOWN(1),
        LEFT(2),
        RIGHT(3);
        private int directionsCode;
        Directions(int directionsCode){
            this.directionsCode = directionsCode;
        }

        public int getDirectionsCode(){
            return directionsCode;
        }
    }

    //重写LinkedList的contains方法,原contains参数Object所以此处不算Override
    public class myLinkedList extends LinkedList<Node>{

        public boolean contains(Node node){
            for (Node nodeTemp : this) {
                if (node.equals(nodeTemp))
                    return true;
            }
            return false;
        }
    }

    Node head;
    myLinkedList body;
    //确定head位置后，左边接三个Node作为body
    Snake(int x, int y){
        body = new myLinkedList();
        head = new Node(x,y);
        body.add(head);
        for (int i = 0; i < 3; i++){
            x = x - Settings.DEFAULT_NODE_SIZE;
            Node bodyNode = new Node(x,y);
            body.add(bodyNode);
        }
    }

}
