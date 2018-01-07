import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


/*TODO рисовать поле после каждого выстрела (ранил, убил, не попал...)
сделать вторую сложность для обсрела раненой клетки
после потомления игроком 1 корабля иногда происходит зацикливание выведения поля UPD проверить еще раз!!!! возможно исправлено
*/

/**
 * Created by Andre on 19.10.2017.
 */
public class Game {
    private static int counter = 0;
    Gamer.TypeOfPlayer typeOfPlayer;
    private Ship[] ships;
    private Gamer gamer1;
    private Gamer gamer2;
    private final String DECK = "0 ";
    Gun gun = new Gun();

    public String nameOfFirstPlayer;
    static public String nameOfKompukter;

    public Gamer getGamer1() {
        return gamer1;
    }

    void start() {
        greetings();
        gamer1 = new Gamer(typeOfPlayer = typeOfGamer());
        gamer1.setField(Field.getBaseFieldInstance());
        if (typeOfPlayer.equals(Gamer.TypeOfPlayer.Computer)) {
            gamer1.setName("KoMPukTeP###111");
        } else {
            gamer1.setName(getNameOfPlayer());
        }
        Field field1 = gamer1.getField();
        field1.setFleet(createShips());
        ships = field1.getFleet();
        placeShips(field1, ships);
        gamer2 = new Gamer(Gamer.TypeOfPlayer.Computer);
        gamer2.setField(Field.getBaseFieldInstance());
        gamer2.setName("KOMP2ZZ01ver");
        Field field2 = gamer2.getField();
        field2.setFleet(createShips());
        ships = field2.getFleet();
        placeShips(field2, ships);

        nameOfFirstPlayer = gamer1.getName();
        nameOfKompukter = gamer2.getName();
        do {

            shoot(field1, field2);
        }
         while (isItVictory(field1, field2));
      //  while (!Gun.allShipsAreDead1 && !Gun.allShipsAreDead2);

        field1.printTwoFielsdBesideClear(field1, field2, nameOfFirstPlayer, nameOfKompukter);
    }

    public void shoot(Field field1, Field field2) {
        if (counter % 2 == 0) {
            System.out.println("turn of " + gamer2.getName() + ", shoot in field 1");
            while (!gun.isShotFinished(field1, Ship.generateCoordinate(), typeOfPlayer)) {
                // field1.printTwoFielsdBeside(field1, field2, nameOfFirstPlayer, nameOfKompukter);
            }
            counter++;
        } else if (typeOfPlayer.equals(Gamer.TypeOfPlayer.Human)) {
            humanShot(field2);
        } else if (typeOfPlayer.equals(Gamer.TypeOfPlayer.Computer)) {
            System.out.println(gamer1.getName() + ", shoot in field 2");
            while (!gun.isShotFinished(field2, Ship.generateCoordinate(), typeOfPlayer)) {
                // field1.printTwoFielsdBeside(field1, field2, nameOfFirstPlayer, nameOfKompukter);
            }
            counter++;
        }
        field1.printTwoFielsdBeside(field1, field2, nameOfFirstPlayer, nameOfKompukter);
    }

    public void humanShot(Field computersField) {
        String humanName = getGamer1().getName();
        System.out.println(humanName + ", type coordinate in <<literaNUMBER>> format, for example D2");
        while (!gun.isShotFinished(computersField, Ship.getCoordinateFromHuman(), typeOfPlayer)) {
            computersField.printTwoFielsdBeside(gamer1.getField(), gamer2.getField(), nameOfFirstPlayer, nameOfKompukter);
        }
        counter++;
    }

    private void placeShips(Field field, Ship[] ships) {
        boolean isFreeCoordinate = false;
        boolean isShipPlaced = false;
        String[][] bigGameField = field.getBigGameField();

        for (Ship ship : ships) {
            Ship.Point coordinate;
            while (!isFreeCoordinate) {
                ship.setCoordinate(Ship.generateCoordinate());
                coordinate = ship.getCoordinate();
                isFreeCoordinate = field.isFreeCoordinate(coordinate, field.getBigGameField());
            }
            while (!isShipPlaced) {
                isShipPlaced = checkFreeSpaceForPlaceTheShip(bigGameField, ship);
            }
            starsAroundTheShip(bigGameField, field);
            isFreeCoordinate = false;
            isShipPlaced = false;
        }
    }

