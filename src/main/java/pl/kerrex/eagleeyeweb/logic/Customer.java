package pl.kerrex.eagleeyeweb.logic;

/**
 * Created by tomek on 26.01.16.
 */
public class Customer {
    private String name;
    private String REGON;
    private long id;

    public Customer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(long id, String name, String REGON) {
        this.name = name;
        this.REGON = REGON;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getREGON() {
        return REGON;
    }

    public void setREGON(String REGON) {
        this.REGON = REGON;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
