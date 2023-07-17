package minesweeper.enums;

public record FieldConfig(int numberRows, int numberColumns, int numberMines) {

    public static FieldConfig getConfig(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> getEasyConfig();
            case ADVANCED -> getAdvancedConfig();
            case EXPERT -> getExpertConfig();
        };
    }

    public static FieldConfig getEasyConfig() {
        return new FieldConfig(9, 9, 10);
    }

    public static FieldConfig getAdvancedConfig() {
        return new FieldConfig(16, 16, 40);
    }

    public static FieldConfig getExpertConfig() {
        return new FieldConfig(16, 32, 99);
    }
}
