package minesweeper;

import java.util.Random;
import minesweeper.userInterface.UI;
public class Field {

    private int[][] field;
    private final UI ui;

    private boolean[][] mineMarkers;
    private final int minesCount;
    private boolean[][] visibleCells;

    private int minesFound = 0;
    private int markersCount = 0;
    private int freeCells;

    private boolean loose = false;

    public Field(UI ui, int x, int y, int quantity) {
        field = new int[x][y];
        this.mineMarkers = new boolean[x][y];
        this.visibleCells = new boolean[x][y];
        this.ui = ui;

        this.minesCount = quantity;
        this.freeCells = x * y;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                field[i][j] = 0;
            }
        }

        Random random = new Random();
        int mineX;
        int mineY;

        for (int i = 0; i < quantity;) {
            mineX = random.nextInt(x);
            mineY = random.nextInt(y);

            if (field[mineX][mineY] == 0) {
                field[mineX][mineY] = -1;
                i++;
            }
        }

        int numb;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if(field[i][j] == 0) {
                    field[i][j] = checkCell(i, j);
                }
            }
        }
    }

    private int checkCell(int x, int y) {
        int dx;
        int dy;

        int result = 0;

        for (int i = 0; i < 8; i++) {
            dx = (int) Math.round(Math.cos(Math.PI / 4 * i));
            dy = (int) Math.round(Math.sin(Math.PI / 4 * i));

            try {
                if (field[x + dx][y + dy] == -1) {
                    result++;
                }
            } catch (IndexOutOfBoundsException e) {
                //
            }
        }

        return result;
    }

    public boolean getMineMarkers(int x, int y) {
        return mineMarkers[x][y];
    }

    private void processMarker(int x, int y) {

        if (field[x][y] >= 0) {
            markersCount = mineMarkers[x][y] ? markersCount - 1 : markersCount + 1;
            mineMarkers[x][y] = !mineMarkers[x][y];
        } else {
            markersCount = mineMarkers[x][y] ? markersCount - 1 : markersCount + 1;
            minesFound = mineMarkers[x][y] ? minesFound - 1 : minesFound + 1;
            mineMarkers[x][y] = !mineMarkers[x][y];
        }
    }

    public void move() {

        String[] command = ui.requestCoordinates().split(" ");

        if ("mine".equals(command[2])) {
            processMarker(Integer.parseInt(command[0]) - 1, Integer.parseInt(command[1]) - 1);
        } else if ("free".equals(command[2])) {
            if (field[Integer.parseInt(command[0]) - 1][Integer.parseInt(command[1]) - 1] == -1) {
                loose = true;
            } else {
                processMove(Integer.parseInt(command[0]) - 1, Integer.parseInt(command[1]) - 1);
            }
        }

    }

    private void processMove(int x, int y) throws ArrayIndexOutOfBoundsException {

        if (x >= field.length || y >= field[0].length || y < 0 || x < 0) {
            return;
        }

        freeCells--;
        if (field[x][y] != 0) {
            if (field[x][y] != -1) {
                processNumbers(x, y);
            }
        } else if (!visibleCells[x][y]) {
            visibleCells[x][y] = true;
            markersCount = mineMarkers[x][y] ? markersCount - 1 : markersCount;
            mineMarkers[x][y] = false;

            processMove(x + 1, y);
            processMove(x - 1, y);
            processMove(x, y + 1);
            processMove(x, y - 1);
            processMove(x - 1, y - 1);
            processMove(x + 1, y - 1);
            processMove(x + 1, y + 1);
            processMove(x - 1, y + 1);

        }
    }

    private void processNumbers(int x, int y) {
        if (x >= field.length || y >= field[0].length || y < 0 || x < 0) {
            return;
        }

        if (!visibleCells[x][y] && field[x][y] > 0) {
            visibleCells[x][y] = true;
            markersCount = mineMarkers[x][y] ? markersCount - 1 : markersCount;
            mineMarkers[x][y] = false;

            processNumbers(x - 1, y);
            processNumbers(x, y + 1);
            processNumbers(x, y - 1);
            processNumbers(x, y + 1);

        }
    }

    public boolean isEnd() {
        return (minesCount == minesFound && minesFound == markersCount) || freeCells == minesCount || loose;
    }

    public boolean isLoose() {
        return loose;
    }

    public void printGame() {
        if (loose) {
            ui.printField(field, visibleCells);
        } else {
            ui.printGame(field, mineMarkers, visibleCells, isEnd());
        }
    }


    public static Field fieldFactory(UI ui) {

        int quantity = ui.getMinesQuantity();

        return new Field(ui, 9, 9, quantity);
    }
}
