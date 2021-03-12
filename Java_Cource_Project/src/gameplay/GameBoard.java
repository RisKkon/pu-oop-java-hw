package gameplay;

import pieces.*;
import tiles.BlackTile;
import tiles.GreyTile;
import tiles.RedTile;
import tiles.Tile;
import java.util.ArrayList;

public class GameBoard {

    public final int GAME_BOARD_HEIGHT = 7;
    public final int GAME_BOARD_WIDTH = 9;
    private Player playerA;
    private Player playerB;
    private Player playerOnTurn;
    private Player playerWinner;
    private final Tile[][] tileCollection;
    private final Piece[][] pieceCollection;
    private Piece selectedPiece;
    public int turnCounter;
    public int roundCounter;

    public GameBoard() {

        this.playerA = new Player("a");
        this.playerB = new Player("b");
        this.turnCounter = 1;
        this.roundCounter = 1;
        this.tileCollection = new Tile[GAME_BOARD_HEIGHT][GAME_BOARD_WIDTH];
        this.pieceCollection = new Piece[GAME_BOARD_HEIGHT][GAME_BOARD_WIDTH];
        fillUpTileCollection();
        fillUpPlayerPieceCollection(this.getPlayerA());
        fillUpPlayerPieceCollection(this.getPlayerB());
        setPlayerOnTurn(this.getPlayerA());
        setAllTilesToNormal();
        spawnObstacles();
    }

    public Player getPlayerWinner() { return playerWinner; }

    public void setPlayerWinner(Player playerWinner) { this.playerWinner = playerWinner; }

    public int getRoundCounter() { return roundCounter; }

    public Piece getSelectedPiece() { return selectedPiece; }

    public void setSelectedPiece(Piece selectedPiece) { this.selectedPiece = selectedPiece; }

    public Player getPlayerOnTurn() { return playerOnTurn; }

    public void setPlayerOnTurn(Player playerOnTurn) { this.playerOnTurn = playerOnTurn; }

    public Player getPlayerA() { return playerA; }

    public void setPlayerA(Player playerA) { this.playerA = playerA; }

    public Player getPlayerB() { return playerB; }

    public void setPlayerB(Player playerB) { this.playerB = playerB; }

    public Tile[][] getTileCollection() { return tileCollection; }

    public Piece[][] getPieceCollection() { return pieceCollection; }


    /**
     * Fill's up the Game Board with the correct tiles.
     */

    public void fillUpTileCollection() {

        //Fill's up the player "a" area.
        fillUpTileArea(2, 0);
        //Fill's up the middle area.
        for (int i = 2; i < 5; i++) {
            for (int j = 0; j < 9; j++) {

                this.getTileCollection()[i][j] = new RedTile(i, j);
            }
        }
        //Fill's up the player "b" area.
        fillUpTileArea(7, 5);
    }

    /**
     *This method helps with the tiles setup.
     * @param maxRow
     * @param row
     */
    private void fillUpTileArea(int maxRow, int row) {

        for (int i = row; i < maxRow; i++) {
            for (int j = 0; j < 9; j++) {

                if (i == 0) {
                    if (j % 2 != 0) {
                        getTileCollection()[i][j] = new GreyTile(i, j);
                    } else {
                        getTileCollection()[i][j] = new BlackTile(i, j);

                    }
                } else {
                    if (j % 2 != 0) {
                        getTileCollection()[i][j] = new BlackTile(i, j);
                    } else {
                        getTileCollection()[i][j] = new GreyTile(i, j);
                    }
                }
            }
        }
    }

    /**
     * Fill's up the piece collection for the certain player.
     * @param player
     */
    public void fillUpPlayerPieceCollection(Player player) {

        player.getPlayerPieceCollection().add(new Knight());
        player.getPlayerPieceCollection().add(new Knight());
        player.getPlayerPieceCollection().add(new Elf());
        player.getPlayerPieceCollection().add(new Elf());
        player.getPlayerPieceCollection().add(new Dwarf());
        player.getPlayerPieceCollection().add(new Dwarf());

    }

