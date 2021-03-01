package gameplay;

import jdk.jshell.Snippet;
import pieces.Dwarf;
import pieces.Elf;
import pieces.Knight;
import pieces.Piece;
import tiles.BlackTile;
import tiles.GreyTile;
import tiles.RedTile;
import tiles.Tile;
import ui.Modal;

import javax.swing.*;

public class GameBoard {

    public final int GAME_BOARD_HEIGHT = 7;
    public final int GAME_BOARD_WIDTH = 9;
    private Player playerA;
    private Player playerB;
    private Player playerOnTurn;
    private Tile[][] tileCollection;
    private Piece[][] pieceCollection;
    private Piece selectedPiece;
    public int turnCounter;

    public GameBoard() {

        this.playerA = new Player("a");
        this.playerB = new Player("b");
        this.turnCounter = 1;
        this.tileCollection = new Tile[GAME_BOARD_HEIGHT][GAME_BOARD_WIDTH];
        this.pieceCollection = new Piece[GAME_BOARD_HEIGHT][GAME_BOARD_WIDTH];
        this.fillUpTileCollection();
        this.fillUpPlayerPieceCollection(this.getPlayerA());
        this.fillUpPlayerPieceCollection(this.getPlayerB());
        this.setPlayerOnTurn(this.getPlayerA());
        this.setAllTilesToNormal();

    }

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

        if(this.isPlacementValid(row, col, pieceCollection)) {
            int randomNum = this.trowDice(0, this.getPlayerOnTurn().getPlayerPieceCollection().size());

            Piece pieceToPlace = this.getPlayerOnTurn().getPlayerPieceCollection().get(randomNum);
            pieceCollection[row][col] = pieceToPlace;
            pieceToPlace.setRow(row);
            pieceToPlace.setCol(col);
            pieceToPlace.setPiecePlayerId(this.getPlayerOnTurn().getPlayerId());
            this.getPlayerOnTurn().getPlayerPieceCollection().remove(randomNum);
            this.updatePlayerAttributes();
        } else {
            this.switchPlayerOnTurn();
        }
    }

    private void updatePlayerAttributes() {

        if(this.getPlayerOnTurn().getPlayerId().equals("a")) {

            this.setPlayerA(this.getPlayerA());

        } else {

            this.setPlayerB(this.getPlayerB());
        }
    }

    private boolean isPlacementValid(int row, int col, Piece[][] pieceCollection) {

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
        catch (Exception e) {

            new Modal(frame, "Wrong piece", "Selected piece is not yours", 400, 100);
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

            this.getPieceCollection()[attackRow][attackCol] = this.getPieceCollection()[oldRow][oldCol];
            this.getSelectedPiece().setRow(attackRow);
            this.getSelectedPiece().setCol(attackCol);
            this.getPieceCollection()[oldRow][oldCol] = null;
        } else {
            int newPoints = this.getPieceCollection()[attackRow][attackCol].getHealthPoints() - damage;
            this.getPieceCollection()[attackRow][attackCol].setHealthPoints(newPoints);
        }
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
}
