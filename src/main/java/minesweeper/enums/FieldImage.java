package minesweeper.enums;

public enum FieldImage {
    FLAG("flag.png"),
    MINE("mine.png"),
    NO_MINE("nomine.png"),
    ONE("1.png"),
    TWO("2.png"),
    THREE("3.png"),
    FOUR("4.png"),
    FIVE("5.png"),
    SIX("6.png"),
    EMPTY("");

    private String fileName;

    FieldImage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
