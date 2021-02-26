package resources.tiles;

import java.awt.*;

public abstract class Tile {

    private int row;
    private int col;
    private String id;
    private Color color;
    private boolean isWitchHere;

    public Tile(int row, int col, String id) {
        this.row = row;
        this.col = col;
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void render(Graphics g);

    /**
     *When the user selects a tile, this method
     * changes the color of the tile and draws a
     * question mark.
     */
    public void onClick(Graphics g) {

        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;

        g.setColor(Color.BLACK);
        g.setFont(Font.decode("Serif, Font.BOLD, 40"));
        g.drawString("?",tileX + 37,tileY + 65);
    }

    public boolean isWitchHere() {
        return isWitchHere;
    }

    public void setWitchHere(boolean witchHere) {
        isWitchHere = witchHere;
    }
}
