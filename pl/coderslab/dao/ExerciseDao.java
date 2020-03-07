package pl.coderslab.dao;

import pl.coderslab.models.Exercise;

import java.sql.*;
import java.util.Arrays;

public class ExerciseDao {
    private static final String CREATE_EXERCISE_QUERY =
            "INSERT INTO exercises(title, description) VALUES (?, ?)";
    private static final String READ_EXERCISE_QUERY =
            "SELECT * FROM exercises where id = ?";
    private static final String UPDATE_EXERCISE_QUERY =
            "UPDATE exercises SET title = ?, description = ? where id = ?";
    private static final String DELETE_EXERCISE_QUERY =
            "DELETE FROM exercises WHERE id = ?";
    private static final String FIND_ALL_EXERCISES_QUERY =
            "SELECT * FROM exercises";

    public Exercise create(Exercise exercise) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise read(int exerciseId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(READ_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setDescription(resultSet.getString("description"));
                exercise.setTitle(resultSet.getString("title"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Exercise exercise) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int exerciseId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Exercise[] addToArray(Exercise e, Exercise[] exercises) {
        Exercise[] tmp = Arrays.copyOf(exercises, exercises.length + 1);
        tmp[exercises.length] = e;
        return tmp;
    }

    public Exercise[] findAll() {
        try (Connection conn = DBUtil.connect()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }}
}
