import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
/**
 * Created by Andre on 19.10.2017.
 */
public class Game {

    private static String firstPlayer = "";
    private TypeOfGame typeOfGame;
    private Gamer gamer1;
    private Gamer gamer2;

    public Gamer getGamer1() {
        return gamer1;
    }

    public void setGamer1(Gamer gamer1) {
        this.gamer1 = gamer1;
    }

    public Gamer getGamer2() {
        return gamer2;
    }

    public void setGamer2(Gamer gamer2) {
        this.gamer2 = gamer2;
    }

    public TypeOfGame getTypeOfGame() {
        return typeOfGame;
    }

    public void setTypeOfGame(TypeOfGame typeOfGame) {
        this.typeOfGame = typeOfGame;
    }

    public void start() {
        greetings();
        setTypeOfGame(chooseTypeOfGame());
        initGamers();
        initFields();
        initFleet();
        gamer1.getField().printTwoFielsdBeside(gamer2.getField(),gamer1.getField(),gamer1.getName(),gamer2.getName());
        switch (firstPlayer) {
            case ("Computer"): {
                while (gamer1.getAmountOfShips() != 0 && gamer2.getAmountOfShips() != 0) {
                    shoot(gamer1);
                    if (gamer1.getAmountOfShips() > 0 && gamer2.getAmountOfShips() > 0)
                        shoot(gamer2);
                }
                break;
            }
            case ("Human"): {
                while (gamer1.getAmountOfShips() != 0) {
                    shoot(gamer1);
                    if (isItVictory(gamer1.getField(), gamer2.getField()))
                        shoot(gamer2);
                }
                break;
            }
        }
    }

    public void shoot(Gamer gamer) {
        System.out.println("turn of gamer " + gamer.getName() + " to SHOT!");
        if (gamer.getTypeOfGamer().equals(TypeOfGamer.HUMAN)) {
            while (!checkResultOfShoot(gamer.getField(), Ship.getCoordinateFromHuman(), gamer)) ;
        } else
            while (!checkResultOfShoot(gamer.getField(), Ship.generateCoordinate(), gamer)) {
            }
    }

    private void initFleet() {
        Field field1 = getGamer1().getField();
        field1.setFleet(createShips());
        Ship[] ships1 = field1.getFleet();
        placeShips(field1, ships1);

        Field field2 = gamer2.getField();
        field2.setFleet(createShips());
        Ship[] ships2 = field2.getFleet();
        placeShips(field2, ships2);
    }

    private void initFields() {
        Gamer gamer = getGamer1();
        gamer.setField(Field.getBaseFieldInstance());

        Gamer gamer2 = getGamer2();
        gamer2.setField(Field.getBaseFieldInstance());
    }

    private void initGamers() {
        switch (getTypeOfGame()) {
            case HUMAN_VS_COMPUTER:
                Gamer gamer1 = new Gamer(TypeOfGamer.HUMAN);
                gamer1.setName(getNameOfPlayer());

                setGamer1(gamer1);
                setGamer2(new Gamer(TypeOfGamer.COMPUTER, "Windows 1988"));
                break;
            case COMPUTER_VS_COMPUTER:
                setGamer1(new Gamer(TypeOfGamer.COMPUTER, "MSDOS"));
                setGamer2(new Gamer(TypeOfGamer.COMPUTER, "Windows 1988"));
                break;
        }
    }

