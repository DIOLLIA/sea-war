import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Andre on 19.10.2017.
 */
public class Ship {

    Ship() {

    }

    private Point coordinate;
    private int deckAmount;

    public int getDeckAmount() {
        return deckAmount;
    }

    public void setDeckAmount(int deckAmount) {
        this.deckAmount = deckAmount;
    }

    public Ship(int deckAmount) {
        this.deckAmount = deckAmount;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public static Point generateCoordinate() {
        return new Point((int) (Math.random() * 10 + 1), (int) (Math.random() * 10 + 1));

    }

    public static Point getCoordinateFromHuman() {
        int x = 0;
        int y = 0;
        BufferedReader bfreader = new BufferedReader(new InputStreamReader(System.in));
        String coordinateFromHuman = null;

        try {
            coordinateFromHuman = bfreader.readLine();
        } catch (IOException incorrectInsert) {
            System.out.println("неверный ввод");
        }
        char[] coordinateFromHumanToChar = coordinateFromHuman.toCharArray();
        if (coordinateFromHumanToChar[0] == 'A')
            x = 1;
        else if (coordinateFromHumanToChar[0] == 'B')
            x = 2;
        else if (coordinateFromHumanToChar[0] == 'C')
            x = 3;
        else         if (coordinateFromHumanToChar[0]=='D')
            x=4;
        else         if (coordinateFromHumanToChar[0]=='E')
            x=5;
        else         if (coordinateFromHumanToChar[0]=='F')
            x=6;
        else         if (coordinateFromHumanToChar[0]=='G')
            x=7;
        else         if (coordinateFromHumanToChar[0]=='H')
            x=8;
        else         if (coordinateFromHumanToChar[0]=='I')
            x=9;
        else         if (coordinateFromHumanToChar[0]=='J')
            x=10;

        y=Integer.parseInt(String.valueOf(coordinateFromHumanToChar[1]));
        return new Point(y, x);
    }

    static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
