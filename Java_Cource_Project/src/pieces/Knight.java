package pieces;

import gameplay.GameBoard;

import java.awt.*;

public class Knight extends Piece{

    public Knight() {

        this.setPieceId("knight");
    }



    @Override
    public void renderPiece(Graphics g) {

        int tileX = this.getCol() * 100;
        int tileY = this.getRow() * 100;
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString("K", tileX + 40, tileY + 60);

    }

    @Override
    public boolean isMoveInRange(int newRow, int newCol, Piece[][] pieceCollection) {

        int rowCoefficient = Math.abs(newRow - this.getRow());
        int colCoefficient = Math.abs(newCol - this.getCol());

        return (rowCoefficient == 1 && colCoefficient == 0) ||
                (rowCoefficient == 0 && colCoefficient == 1);
    }
}
