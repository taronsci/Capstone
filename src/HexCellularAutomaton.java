import javax.swing.*;
import javax.swing.Timer;

public class HexCellularAutomaton {

    private HexagonGrid main;
    private HexagonGrid clone;
    public final WolframRules rules;

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
                rules.enforceRule(main.getHexagon(x,y), clone.getHexagon(x,y));
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
                //edge null checks done only for method below
                main.getHexagon(x,y).setEdgeDirections(clone.getHexagon(x,y).getHexagonEddies(),false);
                clone.getHexagon(x,y).reset();
            }
        }
    }

    public void step(int count){
        for(int i = 0; i < count; i++){
            update();
            copy();

            main.setUpGrid();
        }
    }

    public void setup(){
        if(count == 0)
            main.setWalls();

        main.setUpGrid();
    }

    public void setWall(int x_wall, int y_wall, int wall_length){
        main.insertVerticalWall(x_wall, y_wall, wall_length, true);
    }

    public void draw(int steps){
        //Individual arrows
        JFrame frame = new JFrame("Hexagonal Lattice Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HexViewerPanelIndiv viewerPanel = new HexViewerPanelIndiv(main.gethGrid());
        frame.add(viewerPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Average arrows
        JFrame frame1 = new JFrame("Hexagonal Lattice Viewer");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HexViewerPanelAVG viewerPanel1 = new HexViewerPanelAVG(main.gethGrid());
        frame1.add(viewerPanel1);
        frame1.pack();
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);

        startTimer(steps, viewerPanel, viewerPanel1);
    }

    private void startTimer(int steps, HexViewerPanel viewerPanel, HexViewerPanel hexViewerPanel) {

        Timer timer = new Timer(50, e -> {
            if(count > steps){
                ((Timer) e.getSource()).stop();
                return;
            }

            this.step(1); // ⚡ this should update the lattice gas model to the next step
            viewerPanel.repaint(); // 🔴 this repaints ONLY the arrows (grid is static)
            hexViewerPanel.repaint();
            count++;
        });

        timer.start();
    }

    public static void main(String[] args) {
        WolframRules bookRules = new WolframRules();
        HexCellularAutomaton test = new HexCellularAutomaton(200,100, bookRules); // 165,75, size 10 fits computer screen
        //10,50, (5,10,20)

        test.setup();
        test.setWall(20,20,30); // (20,20,30)

        test.draw(400);
    }
}
