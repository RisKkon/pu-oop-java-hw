package ui;

import gameplay.GameBoard;

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

            if(!this.isThereIsAPieceInBox(row, col)) {

                this.executeMoveOnBoard(row, col);
            }
            if(this.isThereIsAPieceInBox(row, col)) {

                if(this.doesPlayerWantToAttackEnemyPiece(row, col)) {

                    this.executeAttackOnBoard(row, col);
                }
            }
        }
        if(this.isSetupCompleteAndPieceNotSelected()) {

            if(this.getGameBoard().isSelectedPieceValid(row, col, this.getGameBoard().getPieceCollection(), this)) {
                this.selectPiece(row, col);
            }
        }

        if(!this.isGameSetupStageComplete()) {

            this.gameSetupRound(row, col);
        }
        this.checkIfMoveFailed();
        this.checkIfMoveWasSuccessful();

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

    private boolean isThereIsAPieceInBox(int row, int col) {

        return this.getGameBoard().isThereAPieceHere(row, col);
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
        }
    }

    private void selectPiece(int row, int col) {

        this.getGameBoard().setSelectedPiece(this.getGameBoard().getPieceCollection()[row][col]);
        this.setPieceSelected(true);
    }

    private void gameSetupRound(int row, int col) {

        this.getGameBoard().executeInitialPlacementOnBoard(row, col, this.getGameBoard().getPieceCollection());
        this.repaint();
        this.getGameBoard().switchPlayerOnTurn();
        if(this.isGameSetupOver()) {
            this.setGameSetupStageComplete(true);
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
}
