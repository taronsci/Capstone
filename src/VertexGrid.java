import java.util.*;

public class VertexGrid {

//    private static VertexGrid instance; //singleton

    private Vertex[][] grid;
    public final int x_length;
    public final int y_width;
    private HashMap<List<Vertex>, Edge> edges;

    public VertexGrid(int length, int width){
        x_length = length;
        y_width = width;
        grid = new Vertex[length][width];
        edges = new HashMap<>();

        grid[0][0] = new Vertex(0,0);
        Vertex cur = new Vertex(1,0);
        grid[1][0] = cur;

        //build top row
        for(int i = 2;i < x_length;i++){
            Vertex next = new Vertex(i,0);
            grid[i][0] = next;

            edges.put(Arrays.asList(cur, next), new Edge(cur, next));
            cur = next;
        }
        edges.put(Arrays.asList(grid[0][1], grid[1][0]), new Edge(grid[0][1], grid[1][0]));

        //for every row, construct bottom row
        for(int y = 1; y < width;y++){
            for(int x = 0; x < length ;x++){
                Vertex down = new Vertex(x,y);
                grid[x][y] = down;
                if(x != 0) {
                    edges.put(Arrays.asList(down, grid[x-1][y]), new Edge(down, grid[x-1][y])); //left
                }

                edges.put(Arrays.asList(down, grid[x][y - 1]), new Edge(down, grid[x][y - 1])); //up

                if(x % 2 == 0){
                    if(x!=0) {
                        edges.put(Arrays.asList(down, grid[x - 1][y - 1]), new Edge(down, grid[x - 1][y - 1])); //top left
                    }
                    if(x_length % 2 != 0 && x == length-1)
                        continue;
                    edges.put(Arrays.asList(down, grid[x+1][y-1]), new Edge(down, grid[x+1][y-1])); //top right
                }
            }
        }
        //top left
        edges.remove(Arrays.asList(grid[0][1], grid[0][0]));
//        edges.remove(Arrays.asList(grid[1][1], grid[1][0]));
        //top right, ... later

    }

    public void setNull(Edge e){
//        edges.remove(e.getEdge());
        e.setNull();
    }

    public Vertex getVertex(int x, int y){
        return grid[x][y];
    }

    public Edge getEdge(Vertex s, Vertex e){
        //add exception if edge is not there
        if(edges.containsKey(Arrays.asList(s, e)))
            return edges.get(Arrays.asList(s, e));
        else
            return edges.get(Arrays.asList(e, s));
    }

    public Vertex[][] getGrid() {
        return grid;
    }

    public int getX_length() {
        return x_length;
    }
    public int getY_width() {
        return y_width;
    }
    public Vertex[] getVertexArray(int[] x, int[] y){
        if(x.length != 7 || x.length != y.length)
            return null;

        Vertex[] res = new Vertex[x.length];
        for(int i=0;i<x.length;i++){
            res[i] = getVertex(x[i],y[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        VertexGrid a = new VertexGrid(5,5);
        for(int i = 0; i < a.y_width;i++){
            for(int j = 0;j < a.x_length;j++){
                System.out.print(a.getVertex(j,i) + " ");
            }
            System.out.println();
        }

        Vertex t = a.getVertex(1,1);
        Vertex t1 = a.getVertex(2,1);
        Vertex t2 = a.getVertex(0,2);

        Edge b = a.getEdge(t1,t);
        System.out.println(b);

        b.setDirection(Edge.eddie.ES);

        Edge c = a.getEdge(t1,t2);
        c.setDirection(Edge.eddie.SE);

        System.out.println(b);
        System.out.println(b.getDirection());
        System.out.println(c);
        System.out.println(c.getDirection());
    }
}
