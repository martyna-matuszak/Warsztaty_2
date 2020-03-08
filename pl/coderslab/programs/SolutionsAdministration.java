package pl.coderslab.programs;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;

import java.util.Scanner;


public class SolutionsAdministration {
    public static void main(String[] args) {
        SolutionDao solutionDao = new SolutionDao();
        String optionsCommand = "Wybierz jedną z opcji:\n" +
                "add – przypisywanie zadań do użytkowników,\n" +
                "view – przeglądanie rozwiązań danego użytkownika,\n" +
                "quit – zakończenie programu.";

        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("quit")){
            String command = scanner.nextLine();
            if (command.equals("add")){
                Solution solution = new Solution();
                UserDao userDao = new UserDao();
                User[] allUsers = userDao.findAll();
                for (User user : allUsers){
                    System.out.println(user.toString());
                }
                System.out.println("Wybierz id użytkownika:");
                solution.setUserId(scanner.nextInt());
                scanner.nextLine();

                ExerciseDao exerciseDao = new ExerciseDao();
                Exercise[] allExercises = exerciseDao.findAll();
                for(Exercise exercise: allExercises){
                    System.out.println(exercise.toString());
                }
                System.out.println("Wybierz id zadania:");
                solution.setExerciseId(scanner.nextInt());
                scanner.nextLine();

                solutionDao.createSimplified(solution);
                System.out.println("Dodano rozwiązanie");

            } else if (command.equals("view")) {
                System.out.println("Podaj id użytkownika, którego zadania chcesz zobaczyć:");
                Solution[] usersSolutions = solutionDao.findAllByUserId(scanner.nextInt());
                scanner.nextLine();
                for (Solution solution: usersSolutions){
                    System.out.println(solution.toString());
                }
            } else {
                System.out.println(optionsCommand);
            }
        }
        scanner.close();
    }
}
