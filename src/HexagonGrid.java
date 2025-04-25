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
//    public Hexagon getNeighbor(Hexagon hex, int direction){
//        int[] coo = hex.getCenter().getCoordinate();
//
//        switch(direction) {
//            case 0: if(getHexagon(coo[0],coo[1]-1) != null)
//                        return getHexagon(coo[0],coo[1]-1);
//            case 1: if(getHexagon(coo[0]-1,coo[1]) != null)
//                        return getHexagon(coo[0]-1,coo[1]); //left
//
//            case 2: return getHexagon(coo[0]-1,coo[1]+1);
//            case 3: return getHexagon(coo[0],coo[1]+1);
//            case 4: return getHexagon(coo[0]+1,coo[1]+1);
//            case 5: return getHexagon(coo[0]+1,coo[1]); //right
//            default:
//                return null;
//        }
//    }

    public int getX_length() {
        return x_length;
    }
    public int getY_width() {
        return y_width;
    }
    public Hexagon getHexagon(int x, int y){
        return grid[x][y];
    }

    public VertexGrid getvGrid() {
        return vGrid;
    }

    public void pr(){
        for(int y = 0; y < y_width;y++) { //y
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
    public void insertDiagonalWall(int x, int y, int length){
        Hexagon left = getHexagon(x, y);

        for(int i = y;i < y + length; i++){
            left = this.getHexagon(x,i + 1);

            left.makeWall(5);
            left.makeWall(4);
        }
//        left = this.getNeighbor(left,3);
//        left.makeWall(4); //nerqevi teq pate
    }

    public void setUpGrid(){ //test set-up grid
//        for(int x = 0; x < 2; x++) {
            for (int y = 0; y < y_width; y++) {
//                grid[0][y].setEdgeDirections(new Hexagon.eddieDir[]{Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA}, true);
//                grid[0][y].setEdgeDirections(new Hexagon.eddieDir[]{Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA}, true);
                grid[0][y].setDirection(1, Hexagon.eddieDir.TC);
                grid[0][y].setDirection(2, Hexagon.eddieDir.TC);
            }
//        }
//        grid[1][0].setEdgeDirections(new Hexagon.eddieDir[]{Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA},true);
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
