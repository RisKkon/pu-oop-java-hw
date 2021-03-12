package pieces;

import java.awt.*;

public class Elf extends Piece  {

    public Elf() {

        setPieceId("elf");
        setAttackPoints(5);
        setDefensePoints(1);
        setHealthPoints(10);
    }


    @Override
    public void renderPiece(Graphics g) {

        int tileX = getCol() * 100;
        int tileY = getRow() * 100;

        if(getPiecePlayerId().equals("a")) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("E", tileX + 38, tileY + 60);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.decode("#ddff00"));
        g.drawString(Integer.toString(getHealthPoints()), tileX + 42, tileY + 90);
    }

    @Override
    public boolean isMoveInRange(int newRow, int newCol, Piece[][] pieceCollection) {

        int rowCoefficient = Math.abs(newRow - getRow());
        int colCoefficient = Math.abs(newCol - getCol());
        boolean isMovingStraight = rowCoefficient <= 3 && colCoefficient == 0 ||
                                    colCoefficient <= 3 && rowCoefficient == 0;
        boolean isMovingGLike = rowCoefficient <= 2 && colCoefficient <= 1 ||
                                    rowCoefficient <= 1 && colCoefficient <= 2;
        return (isMovingStraight || isMovingGLike);
    }

    @Override
    public boolean isAttackValid(int newRow, int newCol, Piece[][] pieceCollection) {

        int rowCoefficient = Math.abs(newRow - getRow());
        int colCoefficient = Math.abs(newCol - getCol());

        boolean isAttackingStraight = rowCoefficient == 3 && colCoefficient == 0 ||
                colCoefficient == 3 && rowCoefficient == 0;
        boolean isAttackingGLike = rowCoefficient == 2 && colCoefficient == 1 ||
                rowCoefficient == 1 && colCoefficient == 2;
        return (isAttackingStraight || isAttackingGLike);
    }

}
