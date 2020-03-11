package pl.coderslab.programs;

import pl.coderslab.dao.DBUtil;
import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class UserInterface {

    private static final String FIND_SOLVED_QUERY =
            "SELECT * FROM exercises e LEFT JOIN solutions s on e.id = s.exercise_id WHERE user_id = ?";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz swój identyfikator użytkownika:");
        int userId = scanner.nextInt();
        scanner.nextLine();
        userOptions(userId);
    }

    public static void userOptions (int userId){
        Scanner scanner = new Scanner(System.in);
        String optionsCommand = "Wybierz jedną z opcji:\n" +
                "add – dodawanie rozwiązania,\n" +
                "view – przeglądanie swoich rozwiązań\n" +
                "quit – zakończenie pogramu.";
        System.out.println(optionsCommand);


        while (!scanner.hasNext("quit")){
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("add")){
                Exercise[] allUnsolved = findAllUnsolved(userId);
                for (Exercise exercise: allUnsolved){
                    System.out.println(exercise.toString());
                }
                System.out.println("Podaj numer id zadania, do którego chcesz dodać rozwiązanie:");
                int exerciseId = scanner.nextInt();
                scanner.nextLine();
                boolean isItUnsolved = false;
                for (Exercise exercise: allUnsolved){
                    if (exercise.getId()==exerciseId){
                        isItUnsolved = true;
                    }
                }
                if (isItUnsolved==true){
                    Solution solution = new Solution();
                    SolutionDao dao = new SolutionDao();
                    solution.setUserId(userId);
                    solution.setUpdated(LocalDateTime.now().toString());
                    solution.setExerciseId(exerciseId);
                    System.out.println("Podaj opis zadania:");
                    solution.setDescription(scanner.nextLine());
                    solution = dao.create(solution);
                    System.out.println("Dodano rozwiązanie");
                } else {
                    System.out.println("Podano błędne id!!!");
                }
            } else if (command.equalsIgnoreCase("view")){

                SolutionDao dao = new SolutionDao();
                Solution[] allSolutions = dao.findAllByUserId(userId);
                for(Solution solution: allSolutions){
                    System.out.println(solution.toString());
                }

            } else {
                System.out.println(optionsCommand);
            }
        }
    }

    /*
    Nie miałam pomysłu, na takiego selecta który pokaże mi to czego szukam, czyli zadania, których dany
    użytkownik nie rozwiązał, więc znalazłam takiego który robił coś odwrotnego i resztę zrobiłam w funkcji,
    czy był select który ułatwiłby mi tu sprawę? Bo szczerze mówiąc nie umiałam na to wpaść.
     */

    public static Exercise[] findAllUnsolved (int userId){
        try (Connection conn = DBUtil.connect()) {
            Exercise[] exercises = new Exercise[0];
            ExerciseDao dao = new ExerciseDao();
            PreparedStatement statement = conn.prepareStatement(FIND_SOLVED_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            int[] solvedExercisesId = new int[0];
            while (resultSet.next()) {
                solvedExercisesId = Arrays.copyOf(solvedExercisesId, solvedExercisesId.length + 1);
                solvedExercisesId[solvedExercisesId.length-1] = resultSet.getInt("e.id");
            }
            Exercise[] allExercises = dao.findAll();
            for( Exercise exercise : allExercises){
                int exerciseId = exercise.getId();
                boolean isItSolved = false;
                for (int i = 0; i < solvedExercisesId.length ; i++) {
                    if (solvedExercisesId[i]==exerciseId){
                        isItSolved = true;
                    }
                }
                if (isItSolved==false){
                    exercises = Arrays.copyOf(exercises, exercises.length + 1);
                    exercises[exercises.length-1] = exercise;
                }
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
}
