package tiles;

import java.awt.*;

public class RedTile extends Tile{

    public RedTile(int row, int col) {

        this.setRow(row);
        this.setCol(col);
        this.setTileId("redTile");
        this.setColor(Color.decode("#ff6347"));
    }

    @Override
    public void renderTile(Graphics g) {


        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;


        g.setColor(Color.BLACK);
        g.fillRect(tileX, tileY, tileSize, tileSize);
        g.setColor(this.getColor());
        g.fillRect(tileX + 2, tileY + 2, tileSize - 2, tileSize - 2);
    }
}
