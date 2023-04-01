package it.polimi.ingsw.am40.CLI;

public class Colors {
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_BLACK = "\u001B[30m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_GREEN = "\u001B[32m";
    private  final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_BLUE = "\u001B[34m";
    private final String ANSI_PURPLE = "\u001B[35m";
    private final String ANSI_CYAN = "\u001B[36m";
    private final String ANSI_WHITE = "\u001B[37m";
    private final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public String rst() {
        return ANSI_RESET;
    }

    public String black() {
        return ANSI_BLACK;
    }

    public String red() {
        return ANSI_RED;
    }

    public String green() {
        return ANSI_GREEN;
    }

    public String yellow() {
        return ANSI_YELLOW;
    }

    public String blue() {
        return ANSI_BLUE;
    }

    public String purple() {
        return ANSI_PURPLE;
    }

    public String cyan() {
        return ANSI_CYAN;
    }

    public String white() {
        return ANSI_WHITE;
    }

    public String blackBg() {
        return ANSI_BLACK_BACKGROUND;
    }

    public String redBg() {
        return ANSI_RED_BACKGROUND;
    }

    public String greenBg() {
        return ANSI_GREEN_BACKGROUND;
    }

    public String yellowBg() {
        return ANSI_YELLOW_BACKGROUND;
    }

    public String blueBg() {
        return ANSI_BLUE_BACKGROUND;
    }

    public String purpleBg() {
        return ANSI_PURPLE_BACKGROUND;
    }

    public String cyanBg() {
        return ANSI_CYAN_BACKGROUND;
    }

    public String whiteBg() {
        return ANSI_WHITE_BACKGROUND;
    }
}
