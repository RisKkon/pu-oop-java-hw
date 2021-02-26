package tiles;

import java.awt.*;

public abstract class Tile {

    public static final int TILE_SIZE = 100;

    int row;
    int col;
    Color color;

    /***
     * Renders a tile to the screen
     */
    public void renderTile(Graphics g) {

        int tileX = this.col * TILE_SIZE;
        int tileY = this.row * TILE_SIZE;

        g.setColor(this.color);
        g.fillRect(tileX, tileY, TILE_SIZE, TILE_SIZE);
    }
}
