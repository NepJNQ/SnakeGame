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