import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Andre on 19.10.2017.
 */
public class Game {
    private Ship.Point coordinate;
    private int oneDeckShipAmount = 4;
    private int twoDecksShipAmount = 3;
    private int threeDecksShipAmount = 2;
    private int fourDecksShipAmount = 1;
    private Ship[] ships;
    private Gamer gamer1;
    private Gamer gamer2;
    private final String DECK = "0 ";

    void start() {
        greetings();
        gamer1 = new Gamer(Gamer.TypeOfPlayer.Computer);
        gamer1.setField(Field.getBaseFieldInstance());
        gamer1.setName(getNameOfPlayer());
        Field field1 = gamer1.getField();
        field1.setFleet(createShips());
        ships = field1.getFleet();
        placeShips(field1, ships);
        field1.print();
    }

    private void placeShips(Field field, Ship[] ships) {
        boolean isFreeCoordinate = false;
        boolean isShipPlaced = false;
        String[][] bigGameField = field.getBigGameField();
        //todo @LR make coordinate as filed of class Ship
        // Ship.Point coordinate = new Ship.Point(-1, -1);//negative initialization because these values are unusable on th field

        for (Ship ship : ships) {
            while (!isFreeCoordinate) {
                coordinate = Ship.getCoordinate();
                isFreeCoordinate = field.isFreeCoordinate(coordinate, field.getBigGameField());
            }
            while (!isShipPlaced) {
                isShipPlaced = chekFreeSpaceForPlaceTheShip(bigGameField, ship);
            }
            starsAroundTheShip(bigGameField, field);
            isFreeCoordinate = false;
            isShipPlaced = false;
        }
    }

    private boolean isShipExistOnChoosedDirection(String[][] bigGameField, Ship decks, int[] delta) {
        int tmp = 0;
        boolean itsFree = false;
        switch (delta[0]) {
            case (0): {
                break;
            }
            case (-1): {
                for (int i = 0; i < decks.getDeckAmount(); i++) {
                    if (bigGameField[coordinate.getX() + (i * delta[0])][coordinate.getY()].equals("~ ")) {
                        tmp++;
                    }
                }
                if (tmp == decks.getDeckAmount()) {
                    itsFree = true;
                }
                break;
            }
            case (1): {
                for (int i = 0; i < decks.getDeckAmount(); i++) {
                    if (bigGameField[coordinate.getX() + i * delta[0]][coordinate.getY()].equals("~ ")) {
                        tmp++;
                    }
                }
            }
            if (tmp == decks.getDeckAmount()) {
                itsFree = true;
            }
            break;
        }

        switch (delta[1]) {
            case (0):
                break;
            case (1): {
                for (int i = 0; i < decks.getDeckAmount(); i++) {
                    if (bigGameField[coordinate.getX()][coordinate.getY() + i * delta[1]].equals("~ ")) {
                        tmp++;
                    }
                }
                if (tmp == decks.getDeckAmount()) {
                    itsFree = true;
                }
                break;
            }
            case (-1): {
                for (int i = 0; i < decks.getDeckAmount(); i++) {
                    if (bigGameField[coordinate.getX()][coordinate.getY() + i * delta[1]].equals("~ ")) {
                        tmp++;
                    }
                }
                if (tmp == decks.getDeckAmount()) {
                    itsFree = true;
                }
                break;
            }
        }
        return itsFree;
    }

    private boolean chekFreeSpaceForPlaceTheShip(String[][] bigGameField, Ship ship) {
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
                System.out.println("Ship" + ship.getDeckAmount() + " палубный построен с начальной координатой X: " + coordinate.getX() + "и Y: " + coordinate.getY());
                isShipPlaced = true;
            } else {
                Ship.getCoordinate();
            }
        }
        return isShipPlaced;
    }

    private int[] chooseDirection(int deckAmount) {
        int a;
        Random random = new Random();
        a = random.nextInt(4) + 1;
        int[] delta = {0, 0};
        switch (a) {
            case (1): //UP
                // for (int i = 1; i < deckAmount; i++) {
                delta[1] = -1;
                //  }
                break;
            case (2): //DOWN
                // for (int i = 1; i < deckAmount; i++) {
                delta[1] = 1;
                //  }
                break;
            case (3): //LEFT
                //  for (int i = 1; i < deckAmount; i++) {
                delta[0] = -1;
                // }
                break;
            case (4): //RIGHT
                // for (int i = 1; i < deckAmount; i++) {
                delta[0] = 1;
                //}
                break;
        }
        return delta;
    }

    public Ship[] createShips() {
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
        System.out.println("введи своё имя: ");
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
                        if (bigGameField[y + 1][x - 1].equals(field.getEMPTY_CELL())) {//1
                            bigGameField[y + 1][x - 1] = ("* ");
                        }
                    }
                    if (x <= 10 && y < 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y + 1][x].equals(field.getEMPTY_CELL())) {//2
                            bigGameField[y + 1][x] = ("* ");
                        }
                    }
                    if (x < 10 && y < 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y + 1][x + 1].equals(field.getEMPTY_CELL())) {//3
                            bigGameField[y + 1][x + 1] = ("* ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x > 1 && y >= 1) {
                        if (bigGameField[y][x - 1].equals(field.getEMPTY_CELL())) {//4
                            bigGameField[y][x - 1] = ("* ");
                        }
                    }
                    if (x < 10 && y <= 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y][x + 1].equals(field.getEMPTY_CELL())) {//6
                            bigGameField[y][x + 1] = ("* ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x > 1 && y > 1) {
                        if (bigGameField[y - 1][x - 1].equals(field.getEMPTY_CELL())) {//7
                            bigGameField[y - 1][x - 1] = ("* ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x >= 1 && y > 1) {
                        if (bigGameField[y - 1][x].equals(field.getEMPTY_CELL())) {//8
                            bigGameField[y - 1][x] = ("* ");
                        }
                    }
                    if (x < 10 && y <= 10 && x >= 1 && y > 1) {
                        if (bigGameField[y - 1][x + 1].equals(field.getEMPTY_CELL())) {//9
                            bigGameField[y - 1][x + 1] = ("* ");
                        }
                    }

                }
            }
        }
    }
}
