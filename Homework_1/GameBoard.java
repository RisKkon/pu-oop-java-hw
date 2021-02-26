import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBoard extends JFrame {

    private ArrayList<Tile> tiles;

    public GameBoard() {

        //Create the game window
        this.setSize(508, 546);
        this.setVisible(true);
        this.setTitle("FrogWars");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //Shitty hardcoding below
        tiles = new ArrayList<>();
        tiles.add(new Tile(0, 0, "yellowGuard1"));
        tiles.add(new Tile(0, 1, "yellowGuard2"));
        tiles.add(new Tile(0, 2, "yellowGuard3"));
        tiles.add(new Tile(0, 3, "yellowGuard2"));
        tiles.add(new Tile(0, 4, "yellowBoss"));
        tiles.add(new Tile(1, 0, "greyTile"));
        tiles.add(new Tile(1, 1, "greyTile"));
        tiles.add(new Tile(1, 2, "whiteTile"));
        tiles.add(new Tile(1, 3, "greyTile"));
        tiles.add(new Tile(1, 4, "greyTile"));
        tiles.add(new Tile(2, 0, "whiteTile"));
        tiles.add(new Tile(2, 1, "whiteTile"));
        tiles.add(new Tile(2, 2, "middleTile"));
        tiles.add(new Tile(2, 3, "whiteTile"));
        tiles.add(new Tile(2, 4, "whiteTile"));
        tiles.add(new Tile(3, 0, "greyTile"));
        tiles.add(new Tile(3, 1, "greyTile"));
        tiles.add(new Tile(3, 2, "whiteTile"));
        tiles.add(new Tile(3, 3, "greyTile"));
        tiles.add(new Tile(3, 4, "greyTile"));
        tiles.add(new Tile(4, 0, "greenBoss"));
        tiles.add(new Tile(4, 1, "greenGuard1"));
        tiles.add(new Tile(4, 2, "greenGuard2"));
        tiles.add(new Tile(4, 3, "greenGuard1"));
        tiles.add(new Tile(4, 4, "greenGuard3"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //Iterates over all the tiles and renders them
        for(Tile tile: tiles) {

            tile.renderTile(g);
        }
    }
}
