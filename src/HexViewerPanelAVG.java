import java.awt.*;
import java.awt.geom.Line2D;

public class HexViewerPanelAVG extends HexViewerPanel{
    static final int CELL_SIZE = 6;
    double[] v = new double[]{0,0};

    public HexViewerPanelAVG(Hexagon[][] hexGrid){
        super(hexGrid);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Always draw the dynamic arrows
        drawAverageArrows(g2, CELL_SIZE, false);
        count++;
    }

    public void drawAverageArrows(Graphics2D g2, int cellSize, boolean avg) {
        double[] momentum;

        double[] nums;
        Hexagon hex;
        double center_x;
        double center_y;
        int density; //mean number of particles per cell
        double[] velocity;

        for(int k1 = 0; k1 < hexGrid.length; k1 += cellSize){
            for(int k2 = 0; k2 < hexGrid[0].length; k2 += cellSize) {
                density = 0;
                momentum = new double[2];

                for (int row = k1; row < Math.min(k1 + cellSize, hexGrid.length) ; row ++) {
                    for (int col = k2; col < Math.min(k2 + cellSize,hexGrid[0].length)  ; col ++) {
                        hex = hexGrid[row][col];

                        double x = col * WIDTH * 0.5 + WIDTH / 2;
                        double y = row * HEIGHT * 0.5 - (col % 2 == 0 ? 0 : HEIGHT / 4) + 3 * HEIGHT / 4;

                        nums = drawHexEdges(g2, x, y, hex,true);
                        density += (int) nums[0]; //adding densities of all sites inside the cell
                        momentum[0] += nums[1]; //adding x components of momentum
                        momentum[1] += nums[2];
                    }
                }

                v[0] += momentum[0];
                v[1] += momentum[1];


                density /= (cellSize * cellSize); //average density by site 16/36
                velocity = new double[]{momentum[0] / density, momentum[1] / density};


                center_x = (k1 + (double) cellSize /2) * WIDTH * 0.5 + WIDTH / 2;
                center_y = (k2+ (double) cellSize /2) * HEIGHT * 0.5 - ((k1 + (double) cellSize /2) % 2 == 0 ? 0 : HEIGHT / 4) + 3 * HEIGHT / 4;


                drawAvArrow(g2, center_x, center_y, velocity, avg);
            }
        }
    }

    private void drawAvArrow(Graphics2D g2, double cx, double cy, double[] velocity, boolean avg) {
        if(!avg){
            //mijin velocity of all ? *36 if not work
            v[0] = v[0] / (16*16); //hexGrid[0].length;
//            v[0] /= hexGrid[0].length;
            v[1] = v[1] / (16*16); //hexGrid.length;
//            v[1] /= hexGrid.length;

            velocity[0] = velocity[0] - v[0];
            velocity[1] = velocity[1] - v[1];
        }

        double angle = Math.atan2(velocity[1], velocity[0]);
        double magnitude = Math.sqrt(Math.pow(velocity[0], 2) + Math.pow(velocity[1], 2));

        double dx = Math.cos(angle) * magnitude;
        double dy = Math.sin(angle) * magnitude;

        double x1, y1, x2, y2;

        x1 = cx;
        y1 = cy;
        x2 = cx + dx;
        y2 = cy + dy;

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(1));
        g2.draw(new Line2D.Double(x1, y1, x2, y2));
        drawArrowHead(g2, x1, y1, x2, y2);
    }

}
