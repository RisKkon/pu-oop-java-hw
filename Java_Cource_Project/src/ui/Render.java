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
    private boolean isGameOver;

    public Render(GameBoard gameboard) {

        this.gameBoard = gameboard;
        this.isGameSetupStageComplete = false;
        this.isPieceSelected = false;
        this.didMoveFail = false;
        this.wasMoveSuccessful = false;
        this.isGameOver = false;
        this.setSize(900, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addMouseListener(this);
        this.setVisible(true);
    }

    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }

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

        if (this.isSetupCompleteAndPieceSelected()) {

            this.moveExecutionStage(row, col);

        }
        if (this.isSetupCompleteAndPieceNotSelected()) {

            this.pieceSelectionStage(row, col);
        }
        this.setupStage(row, col);
        this.checkIfMoveFailed();
        this.checkIfMoveWasSuccessful();
        this.printInfoToConsole();
        if(this.checkIfGameIsOver() && this.isGameSetupStageComplete) {
            this.gameOverMessage();
            System.exit(1);
        }

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
        } else {

            new Modal(this, "Invalid attack!",
                    "You are attempting an invalid attack try again", 400, 100);
            this.setPieceSelected(false);
            this.getGameBoard().roundCounter--;
        }
        this.repaint();
    }

    private void executeHealOnBoard(int row, int col) {

        this.getGameBoard().executeHeal(row, col);
        int diceNum = this.getGameBoard().trowDice(1, 100);
        if(diceNum % 2 != 0) {

            new Modal(this, "Lucky", "Player " + this.getGameBoard()
                    .getPlayerOnTurn().getPlayerId().toUpperCase() + " got lucky, go again", 400, 100);
            this.getGameBoard().switchPlayerOnTurn();
        }
        this.repaint();
        this.setWasMoveSuccessful(true);
        this.setPieceSelected(false);
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
        if(this.getGameBoard().isPlacementOnValidSide(row) && this.getGameBoard().isBoxEmpty(row, col)) {

            this.getGameBoard().executeInitialPlacementOnBoard(row, col, this.getGameBoard().getPieceCollection());
            this.repaint();
            this.getGameBoard().switchPlayerOnTurn();
            if (this.isGameSetupOver()) {
                this.setGameSetupStageComplete(true);
                this.getGameBoard().setAllTilesToNormal();

            }
        } else {
            new Modal(this, "Invalid placement",
                    "You can't place a piece here, try again", 400, 100);

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

        if(this.getGameBoard().isSelectedPieceValid(row, col, this.getGameBoard().getPieceCollection())) {
            this.selectPiece(row, col);
        } else {
            new Modal(this, "Wrong piece", "Selected piece is not yours", 400, 100);
        }
    }

    private void moveExecutionStage(int row, int col) {

        this.getGameBoard().setAllTilesToNormal();
        if(!this.isThereIsAPieceInBox(row, col)) {

            this.executeMoveOnBoard(row, col);
            this.increaseRoundCounter();
        }
        if(this.isThereIsAPieceInBox(row, col)) {

            if(this.doesPlayerWantToAttackEnemyPiece(row, col)) {

                this.executeAttackOnBoard(row, col);
                this.increaseRoundCounter();
            } else {
                if (this.doesPlayerWantToHealAPiece(row, col)) {
                    this.executeHealOnBoard(row, col);
                    this.increaseRoundCounter();
                } else {

                    new Modal(this, "Invalid heal", "Invalid healing attempt, try again",400, 100 );
                    this.setPieceSelected(false);
                    this.setDidMoveFail(true);
                    this.repaint();
                }
            }
        }
    }

    private void printInfoToConsole() {

        System.out.println("==================================================");
        System.out.println("Player a points: " + this.getGameBoard().getPlayerA().getPlayerPoints()
                + "             round: " + this.getGameBoard().getRoundCounter());
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

    private void increaseRoundCounter() {

        if(this.isGameSetupStageComplete) {

            this.getGameBoard().roundCounter++;
        }
    }

    private void checkIfPlayerHasNoPieces() {

        if(this.getGameBoard().isGameOver() && this.isGameSetupOver()) {
            this.setGameOver(true);
        }
    }

    private boolean checkIfGameIsOver() {

        this.checkIfPlayerHasNoPieces();
        return this.isGameOver;
    }

    private void gameOverMessage() {

        System.out.println();
        System.out.println("Game Over!!        rounds player: " + this.getGameBoard()
                                                                     .getRoundCounter());
        System.out.println("Game winner is player" + this.getGameBoard().getPlayerWinner()
                                                     .getPlayerId().toUpperCase());
        System.out.print("Player B's taken pieces: ");
        this.getGameBoard().getPlayerA().getPlayerDeadPieces().forEach(e -> System.out.print(e.getPieceId() + " "));
        System.out.println();
        System.out.print("Player A's taken pieces: ");
        this.getGameBoard().getPlayerB().getPlayerDeadPieces().forEach(e -> System.out.print(e.getPieceId() + " "));
        System.out.println("Player A's points: " + this.getGameBoard().getPlayerA().getPlayerPoints());
        System.out.println();
        System.out.println("Player B's points: " + this.getGameBoard().getPlayerB().getPlayerPoints());

    }
}
