package gameplay;

import pieces.Piece;
import tiles.BlackTile;
import tiles.GreyTile;
import tiles.Tile;

public class GameBoard {

    public int GAME_BOARD_SIDE_SIZE = 12;
    private Player playerA;
    private Player playerB;
    private Player playerOnTurn;
    private Tile[][] tileCollection;
    private Piece[][] pieceCollection;

    public int getGAME_BOARD_SIDE_SIZE() {
        return GAME_BOARD_SIDE_SIZE;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public Player getPlayerOnTurn() {
        return playerOnTurn;
    }

    public Tile[][] getTileCollection() {
        return tileCollection;
    }

    public Piece[][] getPieceCollection() {
        return pieceCollection;
    }

    public GameBoard() {

        this.playerA = new Player();
        this.playerB = new Player();
        this.tileCollection = new Tile[GAME_BOARD_SIDE_SIZE][GAME_BOARD_SIDE_SIZE];
        this.pieceCollection = new Piece[GAME_BOARD_SIDE_SIZE][GAME_BOARD_SIDE_SIZE];
        fillUpTileCollection();

    }

    private void fillUpTileCollection() {

        int counter = 1;
        for (int i = 0; i < getTileCollection().length; i++) {
            for (int j = 0; j < getTileCollection()[i].length; j++) {

                if(counter % 2 == 0) {

                    getTileCollection()[i][j] = new BlackTile(i, j);
                    counter++;
                } else {

                    getTileCollection()[i][j] = new GreyTile(i, j);
                    counter++;
                }
            }
            counter++;
        }
    }
}
