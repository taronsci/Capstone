import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class HexViewerPanel extends JPanel {
    static final int HEX_SIZE = 7; //radius of hexagon
    static final double WIDTH = Math.sqrt(3) * HEX_SIZE;
    static final double HEIGHT = 2 * HEX_SIZE;
    private BufferedImage gridImage;

    int count = 0;
    final Hexagon[][] hexGrid;

    static final double[][] e = {
            {0.0, 1.0},                                // e1-0
            {-Math.sqrt(3) / 2, 0.5},                   // e2
            {-Math.sqrt(3) / 2, -0.5},                  // e3
            {0.0, -1.0},                               // e4
            {Math.sqrt(3) / 2, -0.5},                 // e5
            {Math.sqrt(3) / 2, 0.5}                   // e6
    };

    public HexViewerPanel(Hexagon[][] hexGrid) {
        this.hexGrid = hexGrid;
        setPreferredSize(new Dimension(2550, 1590)); //2560,1600
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Only draw the hexagon outlines ONCE (optional optimization)
        if (gridImage == null) {
            gridImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D gGrid = gridImage.createGraphics();
            gGrid.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawGrid(gGrid);
            gGrid.dispose();
        }
        g2.drawImage(gridImage, 0, 0, null);

//        // Always draw the dynamic arrows
//        if(count == 1000) {
//            drawAverageArrows(g2, 6); //average
//        } else {
//            drawArrows(g2); //no average
//            count++;
//        }
    }

    public void drawGrid(Graphics2D g2) {
        for (int row = 0; row < hexGrid[0].length; row++) {
            for (int col = 0; col < hexGrid.length; col++) {

                double x = col * WIDTH * 0.5;
                double y = row * HEIGHT * 0.5 - (col % 2 == 0 ? 0 : HEIGHT / 4);

                drawHex(g2, x, y);
                drawHexEdges(g2,x + WIDTH / 2,y + 3 * HEIGHT / 4, hexGrid[col][row],false);
            }
        }
    }

//    private void drawArrows(Graphics2D g2) {
//        for (int row = 0; row < hexGrid[0].length; row++) {
//            for (int col = 0; col < hexGrid.length; col++) {
//                Hexagon hex = hexGrid[col][row];
//
//                //without the sums: top left of hexagon (nw), with sums: center of hexagon
//                double x = col * WIDTH * 0.5 + WIDTH / 2;
//                double y = row * HEIGHT * 0.5 - (col % 2 == 0 ? 0 : HEIGHT / 4) + 3 * HEIGHT / 4;
//
//                drawHexEdges(g2, x, y, hex,false);
//            }
//        }
//    }

//    private void drawAverageArrows(Graphics2D g2, int cellSize) {
//        double[] momentum;
//
//        double[] nums;
//        Hexagon hex;
//        double center_x;
//        double center_y;
//        int density; //mean number of particles per cell
//        double[] velocity;
//
//        for(int k1 = 0; k1 < hexGrid.length; k1 += cellSize){
//            for(int k2 = 0; k2 < hexGrid[0].length; k2 += cellSize) {
//                density = 0;
//                momentum = new double[2];
//
//                for (int row = k1; row < Math.min(k1 + cellSize, hexGrid.length) ; row ++) {
//                    for (int col = k2; col < Math.min(k2 + cellSize,hexGrid[0].length)  ; col ++) {
//                        hex = hexGrid[row][col];
//
//                        double x = col * WIDTH * 0.5 + WIDTH / 2;
//                        double y = row * HEIGHT * 0.5 - (col % 2 == 0 ? 0 : HEIGHT / 4) + 3 * HEIGHT / 4;
//
//                        nums = drawHexEdges(g2, x, y, hex,true);
//                        density += (int) nums[0]; //adding densities of all sites inside the cell
//                        momentum[0] += nums[1]; //adding x components of momentum
//                        momentum[1] += nums[2];
//                    }
//                }
//
//                density /= (cellSize * cellSize); //average density by site 16/36
//                velocity = new double[]{momentum[0] / density, momentum[1] / density};
//
//                center_x = (k1 + (double) cellSize /2) * WIDTH * 0.5 + WIDTH / 2;
//                center_y = (k2+ (double) cellSize /2) * HEIGHT * 0.5 - ((k1 + (double) cellSize /2) % 2 == 0 ? 0 : HEIGHT / 4) + 3 * HEIGHT / 4;
//
//                drawAvArrow(g2, center_x, center_y, velocity);
//            }
//        }
//    }


    public void drawHex(Graphics2D g2, double x, double y) {
        Path2D hex = new Path2D.Double();

        x += WIDTH / 2;
        y += 3 * HEIGHT / 4 ;

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 30);

            double dx = HEX_SIZE * Math.cos(angle);
            double dy = HEX_SIZE * Math.sin(angle);

            if (i == 0)
                hex.moveTo(x + dx, y + dy);
            else
                hex.lineTo(x + dx, y + dy);
        }
        hex.closePath();
        g2.setColor(Color.LIGHT_GRAY);
        g2.draw(hex);
    }

     public double[] drawHexEdges(Graphics2D g2, double cx, double cy, Hexagon hex, boolean average) {
        Edge[] edges = hex.getEdges();
        Hexagon.eddieDir[] d = hex.getHexagonEddies();

        double ro_site = 0; //sum of 6 occupational numbers n_i
        double vel_sum_x = 0;
        double vel_sum_y = 0;

        for (int i = 0; i < edges.length; i++) {

            if(count == 0 && d[i] == null) {
                drawArrow(g2, cx, cy, i, hex.getEddie(i), true); //draw wall
                continue;
            }

            if(average && hex.getEddie(i) == Hexagon.eddieDir.FC){ // n_i == 1
                ro_site += 1;
                vel_sum_x += e[i][0];
                vel_sum_y += e[i][1];
            }

            if (d[i] == null){
//                drawArrow(g2, cx, cy, i, hex.getEddie(i),true); //draw wall
                continue;
            } else
                if(hex.getEddie(i) == Hexagon.eddieDir.NA) //draw nothing
                    continue;

            if(!average && count != 0 )
                drawArrow(g2, cx, cy, i, hex.getEddie(i), false); //draw red arrows
        }

        return new double[]{ro_site, vel_sum_x, vel_sum_y};
    }

    private void drawArrow(Graphics2D g2, double cx, double cy, int edgeIndex, Hexagon.eddieDir direction, boolean wall) {
        double angle;
        angle = Math.toRadians(270 - 60 * edgeIndex);

        double dx = HEX_SIZE * Math.cos(angle);
        double dy = HEX_SIZE * Math.sin(angle);

        double x1, y1, x2, y2;

        if (direction == Hexagon.eddieDir.FC) {
            x1 = cx;
            y1 = cy;
            x2 = cx + dx;
            y2 = cy + dy;
        } else {
            x1 = cx + dx;
            y1 = cy + dy;
            x2 = cx;
            y2 = cy;
        }

        if(wall) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));

        }else {
            g2.setColor(Color.RED); //direction == Hexagon.eddieDir.FC ? Color.BLUE : Color.RED
            g2.setStroke(new BasicStroke(1));
        }

        g2.setStroke(new BasicStroke(1));
        g2.draw(new Line2D.Double(x1, y1, x2, y2));
        drawArrowHead(g2, x1, y1, x2, y2);
    }

