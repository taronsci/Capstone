import java.util.Random;

public class HexagonGrid{

    private VertexGrid vGrid;
    private Hexagon[][] grid;
    int x_length; //size of hex grid
    int y_width;

    public HexagonGrid(int xx, int yy){
        vGrid = new VertexGrid(xx+2, yy+2);

        x_length = xx;
        y_width = yy;
        grid = new Hexagon[x_length][y_width];

        int i = 1;
        int j = 1;
        for(int y = 0; y < y_width;y++) { //y
            for(int x = 0; x < x_length; x++) { //x
                grid[x][y] = new Hexagon(vGrid.getVertex(i,j), vGrid);
                i++;
            }
            i = 1;
            j++;
        }
    }

    public int getX_length() {
        return x_length;
    }
    public int getY_width() {
        return y_width;
    }
    public Hexagon getHexagon(int x, int y){
        return grid[x][y];
    }

    public Hexagon[][] gethGrid() {
        return grid;
    }

    /**
     * @param x
     * @param y
     * @param length one hexagon distance = 2edges
     */
    public void insertVerticalWall(int x, int y, int length, boolean right){
        Hexagon left;
        if(right) {
            for (int i = y; i < y + length; i++) {
                left = this.getHexagon(x, i + 1);

                left.makeWall(5);
                left.makeWall(4);
            }
        }
        //new
        else{
            for (int i = y; i < y + length; i++) {
                left = this.getHexagon(x, i);

                left.makeWall(1);
                left.makeWall(2);
            }
        }

    }

    public void insertHorizontalWall(int x, int y, int length, boolean top){
        Hexagon cur;

        if(top) {
            for (int i = x; i < x + length; i ++) {
                cur = this.getHexagon(i, y);

                cur.makeWall(1);
                cur.makeWall(5);
            }
        }else{
            for (int i = x ; i < x + length; i ++) {
                cur = this.getHexagon(i, y);

                cur.makeWall(2);
                cur.makeWall(4);
            }
        }

    }

    public void insertCornerWalls(){
        Hexagon cur;

        cur = this.getHexagon(x_length-1,y_width-1); //bottom right
        cur.makeWall(2);

        cur = this.getHexagon(x_length - 1,0); //top right
        cur.makeWall(5);

    }

    public void setUpGrid(){ //test set-up grid
        Random rand = new Random(1);
        int a;
        int b;

        for (int y = 2; y < y_width - 2; y++) {
//            a = rand.nextInt(6);
//            b = rand.nextInt(6);
//            grid[2][y].setDirection(a, Hexagon.eddieDir.TC);
//            grid[2][y].setDirection(b, Hexagon.eddieDir.TC);

            grid[2][y].setDirection(2, Hexagon.eddieDir.TC);
            grid[2][y].setDirection(1, Hexagon.eddieDir.TC);
        }
    }

    public void setWalls(){
        insertVerticalWall(0,0,y_width, false); //left wall

        insertVerticalWall(x_length-1,0,y_width-1,true); //right wall
        insertHorizontalWall(0,0,x_length,true);//top wall
        insertHorizontalWall(0,y_width-1,x_length,false);//bottom wall
        insertCornerWalls();
    }

    public void pr(){
        for(int y = 0; y < y_width; y++) { //y
            for(int x = 0; x < x_length; x++) { //x
                System.out.print(grid[x][y] + "    ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
