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

    public void pr(){
        for(int y = 0; y < y_width; y++) { //y
            for(int x = 0; x < x_length; x++) { //x
                System.out.print(grid[x][y] + "    ");
            }
            System.out.println();
        }
        System.out.println();
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

    public static void main(String[] args) {
//        VertexGrid.initialize(4,4);
        HexagonGrid test = new HexagonGrid(4, 4);
//        HexagonGrid clone = new HexagonGrid(4, 4);

        test.pr();
//        test.setUpGrid();
        Hexagon a = test.getHexagon(0,0);
//        Hexagon b = clone.getHexagon(0,0);

//        System.out.println(test.getHexagon(0,0).getEdge_nw().getDirection());
//        System.out.println(test.getHexagon(1,0).getEdge_nw().getDirection());

        if(test.getHexagon(0,0).getEdge_s() == test.getHexagon(0,1).getEdge_n())
            System.out.println(1000);

//        test.getHexagon(0,0).setDirection_s(Hexagon.eddieDir.TC);
        test.getHexagon(0,1).makeWall(0);

        System.out.println("0,0 s edge: "+ test.getHexagon(0,0).getEdge_s());
        System.out.println("0,0 s eddie: "+ test.getHexagon(0,0).getEddie(3));

        System.out.println("0,1 n edge: " + test.getHexagon(0,1).getEdge_n());
        System.out.println("0,1 n eddie: " + test.getHexagon(0,1).getEddie(0));

        test.getHexagon(0,0).setDirection_ne(Hexagon.eddieDir.TC);

//        test.getHexagon(1,0).makeWall(5);
//
//
//        System.out.println("0,0 s edge: "+ test.getHexagon(0,0).getEdge_ne());
//        System.out.println("0,0 s eddie: "+ test.getHexagon(0,0).getEddie(5));
//
//        System.out.println("1,0 n edge: " + test.getHexagon(1,0).getEdge_sw());
//        System.out.println("1,0 n eddie: " + test.getHexagon(1,0).getEddie(2));

        if(test.getHexagon(0,0).getEdge_s() == test.getHexagon(0,1).getEdge_n())
            System.out.println(1500);



//        Grid update tests
//        System.out.println("1st phase");
//        test.update(clone);
//        original
//        NA NA NA NA NA NA    NA NA NA NA FC NA
//        NA NA NA NA FC NA    NA NA NA NA NA NA
//
//        copy
//        NA NA NA NA FC NA    NA NA NA NA FC NA
//        NA NA NA NA FC NA    NA TC NA NA FC NA

//        System.out.println("2nd phase");
//        clone.copy(test);
//        original
//        NA NA NA NA FC NA    NA NA NA NA FC NA
//        NA NA NA NA FC NA    NA TC NA NA FC NA
//
//        copy
//        NA NA NA NA NA NA    NA NA NA NA NA NA
//        NA NA NA NA NA NA    NA NA NA NA NA NA


//        //initial tests
//        System.out.println(a);
//        System.out.println(a.getEdge_n().getDirection()); //should be NA
//        a.setDirection_n(Hexagon.eddieDir.FC);
//        System.out.println(a.getEdge_n().getDirection()); //Should be SE
//        System.out.println(a); //should be FC in 1st direction

//        //rule 0
//        a.setDirection_nw(Hexagon.eddieDir.TC);
//        a.setDirection_se(Hexagon.eddieDir.TC);
//        System.out.println(a);
//        System.out.println(a.getEdge_ne().getDirection()); //Should be NA
//        test.update(clone);
//        clone.copy(test);
        //NA NA FC NA NA FC    NA NA TC NA NA NA
        //NA NA NA NA NA NA    NA NA NA NA NA NA

//        System.out.println(b.getEdge_sw().getDirection()); //Should be ES
//        System.out.println(b.getEdge_ne().getDirection()); //Should be ES

//        //rule 1
//        a.setDirection_nw(Hexagon.eddieDir.TC);
//        a.setDirection_ne(Hexagon.eddieDir.TC);
//        a.setDirection_se(Hexagon.eddieDir.TC);
//        a.setDirection_sw(Hexagon.eddieDir.TC); //with extra TC eddie
//        System.out.println(a);
//        test.update(clone);
//        clone.copy(test);
        //FC NA FC FC NA FC    NA NA TC NA NA NA
        //TC NA NA NA NA NA    NA NA NA NA NA NA

//        //rule 2
//        a.setDirection_se(Hexagon.eddieDir.FC); //with extra FC
//        a.setDirection_sw(Hexagon.eddieDir.TC);//and extra TC
//        a.setDirection_n(Hexagon.eddieDir.TC);
//        a.setDirection_ne(Hexagon.eddieDir.TC);
//        a.setDirection_s(Hexagon.eddieDir.TC);
//        System.out.println(a);
//        test.update(clone);
//        clone.copy(test);
//        NA FC FC NA NA FC    NA NA TC NA NA NA
//        NA NA NA NA NA NA    NA NA NA NA FC NA

//        //rule 3
//        a.setDirection_nw(Hexagon.eddieDir.TC);
//        a.setDirection_sw(Hexagon.eddieDir.FC); //with extra FC eddie
//        a.setDirection_ne(Hexagon.eddieDir.TC);
//        a.setDirection_s(Hexagon.eddieDir.TC);
//        System.out.println(a);
//        test.update(clone);
//        clone.copy(test);
        //NA FC FC FC NA FC    NA NA TC NA NA NA
        //TC NA NA NA NA NA    NA NA NA NA NA NA

//        //rule 4
//        a.setDirection_n(Hexagon.eddieDir.TC);
//        a.setDirection_nw(Hexagon.eddieDir.TC);
//        a.setDirection_s(Hexagon.eddieDir.TC);
//        a.setDirection_se(Hexagon.eddieDir.TC);
//        a.setDirection_sw(Hexagon.eddieDir.FC); //with extra FC eddie
//        System.out.println(a);
//        test.update(clone);
//        clone.copy(test);
        //NA FC FC NA FC FC    NA NA TC NA NA NA
        //NA NA NA NA NA NA    NA TC NA NA NA NA

//        //no rule case
//        a.setDirection_n(Hexagon.eddieDir.TC);
//        a.setDirection_ne(Hexagon.eddieDir.FC);
//        a.setDirection_sw(Hexagon.eddieDir.FC);
//        System.out.println(a);
//        test.update(clone);
//        clone.copy(test);
        //NA NA FC FC NA NA    NA NA NA NA NA FC
        //TC NA NA NA NA NA    NA NA NA NA NA NA

    }
}
