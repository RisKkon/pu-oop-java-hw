package gameplay;

import pieces.*;
import tiles.BlackTile;
import tiles.GreyTile;
import tiles.RedTile;
import tiles.Tile;
import ui.Modal;

import javax.swing.*;
import java.util.ArrayList;

public class GameBoard {

    public final int GAME_BOARD_HEIGHT = 7;
    public final int GAME_BOARD_WIDTH = 9;
    private Player playerA;
    private Player playerB;
    private Player playerOnTurn;
    private Player playerWinner;
    private Tile[][] tileCollection;
    private Piece[][] pieceCollection;
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

    public int getTurnCounter() { return turnCounter; }

    public Player getPlayerA() { return playerA; }

    public void setPlayerA(Player playerA) { this.playerA = playerA; }

    public Player getPlayerB() { return playerB; }

    public void setPlayerB(Player playerB) { this.playerB = playerB; }

    public Tile[][] getTileCollection() { return tileCollection; }

    public void setTileCollection(Tile[][] tileCollection) { this.tileCollection = tileCollection; }

    public Piece[][] getPieceCollection() { return pieceCollection; }

    public void setPieceCollection(Piece[][] pieceCollection) { this.pieceCollection = pieceCollection; }

    public void fillUpTileCollection() {

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {

                if (i == 0) {
                    if (j % 2 != 0) {
                        this.getTileCollection()[i][j] = new GreyTile(i, j);
                    } else {
                        this.getTileCollection()[i][j] = new BlackTile(i, j);

                    }
                } else {
                    if (j % 2 != 0) {
                        this.getTileCollection()[i][j] = new BlackTile(i, j);
                    } else {
                        this.getTileCollection()[i][j] = new GreyTile(i, j);
                    }
                }
            }
        }
        for (int i = 2; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                this.getTileCollection()[i][j] = new RedTile(i, j);

            }
        }

        for (int i = 5; i < 7; i++) {
            for (int j = 0; j < 9; j++) {

                if (i == 5) {
                    if (j % 2 != 0) {
                        this.getTileCollection()[i][j] = new GreyTile(i, j);
                    } else {
                        this.getTileCollection()[i][j] = new BlackTile(i, j);

                    }
                } else {

                    if (j % 2 != 0) {
                        this.getTileCollection()[i][j] = new BlackTile(i, j);
                    } else {
                        this.getTileCollection()[i][j] = new GreyTile(i, j);
                    }
                }
            }
        }
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

        if(this.getPlayerOnTurn().getPlayerId().equals("a")) {
            this.setPlayerOnTurn(this.getPlayerB());
        } else {
            this.setPlayerOnTurn(this.getPlayerA());
        }
    }
    public void executeInitialPlacementOnBoard(int row, int col, Piece[][] pieceCollection) {

        int randomNum = this.trowDice(0, this.getPlayerOnTurn().getPlayerPieceCollection().size());

        Piece pieceToPlace = this.getPlayerOnTurn().getPlayerPieceCollection().get(randomNum);
        pieceCollection[row][col] = pieceToPlace;
        pieceToPlace.setRow(row);
        pieceToPlace.setCol(col);
        pieceToPlace.setPiecePlayerId(this.getPlayerOnTurn().getPlayerId());
        this.getPlayerOnTurn().getPlayerPieceCollection().remove(randomNum);
        this.updatePlayerAttributes();
    }

    private void updatePlayerAttributes() {

        if(this.getPlayerOnTurn().getPlayerId().equals("a")) {

            this.setPlayerA(this.getPlayerA());

        } else {

            this.setPlayerB(this.getPlayerB());
        }
    }

    public boolean isPlacementOnValidSide(int row) {

        if(this.getPlayerOnTurn().getPlayerId().equals("a")) {

            return row <= 1;
        } else {

            return row >= 5;
        }
    }

    public boolean isSelectedPieceValid(int row, int col, Piece[][] pieceCollection, JFrame frame) {

        try {
            return pieceCollection[row][col].getPiecePlayerId()
                    .equals(this.getPlayerOnTurn().getPlayerId());
        }
        catch (Exception ignored) {
            return false;
        }

    }

    public boolean isThereAPieceHere(int row, int col) {

        return this.getPieceCollection()[row][col] != null;
    }

    public void executeMove(int newRow, int newCol) {

        int oldRow = this.getSelectedPiece().getRow();
        int oldCol = this.getSelectedPiece().getCol();

        if(this.getSelectedPiece().isMoveInRange(newRow, newCol, this.getPieceCollection())) {

            this.getPieceCollection()[newRow][newCol] = this.getSelectedPiece();
            this.getSelectedPiece().setRow(newRow);
            this.getSelectedPiece().setCol(newCol);
            this.getPieceCollection()[oldRow][oldCol] = null;
            this.setSelectedPiece(null);


        }
    }

    public void executeAttack(int attackRow, int attackCol) {

        int oldRow = this.getSelectedPiece().getRow();
        int oldCol = this.getSelectedPiece().getCol();

        int damage = this.getPieceCollection()[oldRow][oldCol].getAttackPoints() -
                this.getPieceCollection()[attackRow][attackCol].getDefensePoints();

        if(damage >= this.getPieceCollection()[attackRow][attackCol].getHealthPoints()) {

            this.getPlayerOnTurn().getPlayerDeadPieces().add(this.getPieceCollection()[attackRow][attackCol]);
            this.getPieceCollection()[attackRow][attackCol] = this.getPieceCollection()[oldRow][oldCol];
            this.getSelectedPiece().setRow(attackRow);
            this.getSelectedPiece().setCol(attackCol);
            this.getPieceCollection()[oldRow][oldCol] = null;
        } else {
            int newPoints = this.getPieceCollection()[attackRow][attackCol].getHealthPoints() - damage;
            this.getPieceCollection()[attackRow][attackCol].setHealthPoints(newPoints);
        }
        this.getPlayerOnTurn().addPlayerPoints(damage);
    }

    public void executeHeal(int row, int col) {

        int healthToGive = this.trowDice(1, 6);
        int newHealth = this.getPieceCollection()[row][col].getHealthPoints() + healthToGive;
        this.getPieceCollection()[row][col].setHealthPoints(newHealth);
    }

    public void removeDeadPieces() {

        for (int i = 0; i < this.getPieceCollection().length; i++) {
            for (int j = 0; j < this.getPieceCollection()[i].length; j++) {

                if(this.getPieceCollection()[i][j] != null) {

                    int healthPoints = this.getPieceCollection()[i][j].getHealthPoints();

                    if(healthPoints <= 0) {
                        this.getPieceCollection()[i][j] = null;
                    }
                }
            }
        }
    }

    public int trowDice(int min, int max) {

        return (int) (Math.random() * max) + min;
    }

    public void setAllTilesToNormal() {

        for (int i = 0; i < this.getTileCollection().length; i++) {
            for (int j = 0; j < this.getTileCollection()[i].length; j++) {

                this.getTileCollection()[i][j].setTileStateId("normalTile");
            }
        }
    }

    public void showAvailableTiles() {

        for (int i = 0; i < this.getTileCollection().length; i++) {
            for (int j = 0; j < this.getTileCollection()[i].length; j++) {

                if(this.isTileAvailableForPlayer(i, j)) {

                    this.getTileCollection()[i][j].setTileStateId("normalTile");

                } else {

                    this.getTileCollection()[i][j].setTileStateId("setupStageTile");

                }
            }
        }
    }

    private boolean isTileAvailableForPlayer(int row, int col) {

        if(this.getPlayerOnTurn().getPlayerId().equals("b")) {

            return row <= 1 && this.getPieceCollection()[row][col] == null;
        } else {

            return row >= 5 && this.getPieceCollection()[row][col] == null;
        }
    }
    public void getSelectedPieceTile() {

        for (int i = 0; i < this.getTileCollection().length; i++) {
            for (int j = 0; j < this.getTileCollection()[i].length; j++) {

                if(this.getSelectedPiece().getRow() == i &&
                    this.getSelectedPiece().getCol() == j) {

                    this.getTileCollection()[i][j].setTileStateId("selectedPieceTile");
                }
            }
        }
    }

    public boolean isGameOver() {

        ArrayList<Piece> playerA = new ArrayList<>();
        ArrayList<Piece> playerB = new ArrayList<>();

        for (int i = 0; i < this.getPieceCollection().length; i++) {
            for (int j = 0; j < this.getPieceCollection()[i].length; j++) {

                if(this.getPieceCollection()[i][j] != null) {

                    if(this.getPieceCollection()[i][j].getPiecePlayerId() != null) {
                        if (this.getPieceCollection()[i][j].getPiecePlayerId().equals("a")) {
                            playerA.add(this.getPieceCollection()[i][j]);
                        } else {
                            playerB.add(this.getPieceCollection()[i][j]);
                        }
                    }
                }
            }
        }
        this.setGameWinner(playerA, playerB);
        return playerA.size() == 0 || playerB.size() == 0;
    }

    public boolean isBoxEmpty(int row, int col) {

        return this.getPieceCollection()[row][col] == null;
    }

    public void setGameWinner(ArrayList<Piece> playerA, ArrayList<Piece> playerB) {

        if(playerA.size() == 0) {

            this.setPlayerWinner(this.getPlayerB());
        }
        if(playerB.size() == 0) {

            this.setPlayerWinner(this.getPlayerA());
        }
    }
    private void spawnObstacles() {

        int numOfObstacles = this.trowDice(1, 5);
        for (int i = 0; i < numOfObstacles; i++) {
            int row = this.trowDice(2, 3);
            int col = this.trowDice(0, 8);
            this.getPieceCollection()[row][col] = new Obstacle();
            this.getPieceCollection()[row][col].setRow(row);
            this.getPieceCollection()[row][col].setCol(col);
        }
    }
}
