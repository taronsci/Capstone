import java.awt.*;

public class HexViewerPanelIndiv extends HexViewerPanel{


    public HexViewerPanelIndiv(Hexagon[][] hexGrid){
        super(hexGrid);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Always draw the dynamic arrows
        drawArrows(g2); //no average
        count++;
    }

    private void drawArrows(Graphics2D g2) {
        for (int row = 0; row < hexGrid[0].length; row++) {
            for (int col = 0; col < hexGrid.length; col++) {
                Hexagon hex = hexGrid[col][row];

                //without the sums: top left of hexagon (nw), with sums: center of hexagon
                double x = col * WIDTH * 0.5 + WIDTH / 2;
                double y = row * HEIGHT * 0.5 - (col % 2 == 0 ? 0 : HEIGHT / 4) + 3 * HEIGHT / 4;

                drawHexEdges(g2, x, y, hex,false);
            }
        }
    }

}
