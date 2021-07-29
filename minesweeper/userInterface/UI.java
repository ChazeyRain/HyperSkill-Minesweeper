package minesweeper.userInterface;

import minesweeper.Messages;

public interface UI {
    void printField(int[][] field, boolean[][] visible);
    void printGame(int[][] cells, boolean[][] moves, boolean[][] visible, boolean isEnd);
    int getMinesQuantity();
    String requestCoordinates();
    void receiveMessage(Messages msg);
    void endGame(boolean lost);
}
