package tiles;

import java.awt.*;

public class RedTile extends Tile{

    public RedTile(int row, int col) {

        setRow(row);
        setCol(col);
        setTileId("redTile");
        setColor(Color.decode("#ff6347"));

    }
}
