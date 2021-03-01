package pieces;

import java.awt.*;

public class Dwarf extends  Piece{

    public Dwarf() {

        this.setPieceId("dwarf");
        this.setAttackPoints(6);
        this.setDefensePoints(2);
        this.setHealthPoints(12);
    }


    @Override
    public void renderPiece(Graphics g) {

        int tileX = this.getCol() * 100;
        int tileY = this.getRow() * 100;
        //g.setColor(Color.BLACK);
        if(this.getPiecePlayerId().equals("a")) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString("D", tileX + 40, tileY + 60);
    }

    @Override
    public boolean isMoveInRange(int newRow, int newCol, Piece[][] pieceCollection) {

        int rowCoefficient = Math.abs(newRow - this.getRow());
        int colCoefficient = Math.abs(newCol - this.getCol());
        boolean isMovingStraight = rowCoefficient <= 2 && colCoefficient == 0 ||
                colCoefficient <= 2 && rowCoefficient == 0;
        boolean isMovingGLike = rowCoefficient <= 1 && colCoefficient <= 1;
        return (isMovingStraight || isMovingGLike);
    }

    @Override
    public boolean isAttackValid(int row, int col, Piece[][] pieceCollection) {
        return false;
    }
}

