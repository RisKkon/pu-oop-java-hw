package pieces;

import java.awt.*;

public class RecklessCannibal extends Piece {

    public RecklessCannibal() {

        this.setAttackPoints(4);
        this.setDefencePoints(6);
        this.setMagicPower(8);
        this.setSpeed(10);
        this.setPieceId("recklessCannibal");
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
        g.drawString("RC", tileX + 38, tileY + 60);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.decode("#ddff00"));
        g.drawString(Integer.toString(getDefencePoints()), tileX + 42, tileY + 90);

    }
}
