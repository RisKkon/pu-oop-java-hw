package pieces;

import java.awt.*;

public class Elf extends Piece  {

    public Elf() {

        this.setPieceId("elf");
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
    public boolean isMoveInRange(int row, int col, Piece[][] pieceCollection) {
        return false;
    }
}
