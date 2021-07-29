package minesweeper;

import minesweeper.userInterface.CLI;
import minesweeper.userInterface.UI;

public class Main {
    public static void main(String[] args) {
        // write your code here
        UI ui = new CLI();

        Field field = Field.fieldFactory(ui);

        field.printGame();

        while (!field.isEnd()) {
            field.move();
            field.printGame();
        }

        ui.endGame(field.isLoose());
    }
}
