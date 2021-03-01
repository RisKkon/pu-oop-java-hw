package pieces;

import tiles.Tile;

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

        if(this.getPiecePlayerId().equals("a")) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("E", tileX + 38, tileY + 60);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.decode("#ddff00"));
        g.drawString(Integer.toString(this.getHealthPoints()), tileX + 42, tileY + 90);
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
    public boolean isAttackValid(int newRow, int newCol, Piece[][] pieceCollection) {

        int rowCoefficient = Math.abs(newRow - this.getRow());
        int colCoefficient = Math.abs(newCol - this.getCol());

        boolean isAttackingStraight = rowCoefficient == 3 && colCoefficient == 0 ||
                colCoefficient == 3 && rowCoefficient == 0;
        boolean isAttackingGLike = rowCoefficient == 2 && colCoefficient == 1 ||
                rowCoefficient == 1 && colCoefficient == 2;
        return (isAttackingStraight || isAttackingGLike);
    }

}
