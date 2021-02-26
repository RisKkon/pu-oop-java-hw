package tiles;

import java.awt.*;

public class GreyTile extends Tile {


    public GreyTile(int row, int col) {

        this.setRow(row);
        this.setCol(col);
        this.setTileId("greyTile");
        this.setColor(Color.decode("#aaaaaa"));
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
