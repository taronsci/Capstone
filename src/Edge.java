public class Edge {

    public enum eddie{SE, ES, NA}

    private final Vertex start;
    private final Vertex end;
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

    public Edge(Edge original){
        if(original == null){
            System.out.println("ERROR");
            System.exit(0);
        }

        this.start = new Vertex(original.start);
        this.end = new Vertex(original.end);
        this.direction = original.direction;
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
        return "(" +start.toString() +", "+ end.toString() +")";
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
}
