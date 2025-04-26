import javax.swing.*;
import javax.swing.Timer;

public class HexCellularAutomaton {

    private HexagonGrid main;
    private HexagonGrid clone;
    private WolframRules rules;

    private HexViewerPanel viewerPanel;
    private int count = 0;

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
            setup();
        }

    }

    public void setup(){
        main.setUpGrid();
        main.setWalls();
    }

    public void setWall(int x_wall, int y_wall, int wall_length){
        main.insertDiagonalWall(x_wall, y_wall, wall_length);
    }



    public void draw(int steps){
        JFrame frame = new JFrame("Hexagonal Lattice Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        viewerPanel = new HexViewerPanel(main.gethGrid());
        frame.add(viewerPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        startTimer(steps);
    }

    private void startTimer(int steps) {

        Timer timer = new Timer(50, e -> {
            if(count > steps){
                ((Timer) e.getSource()).stop();
                return;
            }

            this.step(1); // ⚡ this should update the lattice gas model to the next step
            viewerPanel.repaint(); // 🔴 this repaints ONLY the arrows (grid is static)
            count++;
        });

        timer.start();
    }

    public static void main(String[] args) {
        WolframRules bookRules = new WolframRules();
        HexCellularAutomaton test = new HexCellularAutomaton(165,75, bookRules); // 165,75, size 10 fits computer screen
        //10,50, (5,10,20)

        test.setup();
        test.setWall(20,20,30); //(2, 4,rules), (0,0,2) 1step

//        test.step(1000);
//        test.main.pr();
        test.draw(100);


    }
}
