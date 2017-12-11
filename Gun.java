

public class Gun extends Ship {
    String DECK = "0 ";
    private static int counter = 0;
    private static final String SHOOTED_CELL= "W ";
    private static final String STAR= "* ";
    protected void shoot(Field field1, Field field2) {

        if (counter % 2 == 0) {
            System.out.println("ход игрока 2, стреляем в поле 1");
            while (!isShotFinished(field1, Ship.generateCoordinate())) ;
            counter++;
        } else {
            humanShot(field2);
            /*System.out.println("ход игрока 1, стреляем в поле 2");
            while (!isShotFinished(field2, Ship.generateCoordinate())) ;
            counter++;*/
        }
        field1.printTwoFielsdBeside(field1, field2);
    }


    protected boolean isShotFinished(Field field, Ship.Point coordinate) {
        boolean hit = false;
        boolean wonded = false;
        boolean shipAreDead = false;
        int[] shootedCell = {coordinate.getX(), coordinate.getY()};
        String[][] bigGameField = field.getBigGameField();
        if (bigGameField[shootedCell[0]][shootedCell[1]].equals(DECK)) {
            field.seeYourCoordinateCorrect(coordinate);
            bigGameField[shootedCell[0]][shootedCell[1]] = SHOOTED_CELL;
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
                shipAreDead = true;
            }
        } else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("* ") || bigGameField[shootedCell[0]][shootedCell[1]].equals("~ ")) {
            field.seeYourCoordinateCorrect(coordinate);
            System.out.println("промазал");
            bigGameField[shootedCell[0]][shootedCell[1]] = "x ";
            hit = true;
        } else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("x ") || bigGameField[shootedCell[0]][shootedCell[1]].equals("W ")) {
            //System.out.println("в эту клетку уже стреляли");
            hit = false;
        }
        if (shipAreDead){
            createDeadFieldAroundShip(field.getBigGameField(),field);
        }
        return hit;
    }

    private void createDeadFieldAroundShip(String[][] bigGameField, Field field) {

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
    protected void humanShot(Field computersField){

        System.out.println("Человек, введи координату в формате ЛИТЕРАЦИФРА, например D2");
        while (!isShotFinished(computersField, Ship.getCoordinateFromHuman())) ;
        counter++;

    }
}