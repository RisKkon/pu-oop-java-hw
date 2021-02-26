package ui;

import resources.gameboard.GameBoard;
import resources.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Renderer extends JFrame implements MouseListener, ActionListener {

    public GameBoard gameBoard;
    private Tile selectedPlayerTile;
    private Tile highlightedTile;
    private JButton restartButton;
    private JButton exitButton;

    /**
     *The constructor sets up all the needed parameters for the JFrame.
     */
    public Renderer(GameBoard gameBoard) {

        this.gameBoard = gameBoard;
        this.setSize(GameBoard.SIDE_SIZE * 100, GameBoard.SIDE_SIZE * 100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("GPSANEMENAMIRA");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addMouseListener(this);
    }

    /**
     *Takes care off all the different actions that
     * are performed when the user clicks on the screen.
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        int row = this.getLocationBasedOnCoordinates(e.getY());
        int col = this.getLocationBasedOnCoordinates(e.getX());

        //If there is a selected and highlighted tile, executes
        //a certain move on the gameBoard.

        this.executePlayerMove(row, col);

        //If there is none, selects the highlighted tile
        //from the coordinates.

        this.selectHighlightedTile(row, col);
        //Selects the player if he is unselected.

        this.selectPlayerTile(row, col);
        this.repaint();
        this.isPlayerSurrounded();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     *Used for the menu at the end of the game.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.restartButton) {

            new Game();
        } else {

            System.exit(1);
        }
    }

    /**
     *Paints all the components of the game.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < this.gameBoard.tileCollection.length; i++) {
            for (int j = 0; j < this.gameBoard.tileCollection[i].length; j++) {

                this.gameBoard.tileCollection[i][j].render(g);
            }
        }
        this.gameBoard.getPlayer().render(g);
        if(this.highlightedTile != null) {
            this.highlightedTile.onClick(g);
        }
    }

    private int getLocationBasedOnCoordinates(int coordinates) {

        return coordinates / 100;
    }

    /**
     * If player is surrounded, the game ends
     * and the loser message is displayed.
     */
    private void isPlayerSurrounded() {

        if(this.gameBoard.isPlayerSurrounded()) {

           this.gameLoserMessage();
        }
    }

    /**
     * Selects the player if he is unselected.
     */
    private void selectPlayerTile(int row, int col) {

        if(this.selectedPlayerTile == null) {

            //Checks if the coordinates are right
            if(gameBoard.doesCoordinatesMathWithPlayer(row, col)) {
                this.selectedPlayerTile = gameBoard.getTileCollection()[row][col];
            }
        }
    }

    /**
     *Selects the highlighted tile.
     */
    private void selectHighlightedTile(int row, int col) {

        if (this.highlightedTile == null && this.selectedPlayerTile != null) {

            //Only selects it if the move is valid.
            if (gameBoard.isMoveValid(row, col)) {
                this.highlightedTile = gameBoard.getTileCollection()[row][col];
            }
        }
    }

    /**
     *Executes a move on the gameboard
     */
    private void executePlayerMove(int row, int col) {

        if(this.selectedPlayerTile != null && this.highlightedTile != null) {

            if(!this.gameBoard.checkIfSelectedTileMatches(row, col, "forbiddenTile")
                    && gameBoard.isMoveValid(row, col)) {

                this.gameBoard.movePlayer(row, col);
            } else {

                new Modal(this, "!", "Cannot execute this move, selected tile is forbidden",
                        400, 120);
            }
            this.highlightedTile = null;
            this.selectedPlayerTile = null;
            if(this.gameBoard.isGameOver()) {

                this.gameWinnerMessage();
            }
        }
    }

    /**
     * Winner message
     */
    private void gameWinnerMessage() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 120);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Congratulations, you won");

        this.restartButton = new JButton("New game");
        this.restartButton.setSize(100, 50);
        this.restartButton.setLocation(10, 10);
        this.restartButton.addActionListener(this);
        this.exitButton = new JButton("Exit");
        this.exitButton.setSize(100, 50);
        this.exitButton.setLocation(370, 10);
        this.exitButton.addActionListener(this);

        frame.add(restartButton);
        frame.add(exitButton);

        frame.setVisible(true);
    }

    /**
     * Loser message.
     */
    private void gameLoserMessage() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 120);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("You lost, try again.");

        this.restartButton = new JButton("New game");
        this.restartButton.setSize(100, 50);
        this.restartButton.setLocation(10, 10);
        this.restartButton.addActionListener(this);
        this.exitButton = new JButton("Exit");
        this.exitButton.setSize(100, 50);
        this.exitButton.setLocation(370, 10);
        this.exitButton.addActionListener(this);

        frame.add(restartButton);
        frame.add(exitButton);

        frame.setVisible(true);
    }
}
