/**
 * Created by Andre on 19.10.2017.
 */
public class Gamer {
    private int amountOfShips = 10;
    private String name;
    private Field field;
    private TypeOfGamer typeOfGamer;

    public int getAmountOfShips() {
        return amountOfShips;
    }

    public TypeOfGamer getTypeOfGamer() {
        return typeOfGamer;
    }

    public Gamer(TypeOfGamer typeOfGamer) {
        this.typeOfGamer = typeOfGamer;
    }

    public Gamer(TypeOfGamer typeOfGamer, String name) {
        this.typeOfGamer = typeOfGamer;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void decreaseAmountOfShips() {
        this.amountOfShips--;
    }
}