package tiles;

import java.awt.*;

public abstract class Tile {

    private int row;
    private int col;
    private String tileId;
    private Color color;

    public int getRow() { return row; }

    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }

    public void setCol(int col) { this.col = col; }

    public String getTileId() { return tileId; }

    public void setTileId(String tileId) { this.tileId = tileId; }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }

    public abstract void renderTile(Graphics g);

}