    private boolean isShipExistOnChoosedDirection(String[][] bigGameField, Ship ship, int[] delta) {
        int tmp = 0;
        boolean itsFree = false;
        switch (delta[0]) {
            case (0): {
                break;
            }
            case (-1): {
                for (int i = 0; i < ship.getDeckAmount(); i++) {
                    if (bigGameField[ship.getCoordinate().getX() + (i * delta[0])][ship.getCoordinate().getY()].equals("~ ")) {
                        tmp++;
                    }
                }
                if (tmp == ship.getDeckAmount()) {
                    itsFree = true;
                }
                break;
            }
            case (1): {
                for (int i = 0; i < ship.getDeckAmount(); i++) {
                    if (bigGameField[ship.getCoordinate().getX() + i * delta[0]][ship.getCoordinate().getY()].equals("~ ")) {
                        tmp++;
                    }
                }
            }
            if (tmp == ship.getDeckAmount()) {
                itsFree = true;
            }
            break;
        }

        switch (delta[1]) {
            case (0):
                break;
            case (1): {
                for (int i = 0; i < ship.getDeckAmount(); i++) {
                    if (bigGameField[ship.getCoordinate().getX()][ship.getCoordinate().getY() + i * delta[1]].equals("~ ")) {
                        tmp++;
                    }
                }
                if (tmp == ship.getDeckAmount()) {
                    itsFree = true;
                }
                break;
            }
            case (-1): {
                for (int i = 0; i < ship.getDeckAmount(); i++) {
                    if (bigGameField[ship.getCoordinate().getX()][ship.getCoordinate().getY() + i * delta[1]].equals("~ ")) {
                        tmp++;
                    }
                }
                if (tmp == ship.getDeckAmount()) {
                    itsFree = true;
                }
                break;
            }
        }
        return itsFree;
    }

    private boolean checkFreeSpaceForPlaceTheShip(String[][] bigGameField, Ship ship) {
        Ship.Point coordinate = ship.getCoordinate();
        boolean isShipPlaced = false;
        int[] delta = chooseDirection(ship.getDeckAmount());
        int cellsAmountInChoosedDirectionX = (ship.getDeckAmount() - 1) * delta[0];
        int cellsAmountInChoosedDirectionY = (ship.getDeckAmount() - 1) * delta[1];
        if (coordinate.getX() + cellsAmountInChoosedDirectionX >= 1 && //1  is the start of coordinates
                coordinate.getX() + cellsAmountInChoosedDirectionX <= 10 && //10  is the end of coordinates
                coordinate.getY() + cellsAmountInChoosedDirectionY <= 10 &&
                coordinate.getY() + cellsAmountInChoosedDirectionY >= 1) {
            if (isShipExistOnChoosedDirection(bigGameField, ship, delta)) {
                bigGameField[coordinate.getX()][coordinate.getY()] = DECK;
                for (int i = 1; i < ship.getDeckAmount(); i++) {
                    bigGameField[coordinate.getX() + delta[0] * i][coordinate.getY() + delta[1] * i] = DECK;
                }
                isShipPlaced = true;
            } else {
                Ship.generateCoordinate();
            }
        }
        return isShipPlaced;
    }

    protected int[] chooseDirection(int deckAmount) {
        int a;
        Random random = new Random();
        a = random.nextInt(4) + 1;
        int[] delta = {0, 0};
        switch (a) {
            case (1):
                delta[1] = -1;
                break;
            case (2):
                delta[1] = 1;
                break;
            case (3):
                delta[0] = -1;
                break;
            case (4):
                delta[0] = 1;
                break;
        }
        return delta;
    }

    public Ship[] createShips() {
        int oneDeckShipAmount = 4;
        int twoDecksShipAmount = 3;
        int threeDecksShipAmount = 2;
        Ship[] ships = new Ship[10];
        int index = 0;

        ships[index++] = new Ship(4);

        for (int i = 0; i < threeDecksShipAmount; i++) {
            ships[index++] = new Ship(3);
        }
        for (int i = 0; i < twoDecksShipAmount; i++) {
            ships[index++] = new Ship(2);
        }
        for (int i = 0; i < oneDeckShipAmount; i++) {
            ships[index++] = new Ship(1);
        }

        return ships;
    }

