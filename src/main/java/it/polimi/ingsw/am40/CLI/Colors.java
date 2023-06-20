package it.polimi.ingsw.am40.CLI;

public class Colors {

    /**
     * Color class with the colors used for the CLI
     */
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

    /**
     * @return reset color
     */
    public String rst() {
        return ANSI_RESET;
    }

    /**
     * @return black color
     */
    public String black() {
        return ANSI_BLACK;
    }

    /**
     * @return red color
     */
    public String red() {
        return ANSI_RED;
    }

    /**
     * @return green color
     */
    public String green() {
        return ANSI_GREEN;
    }

    /**
     * @return yellow color
     */
    public String yellow() {
        return ANSI_YELLOW;
    }

    /**
     * @return blue color
     */
    public String blue() {
        return ANSI_BLUE;
    }

    /**
     * @return purple color
     */
    public String purple() {
        return ANSI_PURPLE;
    }

    /**
     * @return cyan color
     */
    public String cyan() {
        return ANSI_CYAN;
    }

    /**
     * @return white color
     */
    public String white() {
        return ANSI_WHITE;
    }

    /**
     * @return black background color
     */
    public String blackBg() {
        return ANSI_BLACK_BACKGROUND;
    }

    /**
     * @return red background color
     */
    public String redBg() {
        return ANSI_RED_BACKGROUND;
    }

    /**
     * @return green background color
     */
    public String greenBg() {
        return ANSI_GREEN_BACKGROUND;
    }

    /**
     * @return yellow background color
     */
    public String yellowBg() {
        return ANSI_YELLOW_BACKGROUND;
    }

    /**
     * @return blue background color
     */
    public String blueBg() {
        return ANSI_BLUE_BACKGROUND;
    }

    /**
     * @return purple background color
     */
    public String purpleBg() {
        return ANSI_PURPLE_BACKGROUND;
    }

    /**
     * @return cyan background color
     */
    public String cyanBg() {
        return ANSI_CYAN_BACKGROUND;
    }

    /**
     * @return white background color
     */
    public String whiteBg() {
        return ANSI_WHITE_BACKGROUND;
    }
}
