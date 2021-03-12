package pieces;

import java.awt.*;

public class Knight extends Piece{

    public Knight() {

        setPieceId("knight");
        setAttackPoints(8);
        setDefensePoints(3);
        setHealthPoints(15);
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
        g.drawString("K", tileX + 38, tileY + 60);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.decode("#ddff00"));
        g.drawString(Integer.toString(this.getHealthPoints()), tileX + 42, tileY + 90);

    }

    @Override
    public boolean isMoveInRange(int newRow, int newCol, Piece[][] pieceCollection) {

        int rowCoefficient = Math.abs(newRow - getRow());
        int colCoefficient = Math.abs(newCol - getCol());

        return (rowCoefficient == 1 && colCoefficient == 0) ||
                (rowCoefficient == 0 && colCoefficient == 1);
    }

    @Override
    public boolean isAttackValid(int attackRow, int attackCol, Piece[][] pieceCollection) {

        int rowCoefficient = Math.abs(attackRow - getRow());
        int colCoefficient = Math.abs(attackCol - getCol());

        return (rowCoefficient == 1 && colCoefficient == 0) ||
                (rowCoefficient == 0 && colCoefficient == 1);
    }

}
