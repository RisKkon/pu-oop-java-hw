import java.awt.*;

public class Tile {

    private int row;
    private int col;
    private String tileType;

    public Tile(int row, int col, String tileType) {
        this.row = row;
        this.col = col;
        this.tileType = tileType;
    }

    public void renderTile(Graphics g) {

        //Create all the needed variables
        int titleBarPixels = 40;
        int tileSize = 100;
        int tileX = this.col * tileSize;
        int tileY = this.row * tileSize + titleBarPixels;

        //Render the right tile (depends on tileType)
        switch(this.tileType) {

            case "yellowGuard1": {

                g.setColor(Color.decode("#ff6347"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#36b745"));
                g.fillOval(tileX + 25, tileY + 20, tileSize - 45, tileSize - 45);
                g.setColor(Color.decode("#fff200"));
                g.fillOval(tileX + 30, tileY + 25, tileSize - 55, tileSize - 55);
                break;
            }
            case "yellowGuard2": {

                g.setColor(Color.decode("#444444"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#36b745"));
                g.fillOval(tileX + 25, tileY + 20, tileSize - 45, tileSize - 45);
                g.setColor(Color.decode("#fff200"));
                g.fillOval(tileX + 30, tileY + 25, tileSize - 55, tileSize - 55);
                break;
            }

            case "yellowGuard3": {

                g.setColor(Color.decode("#ffffff"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#36b745"));
                g.fillOval(tileX + 25, tileY + 20, tileSize - 45, tileSize - 45);
                g.setColor(Color.decode("#fff200"));
                g.fillOval(tileX + 30, tileY + 25, tileSize - 55, tileSize - 55);
                break;
            }
            case "yellowBoss": {

                g.setColor(Color.decode("#ff6347"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#fff200"));
                g.fillRect(tileX + 22, tileY + 20, tileSize - 40, tileSize - 40);
                break;
            }
            case "greyTile": {

                g.setColor(Color.decode("#aaaaaa"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                break;
            }
            case "whiteTile": {

                g.setColor(Color.decode("#ffffff"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                break;
            }
            case "middleTile": {

                g.setColor(Color.decode("#ffffff"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#777777"));
                g.fillOval(tileX + 25, tileY + 20, tileSize - 45, tileSize - 45);
                break;
            }
            case "greenBoss": {

                g.setColor(Color.decode("#444444"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#22b14c"));
                g.fillRect(tileX + 22, tileY + 20, tileSize - 40, tileSize - 40);
                break;
            }
            case "greenGuard1": {

                g.setColor(Color.decode("#ff6347"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#fff200"));
                g.fillOval(tileX + 25, tileY + 20, tileSize - 45, tileSize - 45);
                g.setColor(Color.decode("#22b14c"));
                g.fillOval(tileX + 30, tileY + 25, tileSize - 55, tileSize - 55);
                break;
            }

            case "greenGuard2": {

                g.setColor(Color.decode("#ffffff"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#fff316"));
                g.fillOval(tileX + 25, tileY + 20, tileSize - 45, tileSize - 45);
                g.setColor(Color.decode("#22b14c"));
                g.fillOval(tileX + 30, tileY + 25, tileSize - 55, tileSize - 55);
                break;
            }
            case "greenGuard3": {

                g.setColor(Color.decode("#444444"));
                g.fillRect(tileX, tileY, tileSize, tileSize);
                g.setColor(Color.decode("#fef100"));
                g.fillOval(tileX + 25, tileY + 20, tileSize - 45, tileSize - 45);
                g.setColor(Color.decode("#22b14c"));
                g.fillOval(tileX + 30, tileY + 25, tileSize - 55, tileSize - 55);
                break;
            }
        }
    }
}
