/**
 * Created by Andre on 19.10.2017.
 */
public class Field {
    private static final String LETTERS[] = {"A ", "B ", "C ", "D ", "E ", "F ", "G ", "H ", "I ", "G "};

    private String[][] bigGameField = new String[11][11];
    private Ship[] fleet;

    public String getEMPTY_CELL() {
        return EMPTY_CELL;
    }

    private final String EMPTY_CELL = "~ ";
    private final String DECK = "0 ";

    public Ship[] getFleet() {
        return fleet;
    }

    public void setFleet(Ship[] fleet) {
        this.fleet = fleet;
    }

    public String[][] getBigGameField() {
        return bigGameField;
    }

    public void setBigGameField(String[][] bigGameField) {
        this.bigGameField = bigGameField;
    }

    private void initFieldLetters() {
        for (int i = 0, j = 1; j < bigGameField[i].length; j++) { //создание линии букв
            this.bigGameField[i][j] = String.valueOf(LETTERS[j - 1]);
        }
    }

    private void initFieldNumbers() {
        for (int i = 1, j = 0; i < bigGameField.length; i++) { // создаем столбец цифр
            if (i < 10)
                this.bigGameField[i][j] = String.valueOf(i + " ");
            else
                this.bigGameField[i][j] = String.valueOf(i);
        }
    }

    private void initGameField() {
        bigGameField[0][0] = "  ";
        for (int i = 1; i < bigGameField.length; i++) {
            for (int j = 1; j < bigGameField.length; j++) {
                this.bigGameField[i][j] = "~ ";
            }
        }
    }

    public static Field getBaseFieldInstance() {
        Field field = new Field();

        field.initFieldLetters();
        field.initFieldNumbers();
        field.initGameField();

        return field;
    }

    public boolean isFreeCoordinate(Ship.Point coordinate, String[][] playerField) {
        return playerField[coordinate.getX()][coordinate.getY()].equals(EMPTY_CELL);
    }

    void print() { // вывод поля на печать
        for (int i = 0; i < bigGameField.length; i++) {
            for (int j = 0; j < bigGameField.length; j++) {
                System.out.print(bigGameField[i][j]);
            }
            System.out.println();
        }
    }
}
