package pl.coderslab.service;

import pl.coderslab.dao.SolutionDao;
import pl.coderslab.models.Solution;
import pl.coderslab.programs.AdminConsole;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolutionService {

    static final String optionsCommand = "Wybierz jedną z opcji:\n" +
            "add – przypisanie zadania do użytkownika,\n" +
            "edit - edytuj przypisanie zadania,\n" +
            "delete - usuń przypisanie zadania,\n" +
            "view – przeglądanie rozwiązań wybranego użytkownika,\n" +
            "view all - wyświetlenie wszystkich rozwiązań \n" +
            "quit – zakończenie programu.";

    public static void solutionsAdministration (){
        SolutionDao dao = new SolutionDao();


        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("quit")){
            switch (scanner.nextLine()) {
                case "add" -> addSolution(scanner, dao);
                case "edit" -> editSolution(scanner, dao);
                case "delete" -> deleteSolution(scanner, dao);
                case "view" -> viewUsersSolutions(scanner);
                case "view all" -> printAllSolutions();
                default -> System.out.println(optionsCommand);
            }
        } System.out.println(AdminConsole.optionsCommand);
    }

    static Solution[] getAllSolutions(){
        SolutionDao dao = new SolutionDao();
        return dao.findAll();
    }

    static void printAllSolutions(){
        Solution[] allSolutions = getAllSolutions();
        for (Solution solution: allSolutions) {
            System.out.println(solution.toString());
        }
    }

    static void addSolution(Scanner scanner, SolutionDao dao){
        Solution solution = new Solution();

        UserService.printAllUsers();
        System.out.println("Wybierz id użytkownika:");
        solution.setUserId(UserService.getProperUser(scanner));

        ExerciseService.printAllExercises();
        System.out.println("Wybierz id zadania:");
        solution.setExerciseId(ExerciseService.getProperExercise(scanner));

        solution.setCreated(LocalDateTime.now().toString());

        dao.create(solution);
        System.out.println("Zadanie zostało przypisane do użytkownika");
    }

    static void printUsersSolutions(int id){
        Solution[] allSolutions = getAllSolutions();
        List<Solution> usersSolutions = new ArrayList<>();
        for (Solution solution: allSolutions){
            if(id == solution.getUserId()){
                usersSolutions.add(solution);
            }
        }
        for (Solution solution: usersSolutions){
            System.out.println(solution.toString());
        }
    }

    static void viewUsersSolutions(Scanner scanner){
        UserService.printAllUsers();
        System.out.println("Podaj id użytkownika, którego zadania chcesz zobaczyć:");
        printUsersSolutions(UserService.getProperUser(scanner));
    }

    static void editSolution(Scanner scanner, SolutionDao dao){
        System.out.println("Podaj id rozwiązania, które chcesz edytować:");
        int solutionId = getProperSolution(scanner);
        Solution solution = dao.read(solutionId);
        if(solution.getUpdated()!=null){
            System.out.println("Zadanie zostało już rozwiązane przez użytkownika, nie możesz go edytować!");
        } else {
            System.out.println("Czy chcesz zmienić użytkownika, przypisanego do rozwiązania tego zadania?");
            if (scanner.nextLine().equalsIgnoreCase("TAK")){
                System.out.println("Podaj nowy id użytkownika:");
                solution.setUserId(UserService.getProperUser(scanner));
            }

            System.out.println("Czy chcesz zmienić zadanie, które zostało przypisane temu użytkownikowi?");
            if (scanner.nextLine().equalsIgnoreCase("TAK")){
                System.out.println("Podaj nowe id zadania:");
                solution.setExerciseId(ExerciseService.getProperExercise(scanner));
            }

            dao.update(solution);
            System.out.println("Zmieniono przypisanie rozwiązania zadania o id " + solutionId);
        }
    }

    static void deleteSolution(Scanner scanner, SolutionDao dao){
        System.out.println("Podaj id rozwiązania, które chcesz usunąć:");
        int solutionId = getProperSolution(scanner);
        Solution solution = dao.read(solutionId);
        if(solution.getUpdated()!=null){
            System.out.println("Zadanie zostało już rozwiązane przez użytkownika, nie możesz go usunąć!");
        } else {
            System.out.println("Czy na pewno chcesz usunąć rozwiązanie o id " + solutionId + "?");
            if (scanner.nextLine().equalsIgnoreCase("TAK")){
                dao.delete(solutionId);
                System.out.println("Usunięto rozwiązanie o id " + solutionId);
            } else {
                System.out.println("Rozwiązanie nie zostało usunięte");
            }
        }
    }

    static boolean checkIfSelectedSolutionExists(int id){
        Solution[] allSolutions = getAllSolutions();
        boolean solutionExists = false;
        for(Solution solution: allSolutions){
            if (id == solution.getId()) {
                solutionExists = true;
                break;
            }
        }
        return solutionExists;
    }

    static int getProperSolution(Scanner scanner){
        int id;
        boolean correctSolution = false;
        do{
            id = scanner.nextInt();
            scanner.nextLine();
            if(checkIfSelectedSolutionExists(id)){
                correctSolution = true;
            } else {
                System.out.println("Rozwiązanie dla wybranego id nie istnieje.");
                printAllSolutions();
            }
        } while (!correctSolution);
        return id;
    }

}
