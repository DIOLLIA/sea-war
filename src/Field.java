/**
 * Created by Andre on 19.10.2017.
 */
public class Field {
    private static final String LETTERS[] = {"A ", "B ", "C ", "D ", "E ", "F ", "G ", "H ", "I ", "J "};
    private String[][] bigGameField = new String[11][11];
    private Ship[] fleet;

    private final String SHOOTED_CELL = "X ";
    private static final String STAR = "* ";
    private final String EMPTY_CELL = "~ ";
    private final String DECK = "0 ";

    public String STAR() {
        return EMPTY_CELL;
    }
    public String getSHOOTED_CELL() {
        return SHOOTED_CELL;
    }


    public String getDECK() {
        return DECK;
    }

    public Ship[] getFleet() {
        return fleet;
    }

    public void setFleet(Ship[] fleet) {
        this.fleet = fleet;
    }

    public String[][] getBigGameField() {
        return bigGameField;
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

    void printTwoFielsdBeside(Field field1, Field field2, String name1, String name2) {

        System.out.println( name1 +"'s field" + "         " +  name2 + "'s field");
        for (int i = 0; i < bigGameField.length; i++) {
            for (int j = 0; j < bigGameField.length; j++) {
                if (field1.bigGameField[i][j].equals(DECK))
                    System.out.print("~ ");
                else if (field1.bigGameField[i][j].equals("~ "))
                    System.out.print("~ ");
                else if (field1.bigGameField[i][j].equals("* "))
                    System.out.print("~ ");
                else
                    System.out.print(field1.bigGameField[i][j]);
            }
            System.out.print("        ");
            for (int j = 0; j < bigGameField.length; j++) {
                if (field2.bigGameField[i][j].equals(DECK))
                    System.out.print("~ ");
                else if (field2.bigGameField[i][j].equals("~ "))
                    System.out.print("~ ");
                else if (field2.bigGameField[i][j].equals("* "))
                    System.out.print("~ ");
                else
                    System.out.print(field2.bigGameField[i][j]);
            }
            System.out.println();
        }
    }

    void printTwoFielsdBesideClear(Field field1, Field field2, String name1, String name2) {

        System.out.println( name1 +"'s field" + "         " +  name2 + "'s field");
        for (int i = 0; i < bigGameField.length; i++) {
            for (int j = 0; j < bigGameField.length; j++) {

                if (field1.bigGameField[i][j].equals("~ "))
                    System.out.print("~ ");
                else if (field1.bigGameField[i][j].equals("* "))
                    System.out.print("~ ");
                else
                    System.out.print(field1.bigGameField[i][j]);
            }
            System.out.print("        ");
            for (int j = 0; j < bigGameField.length; j++) {
                if (field2.bigGameField[i][j].equals("~ "))
                    System.out.print("~ ");
                else if (field2.bigGameField[i][j].equals("* "))
                    System.out.print("~ ");
                else
                    System.out.print(field2.bigGameField[i][j]);
            }
            System.out.println();
        }
    }

    void seeYourCoordinateCorrect(Ship.Point coordinate) {

        String literABC = LETTERS[coordinate.getY() - 1];
        String numberOfLine = String.valueOf(coordinate.getX());
        System.out.println("SHOT IN " + literABC + " " + numberOfLine);
    }
    void createDeadPointsAroundShip(String[][] bigGameField, Field field) {
        for (int y = 1; y < bigGameField.length; y++) {
            for (int x = 1; x < bigGameField.length; x++) {

                if (bigGameField[y][x].equals(SHOOTED_CELL)) {//5
                    if (x <= 10 && y < 10 && x > 1 && y >= 1) {
                        if (bigGameField[y + 1][x - 1].equals(STAR)) {//1
                            bigGameField[y + 1][x - 1] = ("` ");
                        }
                    }
                    if (x <= 10 && y < 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y + 1][x].equals(STAR)) {//2
                            bigGameField[y + 1][x] = ("` ");
                        }
                    }
                    if (x < 10 && y < 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y + 1][x + 1].equals(STAR)) {//3
                            bigGameField[y + 1][x + 1] = ("` ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x > 1 && y >= 1) {
                        if (bigGameField[y][x - 1].equals(STAR)) {//4
                            bigGameField[y][x - 1] = ("` ");
                        }
                    }
                    if (x < 10 && y <= 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y][x + 1].equals(STAR)) {//6
                            bigGameField[y][x + 1] = ("` ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x > 1 && y > 1) {
                        if (bigGameField[y - 1][x - 1].equals(STAR)) {//7
                            bigGameField[y - 1][x - 1] = ("` ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x >= 1 && y > 1) {
                        if (bigGameField[y - 1][x].equals(STAR)) {//8
                            bigGameField[y - 1][x] = ("` ");
                        }
                    }
                    if (x < 10 && y <= 10 && x >= 1 && y > 1) {
                        if (bigGameField[y - 1][x + 1].equals(STAR)) {//9
                            bigGameField[y - 1][x + 1] = ("` ");
                        }
                    }

                }
            }


        }

    }
}
