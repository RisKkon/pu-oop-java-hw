package ui;

import gameplay.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Render extends JFrame implements MouseListener {

    private GameBoard gameBoard;
    private boolean gameSetupStage;
    private boolean isPieceSelected;
    private boolean didMoveFail;
    private boolean wasMoveSuccessful;

    public Render(GameBoard gameboard) {

        this.gameBoard = gameboard;
        this.gameSetupStage = false;
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

    public boolean isGameSetupStage() { return gameSetupStage; }

    public void setGameSetupStage(boolean gameSetupStage) { this.gameSetupStage = gameSetupStage; }

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

        if(this.isGameSetupOver() && this.isPieceSelected()) {

            if(!this.getGameBoard().isThereAPieceHere(row, col)) {

                this.getGameBoard().executeMove(row, col);
                this.setPieceSelected(false);
                this.setWasMoveSuccessful(true);
                this.repaint();
            }

        }

        if(this.isGameSetupStage() && !this.isPieceSelected() && !this.isDidMoveFail() && !this.isWasMoveSuccessful()) {

            if(this.getGameBoard().isSelectedPieceValid(row,col,
                    this.getGameBoard().getPieceCollection())) {

                this.getGameBoard().setSelectedPiece(this.getGameBoard().getPieceCollection()[row][col]);
                this.setPieceSelected(true);
            }


        }

        if(!this.isGameSetupStage()) {


            this.getGameBoard().executeInitialPlacementOnBoard(row, col, this.getGameBoard().getPieceCollection());
            this.repaint();
            this.getGameBoard().switchPlayerOnTurn();
            if(this.isGameSetupOver()) {
                this.setGameSetupStage(true);
            }
        }
        if(this.isDidMoveFail()) {
            this.setDidMoveFail(false);

        }
        if(this.isWasMoveSuccessful()) {
            this.getGameBoard().switchPlayerOnTurn();
            this.setWasMoveSuccessful(false);
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
}
