package tiles;

import java.awt.*;

public class WhiteTile extends Tile {


    public WhiteTile(int row, int col) {

        this.row = row;
        this.col = col;
        this.color = Color.decode("#ffffff");
    }
}
