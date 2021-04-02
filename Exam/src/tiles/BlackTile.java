package tiles;

import java.awt.*;

public class BlackTile extends Tile{

    public BlackTile(int row, int col) {

        setRow(row);
        setCol(col);
        setTileId("blackTile");
        setColor(Color.decode("#444444"));
    }


    @Override
    public void renderTile(Graphics g) {


        int tileSize = 100;
        int tileX = getCol() * tileSize;
        int tileY = getRow() * tileSize;


        g.setColor(Color.BLACK);
        g.fillRect(tileX, tileY, tileSize, tileSize);
        g.setColor(getColor());
        g.fillRect(tileX + 2, tileY + 2, tileSize - 2, tileSize - 2);

    }
}
