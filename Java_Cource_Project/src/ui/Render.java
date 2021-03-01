package ui;

import gameplay.GameBoard;
import gameplay.Player;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Render extends JFrame implements MouseListener {

    private GameBoard gameBoard;
    private boolean isGameSetupStageComplete;
    private boolean isPieceSelected;
    private boolean didMoveFail;
    private boolean wasMoveSuccessful;

    public Render(GameBoard gameboard) {

        this.gameBoard = gameboard;
        this.isGameSetupStageComplete = false;
        this.isPieceSelected = false;
        this.didMoveFail = false;
        this.wasMoveSuccessful = false;
        this.setSize(900, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Kurvi i belo do grob");
        this.addMouseListener(this);
        this.setVisible(true);


    }

    public boolean isWasMoveSuccessful() { return wasMoveSuccessful; }

    public void setWasMoveSuccessful(boolean wasMoveSuccessful) { this.wasMoveSuccessful = wasMoveSuccessful; }

    public boolean isDidMoveFail() { return didMoveFail; }

    public void setDidMoveFail(boolean didMoveFail) { this.didMoveFail = didMoveFail; }

    public GameBoard getGameBoard() { return gameBoard; }

    public boolean isGameSetupStageComplete() { return isGameSetupStageComplete; }

    public void setGameSetupStageComplete(boolean gameSetupStageComplete) { this.isGameSetupStageComplete = gameSetupStageComplete; }

    public boolean isPieceSelected() { return isPieceSelected; }

    public void setPieceSelected(boolean pieceSelected) { isPieceSelected = pieceSelected; }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        for (int i = 0; i < this.getGameBoard().getTileCollection().length; i++) {
            for (int j = 0; j < this.getGameBoard().getTileCollection()[i].length; j++) {

                this.getGameBoard().getTileCollection()[i][j].renderTile(g);
            }
        }
        for (int i = 0; i < this.getGameBoard().getPieceCollection().length ; i++) {
            for (int j = 0; j <this.getGameBoard().getPieceCollection()[i].length ; j++) {

                if(this.getGameBoard().getPieceCollection()[i][j] != null) {
                    this.getGameBoard().getPieceCollection()[i][j].renderPiece(g);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int row = this.getLocationBasedOnCoordinates(e.getY());
        int col = this.getLocationBasedOnCoordinates(e.getX());

        if(this.isSetupCompleteAndPieceSelected()) {

            this.moveExecutionStage(row, col);
        }
        if(this.isSetupCompleteAndPieceNotSelected()) {

            this.pieceSelectionStage(row, col);
        }
        this.setupStage(row, col);
        this.checkIfMoveFailed();
        this.checkIfMoveWasSuccessful();
        this.printInfoToConsole();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private int getLocationBasedOnCoordinates(int coordinates) {

        return coordinates / 100;
    }

    private boolean isGameSetupOver() {

         return this.getGameBoard().getPlayerA().getPlayerPieceCollection().size() == 0 &&
                this.getGameBoard().getPlayerB().getPlayerPieceCollection().size() == 0;
    }

    private boolean isSetupCompleteAndPieceSelected() {

        return this.isGameSetupOver() && this.isPieceSelected();
    }

    private boolean doesPlayerWantToAttackEnemyPiece(int row, int col) {

        return !this.getGameBoard().getPieceCollection()[row][col].getPiecePlayerId().equals(
                this.getGameBoard().getPlayerOnTurn().getPlayerId());
    }

    private boolean doesPlayerWantToHealAPiece(int row, int col) {

        return this.getGameBoard().getSelectedPiece().getRow() == row &&
                this.getGameBoard().getSelectedPiece().getCol() == col;

    }

    private boolean isThereIsAPieceInBox(int row, int col) {

        return this.getGameBoard().isThereAPieceHere(row, col) && !this.isWasMoveSuccessful();

    }

    private boolean isSetupCompleteAndPieceNotSelected() {

        return this.isGameSetupStageComplete() && !this.isPieceSelected() && !this.isDidMoveFail() && !this.isWasMoveSuccessful();
    }

    private void executeMoveOnBoard(int row, int col) {

        this.getGameBoard().executeMove(row, col);
        this.setPieceSelected(false);
        this.setWasMoveSuccessful(true);
        this.repaint();
    }

    private void executeAttackOnBoard(int row, int col) {

        if (this.getGameBoard().getSelectedPiece().isAttackValid(row, col, this.getGameBoard().getPieceCollection())) {
            this.getGameBoard().executeAttack(row, col);
            this.getGameBoard().removeDeadPieces();
            this.getGameBoard().setSelectedPiece(null);
            this.setPieceSelected(false);
            this.setWasMoveSuccessful(true);
        }
        this.repaint();
    }

    private void executeHealOnBoard(int row, int col) {

        this.getGameBoard().executeHeal(row, col);
        int diceNum = this.getGameBoard().trowDice(1, 100);
        if(diceNum % 2 == 0) {
            this.getGameBoard().switchPlayerOnTurn();
        }
    }

    private void selectPiece(int row, int col) {

        this.getGameBoard().setSelectedPiece(this.getGameBoard().getPieceCollection()[row][col]);
        this.getGameBoard().getSelectedPiece().showAvailableMoves(
                                        this.getGameBoard().getPieceCollection(),
                                         this.getGameBoard().getTileCollection());
        this.getGameBoard().getSelectedPieceTile();
        this.setPieceSelected(true);
        this.repaint();
    }

    private void gameSetupRound(int row, int col) {

        this.getGameBoard().showAvailableTiles();
        this.getGameBoard().executeInitialPlacementOnBoard(row, col, this.getGameBoard().getPieceCollection());
        this.repaint();
        this.getGameBoard().switchPlayerOnTurn();
        if(this.isGameSetupOver()) {
            this.setGameSetupStageComplete(true);
            this.getGameBoard().setAllTilesToNormal();
        }
    }

    private void checkIfMoveFailed() {

        if(this.isDidMoveFail()) {
            this.setDidMoveFail(false);

        }
    }

    private void checkIfMoveWasSuccessful() {

        if(this.isWasMoveSuccessful()) {
            this.getGameBoard().switchPlayerOnTurn();
            this.setWasMoveSuccessful(false);
        }
    }

    private void setupStage(int row, int col) {

        if(!this.isGameSetupStageComplete()) {

            this.gameSetupRound(row, col);
        }
    }

    private void pieceSelectionStage(int row, int col) {

        if(this.getGameBoard().isSelectedPieceValid(row, col, this.getGameBoard().getPieceCollection(), this)) {
            this.selectPiece(row, col);
        }
    }

    private void moveExecutionStage(int row, int col) {

        this.getGameBoard().setAllTilesToNormal();
        if(!this.isThereIsAPieceInBox(row, col)) {

            this.executeMoveOnBoard(row, col);
        }
        if(this.isThereIsAPieceInBox(row, col)) {

            if(this.doesPlayerWantToAttackEnemyPiece(row, col)) {

                this.executeAttackOnBoard(row, col);
            } else {
                if (this.doesPlayerWantToHealAPiece(row, col)) {
                    this.executeHealOnBoard(row, col);
                } else {

                    new Modal(this, "Invalid heal", "Invalid healing attempt, try again",400, 100 );
                    this.setPieceSelected(false);


                }
            }
        }
    }

    private void printInfoToConsole() {

        System.out.println("==================================================");
        System.out.println("Player a points: " + this.getGameBoard().getPlayerA().getPlayerPoints());
        System.out.println("Player b points: " + this.getGameBoard().getPlayerB().getPlayerPoints());
        System.out.println("Player " + this.getGameBoard().getPlayerOnTurn().getPlayerId().toUpperCase() + " on turn");
        System.out.println("==================================================");

        if(!this.isGameSetupOver()) {
            System.out.println("Game setup stage");
        }

        if(this.isSetupCompleteAndPieceNotSelected()) {
            System.out.println("Select piece");
        }
        if(this.isSetupCompleteAndPieceSelected()) {

            System.out.println("Piece selected on row: " + this.getGameBoard().getSelectedPiece().getRow()
                                + " and on col: " + this.getGameBoard().getSelectedPiece().getCol());
        }
    }
}
