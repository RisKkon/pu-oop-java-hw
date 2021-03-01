package tiles;

import java.awt.*;

public class RedTile extends Tile{

    public RedTile(int row, int col) {

        this.setRow(row);
        this.setCol(col);
        this.setTileId("redTile");
        this.setColor(Color.decode("#ff6347"));

    }
}
