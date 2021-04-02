package pieces;

import java.awt.*;

public class MagicCat extends Piece {

    public MagicCat() {

        this.setAttackPoints(8);
        this.setDefencePoints(5);
        this.setMagicPower(10);
        this.setSpeed(1);
        this.setPieceId("magicCat");
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
        g.drawString("MC", tileX + 38, tileY + 60);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.decode("#ddff00"));
        g.drawString(Integer.toString(getDefencePoints()), tileX + 42, tileY + 90);

    }
}
