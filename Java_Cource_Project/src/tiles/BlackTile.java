package tiles;

import java.awt.*;

public class BlackTile extends Tile{

    public BlackTile(int row, int col) {

        setRow(row);
        setCol(col);
        setTileId("blackTile");
        setColor(Color.decode("#444444"));
    }
}
