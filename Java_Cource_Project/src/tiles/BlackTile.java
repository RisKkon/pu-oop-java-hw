package tiles;

import java.awt.*;

public class BlackTile extends Tile{

    public BlackTile(int row, int col) {

        this.setRow(row);
        this.setCol(col);
        this.setTileId("blackTile");
        this.setColor(Color.decode("#444444"));
    }
}
