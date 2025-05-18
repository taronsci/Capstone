import java.util.*;

public class VertexGrid {

    private Vertex[][] grid;
    private HashMap<List<Vertex>, Edge> edges;

    public VertexGrid(int length, int width){
        grid = new Vertex[length][width];
        edges = new HashMap<>();

        grid[0][0] = new Vertex(0,0);
        Vertex cur = new Vertex(1,0);
        grid[1][0] = cur;

        //build top row
        for(int i = 2;i < getX_length();i++){
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
                    if(getX_length() % 2 != 0 && x == length-1)
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
        return grid.length;
    }
    public int getY_width() {
        return grid[0].length;
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

}
