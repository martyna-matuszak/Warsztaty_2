package pl.coderslab.models;

public class Solution{

    private int id;
    private String created;
    private String updated;
    /*
    Z braku lepszego typu - string
    Datetime format: YYYY-MM-DD HH:MM:SS
     */
    private String description;
    private int exerciseId;
    private int userId;

    public Solution (String created, String updated, String description, int exerciseId, int userId){
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exerciseId = exerciseId;
        this.userId = userId;
    }

    public Solution() {}

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getUpdated() {
        return updated;
    }

    public String getCreated() {
        return created;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + ", created: " + created
                + ", updated: " + updated
                + ", description: " + description
                + ", exercise id: " + exerciseId
                + ", user_id: " + userId;
    }
}
