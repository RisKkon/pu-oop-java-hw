package tiles;

import java.awt.*;

public class GreyTile extends Tile {


    public GreyTile(int row, int col) {

        this.row = row;
        this.col = col;
        this.color = Color.decode("#aaaaaa");
    }
}
