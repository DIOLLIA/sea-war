/**
 * Created by Andre on 19.10.2017.
 */
public class Gamer {
    private String name;
    private Field field;

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

    Gamer(TypeOfPlayer Computer) {
    }

    enum TypeOfPlayer {Human, Computer}
}