    public static void greetings() {
        System.out.println("SeaBattle v0.1");
    }

    private String getNameOfPlayer() {
        String nameOfPlayer = "";
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("enter your name: ");
        try {
            nameOfPlayer = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameOfPlayer;
    }

    void starsAroundTheShip(String[][] bigGameField, Field field) {
        for (int y = 1; y < bigGameField.length; y++) {
            for (int x = 1; x < bigGameField.length; x++) {

                if (bigGameField[y][x].equals(DECK)) {//5
                    if (x <= 10 && y < 10 && x > 1 && y >= 1) {
                        if (bigGameField[y + 1][x - 1].equals(field.STAR())) {//1
                            bigGameField[y + 1][x - 1] = ("* ");
                        }
                    }
                    if (x <= 10 && y < 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y + 1][x].equals(field.STAR())) {//2
                            bigGameField[y + 1][x] = ("* ");
                        }
                    }
                    if (x < 10 && y < 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y + 1][x + 1].equals(field.STAR())) {//3
                            bigGameField[y + 1][x + 1] = ("* ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x > 1 && y >= 1) {
                        if (bigGameField[y][x - 1].equals(field.STAR())) {//4
                            bigGameField[y][x - 1] = ("* ");
                        }
                    }
                    if (x < 10 && y <= 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y][x + 1].equals(field.STAR())) {//6
                            bigGameField[y][x + 1] = ("* ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x > 1 && y > 1) {
                        if (bigGameField[y - 1][x - 1].equals(field.STAR())) {//7
                            bigGameField[y - 1][x - 1] = ("* ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x >= 1 && y > 1) {
                        if (bigGameField[y - 1][x].equals(field.STAR())) {//8
                            bigGameField[y - 1][x] = ("* ");
                        }
                    }
                    if (x < 10 && y <= 10 && x >= 1 && y > 1) {
                        if (bigGameField[y - 1][x + 1].equals(field.STAR())) {//9
                            bigGameField[y - 1][x + 1] = ("* ");
                        }
                    }

                }
            }
        }
    }

    boolean isItVictory(Field field1, Field field2) {
        boolean victory = true;
        int a = 0;
        int b = 0;

        for (int i = 0; i < field1.getBigGameField().length; i++) {
            if (a != 0) {
                break;
            }
            for (int j = 0; j < field1.getBigGameField().length; j++) {
                if (field1.getBigGameField()[i][j].equals(DECK)) {
                    a++;
                    break;
                }
            }
        }
        if (a > 0) {
            for (int i = 0; i < field2.getBigGameField().length; i++) {
                if (b != 0) {
                    break;
                }
                for (int j = 0; j < field2.getBigGameField().length; j++) {
                    if (field2.getBigGameField()[i][j].equals(DECK)) {
                        b++;
                        break;
                    }
                }
            }
        }
        if (a == 0) {
            victory = false;
            System.out.println("VICTORY OF " + nameOfKompukter);
        }
        if (b == 0 && a != 0) {
            victory = false;
            System.out.println("VICTORY OF " + nameOfFirstPlayer);
        }
        return victory;
    }

    private Gamer.TypeOfPlayer typeOfGamer() {
        System.out.println("choose type of battle: \n type 1 for humanVSkomp\n type 2 for kompVSkomp");
        Scanner sc = new Scanner(System.in);
        while (typeOfPlayer == null) {
            int typeOfSecondPlayer = 0;
            try {
                typeOfSecondPlayer = sc.nextInt();
            } catch (InputMismatchException wrongNumber) {
                System.out.println("Your choise is 1 or 2 only");
                sc.next();
            }

            switch (typeOfSecondPlayer) {
                case 1:
                    System.out.println("you've choosed humanVSkomp");
                    typeOfPlayer = Gamer.TypeOfPlayer.Human;
                    break;
                case 2:
                    System.out.println("you've choosed kompVSkomp");
                    typeOfPlayer = Gamer.TypeOfPlayer.Computer;
                    break;
                default:
                    System.out.println("Choose correctly! type 1 for humanVSkomp or type 2 for kompVSkomp");
                    typeOfPlayer = null;
                    sc.hasNext();
            }
        }
        return typeOfPlayer;
    }
}