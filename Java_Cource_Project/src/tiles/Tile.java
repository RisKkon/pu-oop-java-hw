package tiles;
import java.awt.*;

public abstract class Tile {

    private int row;
    private int col;
    private String tileId;
    private Color color;
    private String tileStateId;

    public String getTileStateId() { return tileStateId; }

    public void setTileStateId(String tileStateId) { this.tileStateId = tileStateId; }

    public int getRow() { return row; }

    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }

    public void setCol(int col) { this.col = col; }

    public String getTileId() { return tileId; }

    public void setTileId(String tileId) { this.tileId = tileId; }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }

    public  void renderTile(Graphics g) {

        switch (this.getTileStateId()) {
            case "normalTile" -> this.normalTileRender(g);
            case "setupStageTile" -> this.setupStageTileRender(g);
            case "availableBoxToMoveTile" -> this.availableBoxToMoveTileRender(g);
            case "selectedPieceTile" -> this.selectedPieceTileRender(g);
        }
    }

    public void normalTileRender(Graphics g) {

        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;


        g.setColor(Color.BLACK);
        g.fillRect(tileX, tileY, tileSize, tileSize);
        g.setColor(this.getColor());
        g.fillRect(tileX + 2, tileY + 2, tileSize - 2, tileSize - 2);

    }

    public void setupStageTileRender(Graphics g) {

        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;


        g.setColor(Color.BLACK);
        g.fillRect(tileX, tileY, tileSize, tileSize);
        g.setColor(Color.decode("#a3262a"));
        g.fillRect(tileX + 2, tileY + 2, tileSize - 2, tileSize - 2);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("X", tileX + 38, tileY + 60);
    }

    public void availableBoxToMoveTileRender(Graphics g) {

        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;

        g.setColor(Color.BLACK);
        g.fillRect(tileX, tileY, tileSize, tileSize);
        g.setColor(Color.decode("#a141f0"));
        g.fillRect(tileX + 2, tileY + 2, tileSize - 2, tileSize - 2);

    }

    public void selectedPieceTileRender(Graphics g) {

        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;

        g.setColor(Color.BLACK);
        g.fillRect(tileX, tileY, tileSize, tileSize);
        g.setColor(Color.decode("#322599"));
        g.fillRect(tileX + 2, tileY + 2, tileSize - 2, tileSize - 2);


    }
}
