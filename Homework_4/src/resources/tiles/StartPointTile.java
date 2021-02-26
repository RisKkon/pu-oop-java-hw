package resources.tiles;

import java.awt.*;

public class StartPointTile extends Tile {

    public StartPointTile(int row, int col, String id) {
        super(row, col, id);

        this.setColor(Color.decode("#f2ff00"));
    }

    @Override
    public void render(Graphics g) {

        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;

        g.setColor(Color.BLACK);
        g.fillRect(tileX, tileY, tileSize, tileSize);
        g.setColor(this.getColor());
        g.fillRect(tileX + 2,tileY + 2, tileSize - 5, tileSize - 5);
    }
}
