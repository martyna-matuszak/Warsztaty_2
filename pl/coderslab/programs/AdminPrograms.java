package pl.coderslab.programs;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.GroupDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.models.Exercise;
import pl.coderslab.models.Group;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;

import java.time.LocalDateTime;
import java.util.Scanner;

/*
W poprzednim commicie były 4 pliki, które miały te funkcje w mainach, ale jednak doszłam do wniosku, że wygodniej
będzie mieć realny dostęp do tych funkcji i przy okazji pomyślałam, że zrobię taki ogólny, dość prosty "panel
administratora" w ramach zadania dodatkowego :)
Minus jest taki, że zrobiła się z tego wielka ściana tekstu, ale nie widziałam sensu dalej trzymać tego
w 4 różnych plikach.
 */

public class AdminPrograms {

    public static void main(String[] args) {
        System.out.println("PANEL ADMINISTRATORA");
        String optionsCommand = "Wybierz dane, które chcesz edytować:\n" +
                "users - dane użytkowników,\n" +
                "groups - grupy,\n" +
                "exercises - informacje o zadaniach,\n" +
                "solutions - rozwiązania zadań,\n" +
                "exit - zakończenie pogramu.";
        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("exit")){
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("users")){
                usersAdministration();
            } else if (command.equalsIgnoreCase("groups")){
                groupsAdministration();
            } else if (command.equalsIgnoreCase("exercises")){
                exercisesAdministration();
            } else if (command.equalsIgnoreCase("solutions")){
                solutionsAdministration();
            } else {
                System.out.println(optionsCommand);
            }
        }
        scanner.close();

    }


    public static void usersAdministration (){
        UserDao dao = new UserDao();
        User[] allUsers = dao.findAll();
        for (User user: allUsers) {
            System.out.println(user.toString());
        }
        String optionsCommand = "Wybierz jedną z opcji:\n" + "add – dodanie użytkownika,\n" + "edit – edycja użytkownika,\n" + "delete – usunięcie użytkownika,\n" + "quit – zakończenie programu.";
        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("quit")){
            String command = scanner.nextLine();
            if (command.equals("add")){
                User user = new User();
                System.out.println("Podaj adres email nowego użytkownika:");
                user.setEmail(scanner.nextLine());
                System.out.println("Podaj nazwę nowego użytkownika:");
                user.setUsername(scanner.nextLine());
                System.out.println("Podaj hasło nowego użytkownika:");
                user.setPassword(scanner.nextLine());
                System.out.println("Podaj id grupy, do której będzie należał użytkownik:");
                user.setUserGroupId(scanner.nextInt());
                scanner.nextLine();
                user = dao.create(user);
                System.out.println("Dodano użytkownika    " + user);
            } else if (command.equals("edit")) {
                System.out.println("Podaj id użytkownika, którego chcesz edytować:");
                int userId = scanner.nextInt();
                scanner.nextLine();
                User user = dao.read(userId);
                System.out.println("Czy chcesz zmienić adres email użytkownika?");
                if (scanner.nextLine().equalsIgnoreCase("TAK")){
                    System.out.println("Podaj nowy adres email użytkownika:");
                    user.setEmail(scanner.nextLine());
                }
                System.out.println("Czy chcesz zmienić nazwę użytkownika?");
                if (scanner.nextLine().equalsIgnoreCase("TAK")){
                    System.out.println("Podaj nową nazwę użytkownika:");
                    user.setUsername(scanner.nextLine());
                }
                System.out.println("Czy chcesz zmienić hasło?");
                if (scanner.nextLine().equalsIgnoreCase("TAK")){
                    System.out.println("Podaj nowe hasło użytkownika:");
                    user.setPassword(scanner.nextLine());
                }
                System.out.println("Czy chcesz zmienić grupę użytkownika?");
                if (scanner.nextLine().equalsIgnoreCase("TAK")){
                    System.out.println("Podaj nowy id grupy, do której będzie należał użytkownik:");
                    user.setUserGroupId(scanner.nextInt());
                    scanner.nextLine();
                }
                dao.update(user);
                System.out.println("Zmieniono dane użytkownika o id " + userId);

            } else if (command.equals("delete")) {
                System.out.println("Podaj id użytkownika, którego chcesz usunąć:");
                int userId = scanner.nextInt();
                scanner.nextLine();
                dao.delete(userId);
                System.out.println("Usunięto użytkownika o id " + userId);
            } else {
                System.out.println(optionsCommand);
            }
        }
    }

    public static void groupsAdministration (){

        GroupDao dao = new GroupDao();
        Group[] allGroups = dao.findAll();
        for (Group group: allGroups) {
            System.out.println(group.toString());
        }
        String optionsCommand = "Wybierz jedną z opcji:\n" +
                "add – dodanie grupy,\n" +
                "edit – edycja grupy,\n" +
                "delete – usunięcie grupy,\n" +
                "quit – zakończenie programu.";

        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("quit")){
            String command = scanner.nextLine();
            if (command.equals("add")){
                Group group = new Group();
                System.out.println("Podaj nazwę nowej grupy:");
                group.setName(scanner.nextLine());
                group = dao.create(group);
                System.out.println("Dodano grupę    " + group);

            } else if (command.equals("edit")) {
                System.out.println("Podaj id grupy, którą chcesz edytować:");
                int groupId = scanner.nextInt();
                scanner.nextLine();
                Group group = dao.read(groupId);
                System.out.println("Czy chcesz zmienić nazwę grupy?");
                if (scanner.nextLine().equalsIgnoreCase("TAK")){
                    System.out.println("Podaj nową nazwę grupy:");
                    group.setName(scanner.nextLine());
                }
                dao.update(group);
                System.out.println("Zmieniono dane grupy o id " + groupId);

            } else if (command.equals("delete")) {
                System.out.println("Podaj id grupy, którą chcesz usunąć:");
                int groupId = scanner.nextInt();
                scanner.nextLine();
                dao.delete(groupId);
                System.out.println("Usunięto grupę o id " + groupId);

            } else {
                System.out.println(optionsCommand);
            }
        }
    }

    public static void solutionsAdministration (){
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
                solution.setCreated(LocalDateTime.now().toString());

                solutionDao.create(solution);
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
    }

    public static void exercisesAdministration (){

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
    }

}
