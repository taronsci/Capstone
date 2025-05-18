import java.util.List;

public class Edge {

    public enum eddie{SE, ES, NA}

    private Vertex start;
    private Vertex end;
    private eddie direction;

    public Edge(Vertex start, Vertex end, eddie direction){
        this.start = start;
        this.end = end;
        this.direction = direction;
    }
    public Edge(Vertex start, Vertex end){
         this(start, end, eddie.NA);
    }
    public Edge(eddie direction){
        this.direction = direction;
        start = new Vertex(0,0);
        end = new Vertex(0,0);
    }

    /**
     * 2 Edges are equal if they have the same direction
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Edge edge))
            return false;
        return direction == edge.direction;
    }

    public String toString() {
        if(end == null)
            return null;

        return "(" + start.toString() +", "+ end.toString() +")";
    }

    public void setDirection(eddie direction){
        this.direction = direction;
    }
    public eddie getDirection() {
        return direction;
    }
    public Vertex getEnd() {
        return end;
    }
    public Vertex getStart() {
        return start;
    }

    public List<Vertex> getEdge(){
        return List.of(start, end);
    }

    public void setNull(){
        start = null;
        end = null;
    }
}
