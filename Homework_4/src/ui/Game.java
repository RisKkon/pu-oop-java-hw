package ui;

import resources.gameboard.GameBoard;

public class Game {


    /**
     * This method starts the game and all the needed resources.
     */
    public Game() {

        GameBoard gameBoard = new GameBoard();
        Renderer renderer = new Renderer(gameBoard);
    }

}