    private TypeOfGame chooseTypeOfGame() {
        TypeOfGame gamerVsComputer = null;
        System.out.println("choose type of battle: \n type 1 for humanVSkomp\n type 2 for kompVSkomp");
        Scanner sc = new Scanner(System.in);
        while (gamerVsComputer == null) {
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
                    gamerVsComputer = TypeOfGame.HUMAN_VS_COMPUTER;
                    firstPlayer = "Human";
                    break;
                case 2:
                    System.out.println("you've choosed kompVSkomp");
                    gamerVsComputer = TypeOfGame.COMPUTER_VS_COMPUTER;
                    firstPlayer = "Computer";
                    break;
                default:
                    System.out.println("Choose correctly! type 1 for humanVSkomp or type 2 for kompVSkomp");
                    gamerVsComputer = null;
                    sc.hasNext();
            }
        }
        return gamerVsComputer;
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
        String deck = gamer1.getField().getDECK();
        Ship.Point coordinate = ship.getCoordinate();
        boolean isShipPlaced = false;
        int[] delta = chooseDirection();
        int cellsAmountInChoosedDirectionX = (ship.getDeckAmount() - 1) * delta[0];
        int cellsAmountInChoosedDirectionY = (ship.getDeckAmount() - 1) * delta[1];
        if (coordinate.getX() + cellsAmountInChoosedDirectionX >= 1 && //1  is the start of coordinates
                coordinate.getX() + cellsAmountInChoosedDirectionX <= 10 && //10  is the end of coordinates
                coordinate.getY() + cellsAmountInChoosedDirectionY <= 10 &&
                coordinate.getY() + cellsAmountInChoosedDirectionY >= 1) {
            if (isShipExistOnChoosedDirection(bigGameField, ship, delta)) {
               // bigGameField[coordinate.getX()][coordinate.getY()] = DECK;
                bigGameField[coordinate.getX()][coordinate.getY()] = deck;
                for (int i = 1; i < ship.getDeckAmount(); i++) {
                    bigGameField[coordinate.getX() + delta[0] * i][coordinate.getY() + delta[1] * i] = deck;
                }
                isShipPlaced = true;
            } else {
                Ship.generateCoordinate();
            }
        }
        return isShipPlaced;
    }

    protected int[] chooseDirection() {
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

    private static void greetings() {
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

                if (bigGameField[y][x].equals(field.getDECK())) {//5
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
        String deck = field1.getDECK();
        boolean victory = true;
        int a = 0;
        int b = 0;

        for (int i = 0; i < field1.getBigGameField().length; i++) {
            if (a != 0) {
                break;
            }
            for (int j = 0; j < field1.getBigGameField().length; j++) {
                if (field1.getBigGameField()[i][j].equals(deck)) {
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
                    if (field2.getBigGameField()[i][j].equals(deck)) {
                        b++;
                        break;
                    }
                }
            }
        }
        if (a == 0) {
            victory = false;
            System.out.println("VICTORY OF " + gamer1.getName());
        }
        if (b == 0 && a != 0) {
            victory = false;
            System.out.println("VICTORY OF " + gamer2.getName());
        }
        return victory;
    }

    private boolean checkResultOfShoot(Field field, Ship.Point coordinate, Gamer gamer) {
        String deck = field.getDECK();
        boolean hit = false;
        boolean wonded = false;
        int[] shootedCell = {coordinate.getX(), coordinate.getY()};
        String[][] bigGameField = field.getBigGameField();

        if (bigGameField[shootedCell[0]][shootedCell[1]].equals(deck)) {
            field.seeYourCoordinateCorrect(coordinate);
            bigGameField[shootedCell[0]][shootedCell[1]] = field.getSHOOTED_CELL();

            if (coordinate.getX() > 1) {
                if (bigGameField[shootedCell[0] - 1][shootedCell[1]].equals(deck)) {
                    wonded = true;
                }
                if (wonded == false) {
                    if (bigGameField[shootedCell[0] - 1][shootedCell[1]].equals(field.getSHOOTED_CELL())
                            && coordinate.getX() > 2 && bigGameField[shootedCell[0] - 2][shootedCell[1]].equals(deck))
                        wonded = true;
                }
                if (wonded == false) {
                    if (coordinate.getX() > 3 && bigGameField[shootedCell[0] - 2][shootedCell[1]].equals(field.getSHOOTED_CELL())
                            && bigGameField[shootedCell[0] - 1][shootedCell[1]].equals(field.getSHOOTED_CELL())
                            && bigGameField[shootedCell[0] - 3][shootedCell[1]].equals(deck))
                        wonded = true;
                }
            }
            if (coordinate.getX() < 10) {
                if (bigGameField[shootedCell[0] + 1][shootedCell[1]].equals(deck)) {
                    wonded = true;
                }
                if (wonded == false) {
                    if (bigGameField[shootedCell[0] + 1][shootedCell[1]].equals(field.getSHOOTED_CELL())
                            && coordinate.getX() < 9 && bigGameField[shootedCell[0] + 2][shootedCell[1]].equals(deck))
                        wonded = true;
                }
                if (wonded == false) {
                    if (coordinate.getX() < 8 && bigGameField[shootedCell[0] + 1][shootedCell[1]].equals(field.getSHOOTED_CELL())
                            && bigGameField[shootedCell[0] + 2][shootedCell[1]].equals(field.getSHOOTED_CELL())
                            && bigGameField[shootedCell[0] + 3][shootedCell[1]].equals(deck))
                        wonded = true;
                }
            }

            if (coordinate.getY() > 1) {
                if (bigGameField[shootedCell[0]][shootedCell[1] - 1].equals(deck)) {
                    wonded = true;
                }

                if (wonded == false) {
                    if (bigGameField[shootedCell[0]][shootedCell[1] - 1].equals(field.getSHOOTED_CELL())
                            && coordinate.getY() > 2 && bigGameField[shootedCell[0]][shootedCell[1] - 2].equals(deck))
                        wonded = true;
                }
                if (wonded == false) {
                    if (coordinate.getY() > 3 && bigGameField[shootedCell[0]][shootedCell[1] - 1].equals(field.getSHOOTED_CELL())
                            && bigGameField[shootedCell[0]][shootedCell[1] - 2].equals(field.getSHOOTED_CELL())
                            && bigGameField[shootedCell[0]][shootedCell[1] - 3].equals(deck))
                        wonded = true;
                }
            }
            if (coordinate.getY() < 10) {
                if (bigGameField[shootedCell[0]][shootedCell[1] + 1].equals(deck)) {
                    wonded = true;
                }
                if (wonded == false) {
                    if (bigGameField[shootedCell[0]][shootedCell[1] + 1].equals(field.getSHOOTED_CELL())
                            && coordinate.getY() < 9 && bigGameField[shootedCell[0]][shootedCell[1] + 2].equals(deck))
                        wonded = true;
                }
                if (wonded == false) {
                    if (coordinate.getY() < 8 && bigGameField[shootedCell[0]][shootedCell[1] + 1].equals(field.getSHOOTED_CELL())
                            && bigGameField[shootedCell[0]][shootedCell[1] + 2].equals(field.getSHOOTED_CELL())
                            && bigGameField[shootedCell[0]][shootedCell[1] + 3].equals(deck))
                        wonded = true;
                }
            }
            if (wonded == true) {
                System.out.println("wounded!");
                field.printTwoFielsdBeside(gamer1.getField(), gamer2.getField(), gamer1.getName(), gamer2.getName());
                return false;
            } else {
                System.out.println("drowned");
                field.createDeadPointsAroundShip(gamer.getField().getBigGameField());
                field.printTwoFielsdBeside(gamer1.getField(), gamer2.getField(), gamer1.getName(), gamer2.getName());
                gamer.decreaseAmountOfShips();
                if (gamer.getAmountOfShips() == 0) {
                    field.createDeadPointsAroundShip(gamer.getField().getBigGameField());
                    victory();
                    gamer.getField().printTwoFielsdBesideClear(gamer1.getField(), gamer2.getField(), gamer1.getName(), gamer2.getName());
                    return true;
                }
                return false;
            }
        } else if (bigGameField[shootedCell[0]][shootedCell[1]].
                equals("* ") || bigGameField[shootedCell[0]][shootedCell[1]].
                equals("~ ")) {
            field.seeYourCoordinateCorrect(coordinate);
            System.out.println("missed");
            bigGameField[shootedCell[0]][shootedCell[1]] = "\" ";
            field.printTwoFielsdBeside(gamer1.getField(), gamer2.getField(), gamer1.getName(), gamer2.getName());
            return true;
        } else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("\" ")
                || bigGameField[shootedCell[0]][shootedCell[1]].equals(field.getSHOOTED_CELL())) {
            if (firstPlayer.equals("Human")) {
                System.out.println("That cell is almost shoted");
            }
            return false;
        } else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("` ")) {
            if (firstPlayer.equals("Human")) {
                System.out.println("there is not necessary to shot here, it's dead area");
            }
            return false;
        }
        return hit;
    }

    private void victory() {
        if (gamer1.getAmountOfShips() == 0) {
            System.out.println("Победил Игрок " + gamer1.getName());

        }
        if (gamer2.getAmountOfShips() == 0)

        {
            System.out.println("Победил Игрок " + gamer2.getName());
        }

    }
}
