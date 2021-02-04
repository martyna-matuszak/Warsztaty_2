package pl.coderslab.service;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.models.Exercise;
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

    static void printSolutions(Solution[] solutions){
        for (Solution solution: solutions) {
            System.out.println(solution.toString());
        }
    }

    static void printAllSolutions(){
        Solution[] allSolutions = getAllSolutions();
        printSolutions(allSolutions);
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

    public static void addSolutionForUser(Scanner scanner, int userId){
        SolutionDao solutionDao = new SolutionDao();
        ExerciseDao exerciseDao = new ExerciseDao();
        Solution[] usersUnsolvedExercises = SolutionService.getAllUnsolvedForUser(userId);

        if (usersUnsolvedExercises.length == 0){
            System.out.println("Użytkownik nie ma zadań do rozwiązania");
        } else {
            SolutionService.printSolutions(usersUnsolvedExercises);
            System.out.println("Podaj numer id zadania, do którego chcesz dodać rozwiązanie:");
            int solutionId = SolutionService.getProperSolutionFromRange(scanner, usersUnsolvedExercises);
            Solution solution = solutionDao.read(solutionId);
            Exercise exercise = exerciseDao.read(solution.getExerciseId());
            System.out.println(exercise.toString());
            solution.setUpdated(LocalDateTime.now().toString());
            System.out.println("Podaj opis rozwiązania:");
            solution.setDescription(scanner.nextLine());
            solutionDao.update(solution);
            System.out.println("Dodano rozwiązanie");
        }

    }

    public static void viewSolutionsForUser(int userId){
        SolutionService.printSolutions(SolutionService.getUsersSolutions(userId));
    }

    static Solution[] getUsersSolutions (int id){
        Solution[] allSolutions = getAllSolutions();
        List<Solution> usersSolutionsList = new ArrayList<>();
        for (Solution solution: allSolutions){
            if(id == solution.getUserId()){
                usersSolutionsList.add(solution);
            }
        }
        return changeSolutionListIntoTab(usersSolutionsList);
    }

    static Solution[] changeSolutionListIntoTab(List<Solution> solutionList){
        Solution[] solutionTab = new Solution[solutionList.size()];
        int index = 0;
        for(Solution solutionListItem : solutionList){
            solutionTab[index] = solutionListItem;
            index++;
        }
        return solutionTab;
    }

    static void viewUsersSolutions(Scanner scanner){
        UserService.printAllUsers();
        System.out.println("Podaj id użytkownika, którego zadania chcesz zobaczyć:");
//        printUsersSolutions(UserService.getProperUser(scanner));
        printSolutions(getUsersSolutions(UserService.getProperUser(scanner)));
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

    static boolean checkIfSelectedSolutionIsInRange(int id, Solution[] rangeOfSolutions){
        boolean solutionExists = false;
        for(Solution solution: rangeOfSolutions){
            if (id == solution.getId()) {
                solutionExists = true;
                break;
            }
        }
        return solutionExists;
    }

    static int getProperSolutionFromRange(Scanner scanner, Solution[] rangeOfSolutions){
        int id;
        boolean correctSolution = false;
        do{
            id = scanner.nextInt();
            scanner.nextLine();
            if(checkIfSelectedSolutionIsInRange(id, rangeOfSolutions)){
                correctSolution = true;
            } else {
                System.out.println("Rozwiązanie dla wybranego id nie istnieje.");
                printSolutions(rangeOfSolutions);
            }
        } while (!correctSolution);
        return id;
    }

    static int getProperSolution(Scanner scanner){
        return getProperSolutionFromRange(scanner, getAllSolutions());
    }

    static Solution[] getAllUnsolvedForUser (int userId){
        Solution[] usersSolutions = getUsersSolutions(userId);
        List<Solution> allUnsolvedForUser = new ArrayList<>();
        for(Solution solution: usersSolutions){
            if(solution.getUpdated() == null){
                allUnsolvedForUser.add(solution);
            }
        }
        return changeSolutionListIntoTab(allUnsolvedForUser);
    }

}
