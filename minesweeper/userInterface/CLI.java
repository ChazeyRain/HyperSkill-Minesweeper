package minesweeper.userInterface;

import minesweeper.Messages;

import java.util.Scanner;

public class CLI implements UI {

    Scanner scanner = new Scanner(System.in);

    @Override
    public void printField(int[][] cells, boolean[][] visible) {
        System.out.print(" |");
        for (int i = 1; i < cells.length + 1; i++) {
            System.out.print(i);
        }
        System.out.println("|");

        System.out.print("-|");
        for (int i = 1; i < cells.length + 1; i++) {
            System.out.print('-');
        }
        System.out.println("|");

        for (int i = 0; i < cells[0].length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < cells.length; j++) {
                switch (cells[j][i]) {
                    case -1:
                        System.out.print('X');
                        break;
                    case 0:
                        System.out.print(visible[j][i] ? '.' : '/');
                        break;
                    default:
                        System.out.print(visible[j][i] ? cells[j][i] : ".");
                }
            }
            System.out.println("|");
        }

        System.out.print("-|");
        for (int i = 1; i < cells.length + 1; i++) {
            System.out.print('-');
        }
        System.out.println("|");
    }

    @Override
    public void printGame(int[][] cells, boolean[][] markers, boolean[][] visible, boolean isEnd) {
        System.out.print(" |");
        for (int i = 1; i < cells.length + 1; i++) {
            System.out.print(i);
        }
        System.out.println("|");

        System.out.print("-|");
        for (int i = 1; i < cells.length + 1; i++) {
            System.out.print('-');
        }
        System.out.println("|");

        for(int i = 0; i < cells[0].length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < cells.length; j++) {
                switch (cells[j][i]) {
                    case -1 :
                        System.out.print(markers[j][i] ? '*' : '.');
                        break;
                    case 0:
                        if (visible[j][i]) {
                            System.out.print(markers[j][i] ^ isEnd ? '*' : '/');
                        } else {
                            System.out.print(markers[j][i] ^ isEnd ? '*' : '.');
                        }
                        break;
                    default:
                        if (visible[j][i]) {
                            System.out.print(markers[j][i] ? '*' : cells[j][i]);
                        } else {
                            System.out.print(markers[j][i] ? '*' : '.');
                        }
                        break;
                }
            }
            System.out.println("|");
        }

        System.out.print("-|");
        for (int i = 1; i < cells.length + 1; i++) {
            System.out.print('-');
        }
        System.out.println("|");
    }



    @Override
    public int getMinesQuantity() {
        System.out.println("How many mines do you want on the field?");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    @Override
    public String requestCoordinates() {
        System.out.println("Set/unset mines marks or claim a cell as free:");
        String input = scanner.nextLine();
        return input;
    }

    @Override
    public void receiveMessage(Messages msg) {
        System.out.println(msg.getMessage());
    }

    @Override
    public void endGame(boolean lost) {
        System.out.print(lost ? "You stepped on a mine and failed!" : "Congratulations! You found all mines!");
    }
}
