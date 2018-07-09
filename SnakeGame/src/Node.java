
public class Node {
    private int x;
    private int y;

    Node(int x, int y) {
        this.x = (x/10)*10;
        this.y = (y/10)*10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //object中的equals无用
    public boolean equals(Node node){
        return (x == node.getX() && y == node.getY());
    }
}

