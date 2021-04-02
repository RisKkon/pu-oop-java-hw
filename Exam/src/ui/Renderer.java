package ui;

import gameplay.GameBoard;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Renderer extends JFrame implements MouseListener {

    GameBoard gameBoard;
    private boolean isGameSetupComplete;


    public Renderer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addMouseListener(this);
        this.setVisible(true);
        this.isGameSetupComplete = false;
    }


    public boolean isGameSetupComplete() {
        return isGameSetupComplete;
    }

    public void setGameSetupComplete(boolean gameSetupComplete) {
        isGameSetupComplete = gameSetupComplete;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int row = getLocationBasedOnCoordinates(e.getY());
        int col = getLocationBasedOnCoordinates(e.getX());

        if(!isGameSetupStageComplete()) {

            placePiece(row, col);
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

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        for (int i = 0; i < getGameBoard().getTileCollection().length; i++) {
            for (int j = 0; j < getGameBoard().getTileCollection()[i].length; j++) {

                getGameBoard().getTileCollection()[i][j].renderTile(g);

                if(getGameBoard().getPieceCollection()[i][j] != null) {
                    getGameBoard().getPieceCollection()[i][j].renderPiece(g);
                }
            }
        }
    }

    private void showDeck() {

        System.out.println("Player Deck:");
        for (int i = 0; i < getGameBoard().getPlayerOnTurn().getPlayerPieceDeck().size(); i++) {

            System.out.println(getGameBoard().getPlayerOnTurn()
                    .getPlayerPieceDeck().get(i).getPieceId());
        }
    }

    private void showPlayerOnTurn() {

        System.out.println("Player on turn: Player " + gameBoard.getPlayerOnTurn().getPlayerId());

    }

    private boolean isGameSetupStageComplete() {

        return getGameBoard().getPlayerA().getPlayerPieceDeck().size() <= 13
        && getGameBoard().getPlayerB().getPlayerPieceDeck().size() <= 13;
    }

    private int getLocationBasedOnCoordinates(int coordinates) {

        return coordinates / 100;
    }

    private void placePiece(int row, int col) {

        if(isInitialPlacementValid(row, col)) {

            int pieceIndex = getGameBoard().trowDice(0, 18);
            Piece pieceToPlace = getGameBoard().getPlayerOnTurn().getPlayerPieceDeck()
                    .get(pieceIndex);
            getGameBoard().getPieceCollection()[row][col] = pieceToPlace;
            getGameBoard().getPieceCollection()[row][col].setRow(row);
            getGameBoard().getPieceCollection()[row][col].setCol(col);
            getGameBoard().getPieceCollection()[row][col]
                    .setPiecePlayerId(getGameBoard().getPlayerOnTurn().getPlayerId());
            repaint();
            getGameBoard().switchPlayerOnTurn();
        }
    }

    private boolean isInitialPlacementValid(int row, int col) {

        return isPlacementOnValidSide(row) && getGameBoard().getPieceCollection()[row][col] == null;
    }

    public boolean isPlacementOnValidSide(int row) {

        if(getGameBoard().getPlayerOnTurn().getPlayerId().equals("a")) {

            return row <= 1;
        }

        return row >= 8;
    }


}

