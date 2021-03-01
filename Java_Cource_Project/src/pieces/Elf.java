package pieces;

import java.awt.*;

public class Elf extends Piece  {

    public Elf() {

        this.setPieceId("elf");
        this.setAttackPoints(5);
        this.setDefensePoints(1);
        this.setHealthPoints(10);
    }


    @Override
    public void renderPiece(Graphics g) {

        int tileX = this.getCol() * 100;
        int tileY = this.getRow() * 100;
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString("E", tileX + 40, tileY + 60);
    }

    @Override
    public boolean isMoveInRange(int newRow, int newCol, Piece[][] pieceCollection) {

        int rowCoefficient = Math.abs(newRow - this.getRow());
        int colCoefficient = Math.abs(newCol - this.getCol());
        boolean isMovingStraight = rowCoefficient <= 3 && colCoefficient == 0 ||
                                    colCoefficient <= 3 && rowCoefficient == 0;
        boolean isMovingGLike = rowCoefficient <= 2 && colCoefficient <= 1 ||
                                    rowCoefficient <= 1 && colCoefficient <= 2;
        return (isMovingStraight || isMovingGLike);
    }

    @Override
    public boolean isAttackValid(int row, int col, Piece[][] pieceCollection) {
        return false;
    }
}
