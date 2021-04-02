package pieces;

import java.awt.*;

public class DrunkenKnight extends Piece {

    public DrunkenKnight() {

        this.setAttackPoints(5);
        this.setDefencePoints(5);
        this.setMagicPower(5);
        this.setSpeed(5);
        this.setPieceId("drunkenKnight");
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
        g.drawString("DK", tileX + 38, tileY + 60);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.decode("#ddff00"));
        g.drawString(Integer.toString(getDefencePoints()), tileX + 42, tileY + 90);
    }
}
