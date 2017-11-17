public class Gun extends Ship {
    Game game;

    String DECK = "0 ";

    protected void shoot(Field field1, Field field2) {

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                System.out.println("ход игрока 2, стреляем в поле 1");
                while (!isShotFinished(field1, Ship.generateCoordinate())) ;
            } else {
                System.out.println("ход игрока 1, стреляем в поле 2");
                while (!isShotFinished(field2, Ship.generateCoordinate())) ;
            }
            field1.printTwoFielsdBeside(field1, field2);
        }
    }

    protected boolean isShotFinished(Field field, Ship.Point coordinate) {
        boolean hit = false;
        boolean wonded = false;
        int[] shootedCell = {coordinate.getX(), coordinate.getY()};
        field.seeYourCoordinateCorrect(coordinate);
        String[][] bigGameField = field.getBigGameField();
        if (bigGameField[shootedCell[0]][shootedCell[1]].equals(DECK)) {
            bigGameField[shootedCell[0]][shootedCell[1]] = "W ";
            if (coordinate.getX() > 1) {
                if (bigGameField[shootedCell[0] - 1][shootedCell[1]].equals(DECK)) {
                    wonded = true;
                }
            }
            if (coordinate.getX() < 10) {
                if (bigGameField[shootedCell[0] + 1][shootedCell[1]].equals(DECK)) {
                    wonded = true;
                }
            }
            if (coordinate.getY() > 1) {
                if (bigGameField[shootedCell[0]][shootedCell[1] - 1].equals(DECK)) {
                    wonded = true;
                }
            }
            if (coordinate.getY() < 10)
                if (bigGameField[shootedCell[0]][shootedCell[1] + 1].equals(DECK)) {
                    wonded = true;
                }

        if (wonded == true) {
            System.out.println("ранил!");
        } else {
            System.out.println("убит");
        }
    }
        else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("* ") || bigGameField[shootedCell[0]][shootedCell[1]].equals("~ ")) {
            System.out.println("промазал");
            bigGameField[shootedCell[0]][shootedCell[1]] = "x ";
            hit = true;
        } else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("x ") || bigGameField[shootedCell[0]][shootedCell[1]].equals("W ")) {
            //System.out.println("в эту клетку уже стреляли");
            hit = false;
        }
        return hit;
    }
}
