package ui;

import gameplay.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Renderer extends JFrame implements MouseListener {

    GameBoard gameBoard;

    public Renderer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.setSize(1200, 1200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addMouseListener(this);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

                /**if(getGameBoard().getPieceCollection()[i][j] != null) {
                    getGameBoard().getPieceCollection()[i][j].renderPiece(g);
                }**/
            }
        }
    }
}