    /**
     * Goes over the game board and set's all the tiles to their normal state.
     */
    public void setAllTilesToNormal() {

        for (int i = 0; i < getTileCollection().length; i++) {
            for (int j = 0; j < getTileCollection()[i].length; j++) {

                getTileCollection()[i][j].setTileStateId("normalTile");
            }
        }
    }

    /**
     * Spawn's a random number of obstacles on the game board.
     */
    private void spawnObstacles() {

        int numOfObstacles = trowDice(1, 5);

        for (int i = 0; i < numOfObstacles; i++) {

            int row = trowDice(2, 3);
            int col = trowDice(0, 8);

            getPieceCollection()[row][col] = new Obstacle();
            getPieceCollection()[row][col].setRow(row);
            getPieceCollection()[row][col].setCol(col);
        }
    }

    /**
     * Executes placement of a random piece from the player piece collection on the game board.
     * @param row
     * @param col
     * @param pieceCollection
     */
    public void executeInitialPlacementOnBoard(int row, int col, Piece[][] pieceCollection) {

        int randomNum = trowDice(0, getPlayerOnTurn().getPlayerPieceCollection().size());
        Piece pieceToPlace = getPlayerOnTurn().getPlayerPieceCollection().get(randomNum);

        pieceCollection[row][col] = pieceToPlace;
        pieceToPlace.setRow(row);
        pieceToPlace.setCol(col);
        pieceToPlace.setPiecePlayerId(getPlayerOnTurn().getPlayerId());

        getPlayerOnTurn().getPlayerPieceCollection().remove(randomNum);
        updatePlayerAttributes();
    }

    /**
     * Used to update the player attributes from the player on turn and a certain player.
     */
    private void updatePlayerAttributes() {

        if(getPlayerOnTurn().getPlayerId().equals("a")) {

            setPlayerA(getPlayerA());

        } else {

            setPlayerB(getPlayerB());
        }
    }

    /**
     * Switches the player on turn.
     */
    public void switchPlayerOnTurn() {

        if(getPlayerOnTurn().getPlayerId().equals("a")) {

            setPlayerOnTurn(getPlayerB());
        } else {
            setPlayerOnTurn(getPlayerA());
        }
    }

    /**
     * Checks if the player has chosen valid coordinates to place a piece.
     * @param row
     * @return
     */
    public boolean isPlacementOnValidSide(int row) {

        if(getPlayerOnTurn().getPlayerId().equals("a")) {

            return row <= 1;
        }

        return row >= 5;
    }

    /**
     * Checks if the player has selected a valid piece.
     * @param row
     * @param col
     * @param pieceCollection
     * @return
     */
    public boolean isSelectedPieceValid(int row, int col, Piece[][] pieceCollection) {

        try {
            return pieceCollection[row][col].getPiecePlayerId()
                    .equals(getPlayerOnTurn().getPlayerId());
        }
        catch (Exception ignored) {
            return false;
        }

    }

    /**
     * Checks if there is a piece on the given coordinates.
     * @param row
     * @param col
     * @return
     */
    public boolean isThereAPieceHere(int row, int col) {

        return getPieceCollection()[row][col] != null;
    }

    /**
     * Executes a move on the game board.
     * @param newRow
     * @param newCol
     */
    public void executeMove(int newRow, int newCol) {

        int oldRow = getSelectedPiece().getRow();
        int oldCol = getSelectedPiece().getCol();

        getPieceCollection()[newRow][newCol] = getSelectedPiece();
        getSelectedPiece().setRow(newRow);
        getSelectedPiece().setCol(newCol);
        getPieceCollection()[oldRow][oldCol] = null;
        setSelectedPiece(null);
    }

    /**
     * Executes an attack on the game board.
     * @param attackRow
     * @param attackCol
     */
    public void executeAttack(int attackRow, int attackCol) {

        int oldRow = getSelectedPiece().getRow();
        int oldCol = getSelectedPiece().getCol();

        int damage = getPieceCollection()[oldRow][oldCol].getAttackPoints() -
                getPieceCollection()[attackRow][attackCol].getDefensePoints();

        if(damage >= getPieceCollection()[attackRow][attackCol].getHealthPoints()) {

            //If the attacked piece is going to die.
            getPlayerOnTurn().getPlayerDeadPieces().add(getPieceCollection()[attackRow][attackCol]);
            getPieceCollection()[attackRow][attackCol] = getPieceCollection()[oldRow][oldCol];

            getSelectedPiece().setRow(attackRow);
            getSelectedPiece().setCol(attackCol);
            getPieceCollection()[oldRow][oldCol] = null;
        } else {
            //If the attacked piece is going to survive.
            int newPoints = getPieceCollection()[attackRow][attackCol].getHealthPoints() - damage;
            getPieceCollection()[attackRow][attackCol].setHealthPoints(newPoints);
        }
        getPlayerOnTurn().addPlayerPoints(damage);
    }

