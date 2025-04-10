public class HexCellularAutomaton {

    private HexagonGrid main;
    private HexagonGrid clone;
    private WolframRules rules;

    /**
     *
     * @param x number of hexagons in x direction
     * @param y number of hexagons in y direction
     * @param rules the set of rules we want too use
     */
    public HexCellularAutomaton(int x, int y, WolframRules rules){
        main = new HexagonGrid(x, y);
        clone = new HexagonGrid(x,y);
        this.rules = rules;
    }

    /**
     * Updates the copy Hexagon (1st step of step)
     */
    private void update() {
        for(int x = 0; x < main.getX_length(); x++) { // int x = x_length - 1; x >=0 ; x--
            for (int y = 0; y < main.getY_width(); y++) {
//                System.out.println(x+ " " + y);
                rules.enforceRule(main.getHexagon(x,y), clone.getHexagon(x,y));

//                System.out.println("original");
//                main.pr();
//                System.out.println("copy");
//                clone.pr();
            }
        }
    }

    /**
     * Copies contents of copy Hexagon to the original Hexagon (2st step of step)
     */
    private void copy(){
        //throw exception if clone is bigger than original or isn't suitable

        for(int x = 0; x < main.getX_length(); x++) { // int x = x_length - 1; x >=0 ; x--
            for (int y = 0; y < main.getY_width(); y++) {
//                System.out.println(x+ " " + y);

                //edge null checks done only for method below
                main.getHexagon(x,y).setEdgeDirections(clone.getHexagon(x,y).getHexagonEddies(),false);

                clone.getHexagon(x,y).reset();
//                System.out.println("original");
//                main.pr();
//                System.out.println("copy");
//                clone.pr();
            }
        }
    }

    public void step(int count){
        for(int i = 0; i < count; i++){
            update();
            copy();
//            main.pr();
            setup();
        }

    }

    public void setup(){
        main.setUpGrid();
    }

    public void setWall(int x_wall, int y_wall, int wall_length){
        main.insertDiagonalWall(x_wall, y_wall, wall_length);
    }

    public static void main(String[] args) {
        WolframRules bookRules = new WolframRules();
        HexCellularAutomaton test = new HexCellularAutomaton(10,30, bookRules);

        test.setup();
        test.setWall(5,7,10); //(2, 4,rules), (0,0,2) 1step
//        NA TC NA null null NA    NA TC NA NA FC NA
//        null TC NA null FC NA    NA null NA null FC NA
//        null TC NA NA null NA    null TC NA null FC NA
//        NA TC NA NA FC NA    null null NA NA FC NA
        test.main.pr();

        test.step(50);
        test.main.pr();

    }
}
