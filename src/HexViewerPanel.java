import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class HexViewerPanel extends JPanel {
    private static final int HEX_SIZE = 10; //radius of hexagon
    private static final double WIDTH = Math.sqrt(3) * HEX_SIZE;
    private static final double HEIGHT = 2 * HEX_SIZE;
    private BufferedImage gridImage;

    private final Hexagon[][] hexGrid;

    public HexViewerPanel(Hexagon[][] hexGrid) {
        this.hexGrid = hexGrid;
        setPreferredSize(new Dimension(2560, 1600));
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

        // Always draw the dynamic arrows
        drawArrows(g2);
    }

    private void drawGrid(Graphics2D g2) {
        for (int row = 0; row < hexGrid[0].length; row++) {
            for (int col = 0; col < hexGrid.length; col++) {

                double x = col * WIDTH * 0.5;
                double y = row * HEIGHT * 0.5 - (col % 2 == 0 ? 0 : HEIGHT / 4);

                drawHex(g2, x, y);
            }
        }
    }

    private void drawArrows(Graphics2D g2) {
        for (int row = 0; row < hexGrid[0].length; row++) {
            for (int col = 0; col < hexGrid.length; col++) {
                Hexagon hex = hexGrid[col][row];

                double x = col * WIDTH * 0.5;
                double y = row * HEIGHT * 0.5 - (col % 2 == 0 ? 0 : HEIGHT / 4);

                drawHexEdges(g2, x, y, hex);
            }
        }
    }


    private void drawHex(Graphics2D g2, double x, double y) {
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

    private void drawHexEdges(Graphics2D g2, double cx, double cy, Hexagon hex) {
        Edge[] edges = hex.getEdges();
        Hexagon.eddieDir[] d = hex.getHexagonEddies();

        cx += WIDTH / 2;
        cy += 3 * HEIGHT / 4 ;

        for (int i = 0; i < edges.length; i++) {

            if (d[i] == null){
                drawArrow(g2, cx, cy, i, hex.getEddie(i),true);
                continue;
            } else if(hex.getEddie(i) == Hexagon.eddieDir.NA)
                continue;

            drawArrow(g2, cx, cy, i, hex.getEddie(i),false);
        }
    }

    private void drawArrow(Graphics2D g2, double cx, double cy, int edgeIndex, Hexagon.eddieDir direction, boolean wall) {

        double angle = Math.toRadians(270 - 60 * edgeIndex);
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
        }else
            g2.setColor(Color.RED); //direction == Hexagon.eddieDir.FC ? Color.BLUE : Color.RED

        g2.setStroke(new BasicStroke(1));
        g2.draw(new Line2D.Double(x1, y1, x2, y2));
        drawArrowHead(g2, x1, y1, x2, y2);
    }

    private void drawArrowHead(Graphics2D g2, double x1, double y1, double x2, double y2) {
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