    /**
     * Executes healing for a piece.
     * @param row
     * @param col
     */
    public void executeHeal(int row, int col) {

        int healthToGive = trowDice(1, 6);
        int newHealth = getPieceCollection()[row][col].getHealthPoints() + healthToGive;
        getPieceCollection()[row][col].setHealthPoints(newHealth);
    }

    /**
     * Removes all the pieces that have fallen in this round of battle.
     */
    public void removeDeadPieces() {

        for (int i = 0; i < getPieceCollection().length; i++) {
            for (int j = 0; j < getPieceCollection()[i].length; j++) {

                if(getPieceCollection()[i][j] != null &&
                        !getPieceCollection()[i][j].getPieceId().equals("obstacle")) {

                    int healthPoints = getPieceCollection()[i][j].getHealthPoints();

                    if(healthPoints <= 0) {
                        getPieceCollection()[i][j] = null;
                    }
                }
            }
        }
    }

    public int trowDice(int min, int max) {

        return (int) (Math.random() * max) + min;
    }

    /**
     * Shows on the game board all the available moves a player can execute.
     */
    public void showAvailableTiles() {

        for (int i = 0; i < getTileCollection().length; i++) {
            for (int j = 0; j < getTileCollection()[i].length; j++) {

                if(isTileAvailableForPlayer(i, j)) {

                    getTileCollection()[i][j].setTileStateId("normalTile");

                } else {

                    getTileCollection()[i][j].setTileStateId("setupStageTile");

                }
            }
        }
    }

    /**
     * Checks if a tile is in range for the player.
     * @param row
     * @param col
     * @return
     */
    private boolean isTileAvailableForPlayer(int row, int col) {

        if(getPlayerOnTurn().getPlayerId().equals("b")) {

            return row <= 1 && getPieceCollection()[row][col] == null;

        }
        return row >= 5 && getPieceCollection()[row][col] == null;
    }

    /**
     * Changes the tile of the selected piece.
     */
    public void getSelectedPieceTile() {

        for (int i = 0; i < getTileCollection().length; i++) {
            for (int j = 0; j < getTileCollection()[i].length; j++) {

                if(getSelectedPiece().getRow() == i && getSelectedPiece().getCol() == j) {

                   getTileCollection()[i][j].setTileStateId("selectedPieceTile");
                }
            }
        }
    }

    /**
     * Sets the game winner.
     * @param playerA
     * @param playerB
     */
    public void setGameWinner(ArrayList<Piece> playerA, ArrayList<Piece> playerB) {

        if(playerA.size() == 0) {

            setPlayerWinner(getPlayerB());
        }
        if(playerB.size() == 0) {

            setPlayerWinner(getPlayerA());
        }
    }

    /**
     * Checks if one of the players has no pieces left.
     * @return
     */
    public boolean isGameOver() {

        ArrayList<Piece> playerA = new ArrayList<>();
        ArrayList<Piece> playerB = new ArrayList<>();

        for (int i = 0; i < getPieceCollection().length; i++) {
            for (int j = 0; j < getPieceCollection()[i].length; j++) {

                if(getPieceCollection()[i][j] != null) {

                    if(getPieceCollection()[i][j].getPiecePlayerId() != null) {
                        if (getPieceCollection()[i][j].getPiecePlayerId().equals("a")) {
                            playerA.add(getPieceCollection()[i][j]);
                        } else {
                            playerB.add(getPieceCollection()[i][j]);
                        }
                    }
                }
            }
        }

        setGameWinner(playerA, playerB);
        return playerA.size() == 0 || playerB.size() == 0;
    }
}
