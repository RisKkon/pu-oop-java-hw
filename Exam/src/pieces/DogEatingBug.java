package pieces;

import java.awt.*;

public class DogEatingBug extends Piece {

    public DogEatingBug() {

        this.setAttackPoints(10);
        this.setDefencePoints(2);
        this.setMagicPower(8);
        this.setSpeed(5);
        this.setPieceId("dogEatingBug");
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
        g.drawString("DEB", tileX + 38, tileY + 60);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.decode("#ddff00"));
        g.drawString(Integer.toString(getDefencePoints()), tileX + 42, tileY + 90);

    }
}
