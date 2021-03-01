package tiles;

import java.awt.*;

public class GreyTile extends Tile {


    public GreyTile(int row, int col) {

        this.setRow(row);
        this.setCol(col);
        this.setTileId("greyTile");
        this.setColor(Color.decode("#aaaaaa"));

    }
}
