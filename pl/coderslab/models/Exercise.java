package pl.coderslab.models;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise (String title, String description){
        this.title = title;
        this.description = description;
    }

    public Exercise () {}

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", title: " + title + ", description: " + description;
    }
}
