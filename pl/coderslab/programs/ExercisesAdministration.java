package pl.coderslab.programs;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.models.Exercise;

import java.util.Scanner;

public class ExercisesAdministration {
    public static void main(String[] args) {
        ExerciseDao dao = new ExerciseDao();
        Exercise[] allExercises = dao.findAll();
        for (Exercise exercise: allExercises) {
            System.out.println(exercise.toString());
        }
        String optionsCommand = "Wybierz jedną z opcji:\n" +
                "add – dodanie zadania,\n" +
                "edit – edycja zadania,\n" +
                "delete – usunięcia zadania,\n" +
                "quit – zakończenie programu.";

        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("quit")){
            String command = scanner.nextLine();
            if (command.equals("add")){
                Exercise exercise = new Exercise();
                System.out.println("Podaj nazwę nowego zadania:");
                exercise.setTitle(scanner.nextLine());
                System.out.println("Podaj opis nowego zadania:");
                exercise.setDescription(scanner.nextLine());
                exercise = dao.create(exercise);
                System.out.println("Dodano zadanie   " + exercise);

            } else if (command.equals("edit")) {
                System.out.println("Podaj id zadania, które chcesz edytować:");
                int exerciseId = scanner.nextInt();
                scanner.nextLine();
                Exercise exercise = dao.read(exerciseId);
                System.out.println("Czy chcesz zmienić nazwę zadania?");
                if (scanner.nextLine().equalsIgnoreCase("TAK")){
                    System.out.println("Podaj nową nazwę zadania:");
                    exercise.setTitle(scanner.nextLine());
                }
                System.out.println("Czy chcesz zmienić opis zadania?");
                if (scanner.nextLine().equalsIgnoreCase("TAK")){
                    System.out.println("Podaj nowy opis zadania:");
                    exercise.setDescription(scanner.nextLine());
                }
                dao.update(exercise);
                System.out.println("Zmieniono dane zadania o id " + exerciseId);

            } else if (command.equals("delete")) {
                System.out.println("Podaj id zadania, które chcesz usunąć:");
                int exerciseId = scanner.nextInt();
                scanner.nextLine();
                dao.delete(exerciseId);
                System.out.println("Usunięto zadanie o id " + exerciseId);
            } else {
                System.out.println(optionsCommand);
            }
        }

        scanner.close();
    }
}