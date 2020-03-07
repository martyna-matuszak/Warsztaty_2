package pl.coderslab.models;

public class Group {

    private int id;
    private String name;

    public Group (String name){
        this.name = name;
    }

    public Group () {}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", name: " + name;
    }
}
