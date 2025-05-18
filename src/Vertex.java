public class Vertex {

    public final int[] coordinate;

    public Vertex(int x, int y){
        coordinate = new int[]{x, y};
    }


    public int[] getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if(!(obj instanceof Vertex v))
            return false;
        return coordinate[0] == v.getCoordinate()[0] && coordinate[1] == v.getCoordinate()[1];
    }

    @Override
    public int hashCode() {
        return 7 * coordinate[0] + 11 * coordinate[1]; //use bitwise XOR if too much clashing
    }

    public String toString() {
        return "(" + coordinate[0] + ", " + coordinate[1] + ") ";
    }
}
