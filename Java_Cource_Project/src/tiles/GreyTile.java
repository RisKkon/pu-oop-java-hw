package tiles;

import java.awt.*;

public class GreyTile extends Tile {


    public GreyTile(int row, int col) {

        setRow(row);
        setCol(col);
        setTileId("greyTile");
        setColor(Color.decode("#aaaaaa"));

    }
}