//    private void drawAvArrow(Graphics2D g2, double cx, double cy, double[] velocity) {
//        double angle = Math.atan2(velocity[1], velocity[0]);
//        double magnitude = Math.sqrt(Math.pow(velocity[0], 2) + Math.pow(velocity[1], 2));
//
//        double dx = Math.cos(angle) * magnitude;
//        double dy = Math.sin(angle) * magnitude;
//
//        double x1, y1, x2, y2;
//
//        x1 = cx;
//        y1 = cy;
//        x2 = cx + dx;
//        y2 = cy + dy;
//
//        g2.setColor(Color.BLUE);
//        g2.setStroke(new BasicStroke(2));
//        g2.draw(new Line2D.Double(x1, y1, x2, y2));
//        drawArrowHead(g2, x1, y1, x2, y2);
//    }

    void drawArrowHead(Graphics2D g2, double x1, double y1, double x2, double y2) {
        double phi = Math.toRadians(25);
        int barb = 3;

        double dx = x2 - x1;
        double dy = y2 - y1;
        double theta = Math.atan2(dy, dx);

        double x, y;
        for (int j = 0; j < 2; j++) {
            double rho = theta + (j == 0 ? phi : -phi);
            x = x2 - barb * Math.cos(rho);
            y = y2 - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(x2, y2, x, y));
        }
    }

}


