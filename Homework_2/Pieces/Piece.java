package Pieces;

import Tiles.Tile;

import java.awt.*;

public abstract class Piece {

    private final static int PIECE_OFFSET = 23;

    public String color;
    public int row;
    public int col;
    public String type;


    /***
     * Renders all the pieces to the gameBoard,
     * depending on the type of the piece.
     */
    public void renderPiece(Graphics g) {

        int x = this.col * Tile.TILE_SIZE + PIECE_OFFSET;
        int y = this.row * Tile.TILE_SIZE + PIECE_OFFSET;

        switch (type) {

            case "guard": {

                if(this.color.equals("green")) {

                    g.setColor(Color.decode("#fff200"));
                    g.fillOval(x, y, 50, 50);
                    g.setColor(Color.decode("#22b14c"));
                } else {

                    g.setColor(Color.decode("#22b14c"));
                    g.fillOval(x, y, 50, 50);
                    g.setColor(Color.decode("#fff200"));
                }
                g.fillOval(x + 5, y + 5, 40, 40);
                break;
            } case "leader": {

                if(this.color.equals("green")) {

                    g.setColor(Color.decode("#22b14c"));

                } else {

                    g.setColor(Color.decode("#fff200"));
                }
                g.fillRect(x, y, 50, 50);
                break;
            }
        }
    }

    /***
     * The method, witch executes the moves of the pieces.
     * I know it's too long, I tried dividing it into smaller
     * methods, but the logic for the movement of the leader
     * is just too big.
     */
    public void movePiece(int newRow, int newCol, Piece[][] pieceCollection) {

        int oldRow = this.row;
        int oldCol = this.col;

        if (this.type.equals("guard")) {

            this.row = newRow;
            this.col = newCol;
            pieceCollection[newRow][newCol] = this;
            pieceCollection[oldRow][oldCol] = null;

        } else {

            if (oldRow != newRow) {

                if (oldRow > newRow) {

                    for (int i = 0; i <= 4; i++) {

                        try {
                            if (pieceCollection[this.row - 1][this.col] == null) {
                                this.row--;
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }
                if (oldRow < newRow) {

                    for (int i = 0; i <= 4; i++) {

                        try {
                            if (pieceCollection[this.row + 1][this.col] == null) {
                                this.row++;
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }
            }

            if(oldCol != newCol) {

                if (oldCol > newCol) {

                    for (int i = 0; i <= 4; i++) {

                        try {
                            if (pieceCollection[this.row][this.col - 1] == null) {
                                this.col--;
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }
                if (oldCol < newCol) {

                    for (int i = 0; i <= 4; i++) {

                        try {
                            if (pieceCollection[this.row][this.col + 1] == null) {
                                this.col++;
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }
            }
        }
        pieceCollection[this.row][this.col] = this;
        pieceCollection[oldRow][oldCol] = null;
    }


    /***
     *This is the validator for the moves,
     * selected by the user.
     */
    public boolean isMoveValid(int row, int col, Piece[][] pieceCollection) {

        if(this.type.equals("guard")) {

            return (this.row == row + 1 || this.row == row - 1 || this.row == row)
                    && (this.col == col + 1 || this.col == col - 1 || this.col == col)
                    && pieceCollection[row][col] == null;
        } else {

            return pieceCollection[row][col] == null;
        }
    }
}
