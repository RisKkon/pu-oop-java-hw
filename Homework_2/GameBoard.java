import Pieces.Guard;
import Pieces.Leader;
import Pieces.Piece;
import Tiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameBoard extends JFrame implements MouseListener {

    public static final int BOARD_SIZE = 500;
    public static final int BOARD_SIDE_SIZE = 5;
    private ArrayList<Tile> tilesCollection;
    public Piece[][] pieceCollection;
    private Piece selectedPiece;

    /***
     * The constructor, witch is also the starting point
     * of the program. It declares all the needed variables
     * and parameters for the JFrame.
     */
    public GameBoard(){

        this.setSize(BOARD_SIZE, BOARD_SIZE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addMouseListener(this);

        tilesCollection = new ArrayList<>();
        pieceCollection = new Piece[BOARD_SIDE_SIZE][BOARD_SIDE_SIZE];

        this.setPieces();
        this.setTiles();

    }


    /***
     * The method witch paints everything
     * to the screen.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(Tile tile : tilesCollection) {

            tile.renderTile(g);
        }

        for (int row = 0; row < BOARD_SIDE_SIZE; row++) {
            for (int col = 0; col < BOARD_SIDE_SIZE; col++) {

                if(this.hasBoardPiece(row, col)) {
                    pieceCollection[row][col].renderPiece(g);
                }
            }
        }
    }

    /***
     * Sets all the different tiles to the GameBoard
     */
    private void setTiles() {

        tilesCollection.add(new RedTile(0, 0));
        tilesCollection.add(new BlackTile(0, 1));
        tilesCollection.add(new WhiteTile(0, 2));
        tilesCollection.add(new BlackTile(0, 3));
        tilesCollection.add(new RedTile(0, 4));
        tilesCollection.add(new GreyTile(1, 0));
        tilesCollection.add(new GreyTile(1, 1));
        tilesCollection.add(new WhiteTile(1, 2));
        tilesCollection.add(new GreyTile(1, 3));
        tilesCollection.add(new GreyTile(1, 4));
        tilesCollection.add(new WhiteTile(2, 0));
        tilesCollection.add(new WhiteTile(2, 1));
        tilesCollection.add(new WhiteTile(2, 2));
        tilesCollection.add(new WhiteTile(2, 3));
        tilesCollection.add(new WhiteTile(2, 4));
        tilesCollection.add(new GreyTile(3, 0));
        tilesCollection.add(new GreyTile(3, 1));
        tilesCollection.add(new WhiteTile(3, 2));
        tilesCollection.add(new GreyTile(3, 3));
        tilesCollection.add(new GreyTile(3, 4));
        tilesCollection.add(new BlackTile(4, 0));
        tilesCollection.add(new RedTile(4, 1));
        tilesCollection.add(new WhiteTile(4, 2));
        tilesCollection.add(new RedTile(4, 3));
        tilesCollection.add(new BlackTile(4, 4));
    }

    /***
     * Sets all the pieces on the GameBoard.
     */
    private void setPieces() {

        this.pieceCollection[0][0] = (new Guard(0, 0, "yellow"));
        this.pieceCollection[0][1] = (new Guard(0, 1, "yellow"));
        this.pieceCollection[0][2] = (new Guard(0, 2, "yellow"));
        this.pieceCollection[0][3] = (new Guard(0, 3, "yellow"));
        this.pieceCollection[0][4] = (new Leader(0, 4, "yellow"));
        this.pieceCollection[4][0] = (new Leader(4, 0, "green"));
        this.pieceCollection[4][1] = (new Guard(4, 1, "green"));
        this.pieceCollection[4][2] = (new Guard(4, 2, "green"));
        this.pieceCollection[4][3] = (new Guard(4, 3, "green"));
        this.pieceCollection[4][4] = (new Guard(4, 4, "green"));
    }

    /***
     *Checks if the selected tile has a piece on it.
     */
    private boolean hasBoardPiece(int row, int col) {

        return this.pieceCollection[row][col] != null;
    }

    /***
     * Gets a certain piece from the board
     */
    private Piece getBoardPiece(int row, int col) {

        return this.pieceCollection[row][col];
    }

    /***
     * Converts the given coordinates to row and col.
     */
    public int getLocationBasedOffCoordinates(int coordinates) {

        return coordinates / Tile.TILE_SIZE;
    }

    /***
     *This method is responsible for the actions that
     * have to be executed after a mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        int row = getLocationBasedOffCoordinates(e.getY());
        int col = getLocationBasedOffCoordinates(e.getX());

        boolean isMoveExecuted = false;

        /**
         * Checks if there is already a selected piece.
         */
        if(this.selectedPiece != null) {

            if(this.selectedPiece.isMoveValid(row, col, this.pieceCollection)) {

                this.selectedPiece.movePiece(row, col, this.pieceCollection);
                isMoveExecuted = true;
            }
            this.selectedPiece = null;
            this.repaint();
        }
        /**
         * If there is no move executed and there is
         * a piece in the selected tile, this method
         * selects that piece.
         */
        if(this.hasBoardPiece(row, col) && !isMoveExecuted) {
            this.selectedPiece = this.getBoardPiece(row, col);
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
}
