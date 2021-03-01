import gameplay.GameBoard;
import ui.Render;

public class Application {


    public static void main(String[] args) {

        boolean doesUserWantToExit = false;
        startGame();
    }

    public static void startGame() {

        GameBoard gameboard = new GameBoard();
        new Render(gameboard);
    }
}
