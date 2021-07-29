package minesweeper;

public enum Messages {
    MOVE_NUMBER("There is a number here! ");

    private String msg;

    Messages (String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }
}
