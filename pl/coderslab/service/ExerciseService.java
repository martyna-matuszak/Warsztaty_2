package pl.coderslab.service;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.models.Exercise;
import pl.coderslab.programs.AdminConsole;

import java.util.Scanner;

public class ExerciseService {

    static final String optionsCommand = "Wybierz jedną z opcji:\n" +
            "add – dodanie zadania,\n" +
            "edit – edycja zadania,\n" +
            "delete – usunięcia zadania,\n" +
            "quit – zakończenie programu.";

    public static void exercisesAdministration (){

        ExerciseDao dao = new ExerciseDao();
        printAllExercises();

        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("quit")){
            String command = scanner.nextLine();
            switch (command) {
                case "add" -> addExercise(scanner, dao);
                case "edit" -> editExercise(scanner, dao);
                case "delete" -> deleteExercise(scanner, dao);
                default -> System.out.println(optionsCommand);
            }
        } System.out.println(AdminConsole.optionsCommand);
    }

    static Exercise[] getAllExercises(){
        ExerciseDao dao = new ExerciseDao();
        return dao.findAll();
    }

    static void printAllExercises(){
        Exercise[] allExercises = getAllExercises();
        for (Exercise exercise: allExercises) {
            System.out.println(exercise.toString());
        }
    }

    static void addExercise(Scanner scanner, ExerciseDao dao){
        Exercise exercise = new Exercise();
        System.out.println("Podaj nazwę nowego zadania:");
        exercise.setTitle(scanner.nextLine());
        System.out.println("Podaj opis nowego zadania:");
        exercise.setDescription(scanner.nextLine());
        exercise = dao.create(exercise);
        System.out.println("Dodano zadanie   " + exercise);
    }

    static void editExercise(Scanner scanner, ExerciseDao dao){
        System.out.println("Podaj id zadania, które chcesz edytować:");
        int exerciseId = getProperExercise(scanner);
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
    }

    static void deleteExercise(Scanner scanner, ExerciseDao dao){
        System.out.println("Podaj id zadania, które chcesz usunąć:");
        int exerciseId = getProperExercise(scanner);
        System.out.println("Czy na pewno chcesz usunąć zadanie o id " + exerciseId + "?");
        if (scanner.nextLine().equalsIgnoreCase("TAK")){
            dao.delete(exerciseId);
            System.out.println("Usunięto zadanie o id " + exerciseId);
        } else {
            System.out.println("Zadanie nie zostało usunięte");
        }
    }

    static boolean checkIfSelectedExerciseExists(int id){
        Exercise[] allExercises = getAllExercises();
        boolean exerciseExists = false;
        for(Exercise exercise: allExercises){
            if (id == exercise.getId()) {
                exerciseExists = true;
                break;
            }
        }
        return exerciseExists;
    }

    static int getProperExercise(Scanner scanner){
        int id;
        boolean correctExercise = false;
        do{
            id = scanner.nextInt();
            scanner.nextLine();
            if(checkIfSelectedExerciseExists(id)){
                correctExercise = true;
            } else {
                System.out.println("Zadanie o wybranym id nie istnieje.");
                printAllExercises();
            }
        } while (!correctExercise);
        return id;
    }
}
