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
        this.fillUpTileCollection();
        this.fillUpPlayerPieceCollection(this.getPlayerA());
        this.fillUpPlayerPieceCollection(this.getPlayerB());
        this.setPlayerOnTurn(this.getPlayerA());
        this.setAllTilesToNormal();
        this.spawnObstacles();

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

    public void fillUpTileCollection() {

        fillUpTileArea(2, 0);
        for (int i = 2; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                this.getTileCollection()[i][j] = new RedTile(i, j);

            }
        }
        fillUpTileArea(7, 5);
    }

    public void fillUpPlayerPieceCollection(Player player) {

        player.getPlayerPieceCollection().add(new Knight());
        player.getPlayerPieceCollection().add(new Knight());
        player.getPlayerPieceCollection().add(new Elf());
        player.getPlayerPieceCollection().add(new Elf());
        player.getPlayerPieceCollection().add(new Dwarf());
        player.getPlayerPieceCollection().add(new Dwarf());

    }

    public void switchPlayerOnTurn() {

        if(getPlayerOnTurn().getPlayerId().equals("a")) {

            setPlayerOnTurn(getPlayerB());
        } else {
            setPlayerOnTurn(getPlayerA());
        }
    }

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

    private void updatePlayerAttributes() {

        if(getPlayerOnTurn().getPlayerId().equals("a")) {

           setPlayerA(getPlayerA());

        } else {

            setPlayerB(getPlayerB());
        }
    }

    public boolean isPlacementOnValidSide(int row) {

        if(getPlayerOnTurn().getPlayerId().equals("a")) {

            return row <= 1;
        } else {

            return row >= 5;
        }
    }

    public boolean isSelectedPieceValid(int row, int col, Piece[][] pieceCollection) {

        try {
            return pieceCollection[row][col].getPiecePlayerId()
                    .equals(getPlayerOnTurn().getPlayerId());
        }
        catch (Exception ignored) {
            return false;
        }

    }

    public boolean isThereAPieceHere(int row, int col) {

        return getPieceCollection()[row][col] != null;
    }

    public void executeMove(int newRow, int newCol) {

        int oldRow = getSelectedPiece().getRow();
        int oldCol = getSelectedPiece().getCol();

        if(getSelectedPiece().isMoveInRange(newRow, newCol, getPieceCollection())) {

            getPieceCollection()[newRow][newCol] = getSelectedPiece();
            getSelectedPiece().setRow(newRow);
            getSelectedPiece().setCol(newCol);
            getPieceCollection()[oldRow][oldCol] = null;
            setSelectedPiece(null);


        }
    }

    public void executeAttack(int attackRow, int attackCol) {

        int oldRow = getSelectedPiece().getRow();
        int oldCol = getSelectedPiece().getCol();

        int damage = getPieceCollection()[oldRow][oldCol].getAttackPoints() -
                getPieceCollection()[attackRow][attackCol].getDefensePoints();

        if(damage >= getPieceCollection()[attackRow][attackCol].getHealthPoints()) {

            getPlayerOnTurn().getPlayerDeadPieces().add(getPieceCollection()[attackRow][attackCol]);
            getPieceCollection()[attackRow][attackCol] = getPieceCollection()[oldRow][oldCol];

            getSelectedPiece().setRow(attackRow);
            getSelectedPiece().setCol(attackCol);
            getPieceCollection()[oldRow][oldCol] = null;
        } else {
            int newPoints = getPieceCollection()[attackRow][attackCol].getHealthPoints() - damage;
            getPieceCollection()[attackRow][attackCol].setHealthPoints(newPoints);
        }
        getPlayerOnTurn().addPlayerPoints(damage);
    }

    public void executeHeal(int row, int col) {

        int healthToGive = trowDice(1, 6);
        int newHealth = getPieceCollection()[row][col].getHealthPoints() + healthToGive;
        getPieceCollection()[row][col].setHealthPoints(newHealth);
    }

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

    public void setAllTilesToNormal() {

        for (int i = 0; i < getTileCollection().length; i++) {
            for (int j = 0; j < getTileCollection()[i].length; j++) {

                getTileCollection()[i][j].setTileStateId("normalTile");
            }
        }
    }

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

    private boolean isTileAvailableForPlayer(int row, int col) {

        if(getPlayerOnTurn().getPlayerId().equals("b")) {

            return row <= 1 && getPieceCollection()[row][col] == null;
        } else {

            return row >= 5 && getPieceCollection()[row][col] == null;
        }
    }
    public void getSelectedPieceTile() {

        for (int i = 0; i < getTileCollection().length; i++) {
            for (int j = 0; j < getTileCollection()[i].length; j++) {

                if(getSelectedPiece().getRow() == i && getSelectedPiece().getCol() == j) {

                   getTileCollection()[i][j].setTileStateId("selectedPieceTile");
                }
            }
        }
    }

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

    public boolean isBoxEmpty(int row, int col) {

        return getPieceCollection()[row][col] == null;
    }

    public void setGameWinner(ArrayList<Piece> playerA, ArrayList<Piece> playerB) {

        if(playerA.size() == 0) {

            setPlayerWinner(getPlayerB());
        }
        if(playerB.size() == 0) {

            setPlayerWinner(getPlayerA());
        }
    }
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
}
