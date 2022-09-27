package minesweeper;

public interface FieldExposer {

    void revealEntireField();

    void revealValue(Value field);

    void revealMine(Mine field);

    void tagField(Field field);

    void setField(Field[][] field);

}
