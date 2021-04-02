package gameplay;

import pieces.Piece;
import tiles.BlackTile;
import tiles.GreyTile;
import tiles.Tile;

public class GameBoard {

    public int GAME_BOARD_SIDE_SIZE = 10;
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



    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }

    public void setPlayerOnTurn(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
    }

    public void setTileCollection(Tile[][] tileCollection) {
        this.tileCollection = tileCollection;
    }

    public void setPieceCollection(Piece[][] pieceCollection) {
        this.pieceCollection = pieceCollection;
    }

    public GameBoard() {

        this.playerA = new Player("a");
        this.playerB = new Player("b");
        this.tileCollection = new Tile[GAME_BOARD_SIDE_SIZE][GAME_BOARD_SIDE_SIZE];
        this.pieceCollection = new Piece[GAME_BOARD_SIDE_SIZE][GAME_BOARD_SIDE_SIZE];
        fillUpTileCollection();
        this.setPlayerOnTurn(getPlayerA());

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

    public int trowDice(int min, int max) {

        return (int) (Math.random() * max) + min;
    }

    public void switchPlayerOnTurn() {

        if(getPlayerOnTurn().getPlayerId().equals("a")) {
           setPlayerOnTurn(getPlayerB());
        }else {

            setPlayerOnTurn(getPlayerA());
        }
    }
}
