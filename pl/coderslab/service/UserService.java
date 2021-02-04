package pl.coderslab.service;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;
import pl.coderslab.programs.AdminConsole;

import java.time.LocalDateTime;
import java.util.Scanner;


public class UserService {

    static final String optionsCommand = "Wybierz jedną z opcji:\n" + "add – dodanie użytkownika,\n" + "edit – edycja użytkownika,\n" + "delete – usunięcie użytkownika,\n" + "quit – wróć do menu głównego.";

    public static void usersAdministration (){
        UserDao dao = new UserDao();
        printAllUsers();

        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNext("quit")){
            switch (scanner.nextLine()) {
                case "add" -> addUser(scanner, dao);
                case "edit" -> editUser(scanner, dao);
                case "delete" -> deleteUser(scanner, dao);
                default -> System.out.println(optionsCommand);
            }
        } System.out.println(AdminConsole.optionsCommand);
    }

    public static boolean userAuthentification(Scanner scanner, int userId){
        UserDao dao = new UserDao();
        User user = dao.read(userId);
        String password = user.getPassword();

        boolean correctPassword = false;

        System.out.println("Podaj hasło");
        for(int i=0; i<3; i++){
            if(BCrypt.checkpw(scanner.nextLine(), password)){
                System.out.println("Hasło jest poprawne");
                correctPassword = true;
                break;
            }else {
                System.out.println("Hasło jest niepoprawne. Pozostało prób: " + (2-i));
            }
        }
        return correctPassword;
    }

    static void addUser(Scanner scanner, UserDao dao){
        User user = new User();
        System.out.println("Podaj adres email nowego użytkownika:");
        user.setEmail(getProperEmail(scanner));
        System.out.println("Podaj nazwę nowego użytkownika:");
        user.setUsername(scanner.nextLine());
        System.out.println("Podaj hasło nowego użytkownika:");
        user.setPassword(scanner.nextLine());
        System.out.println("Podaj id grupy, do której będzie należał użytkownik:");
        user.setUserGroupId(GroupService.getProperGroup(scanner));
        user = dao.create(user);
        System.out.println("Dodano użytkownika    " + user);
    }

    static void editUser(Scanner scanner, UserDao dao){
        System.out.println("Podaj id użytkownika, którego chcesz edytować:");
        int userId = getProperUser(scanner);
        User user = dao.read(userId);
        System.out.println("Czy chcesz zmienić adres email użytkownika?");
        if (scanner.nextLine().equalsIgnoreCase("TAK")){
            System.out.println("Podaj nowy adres email użytkownika:");
            user.setEmail(getProperEmail(scanner));
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
            user.setUserGroupId(GroupService.getProperGroup(scanner));
            scanner.nextLine();
        }
        dao.update(user);
        System.out.println("Zmieniono dane użytkownika o id " + userId);
    }

    static void deleteUser(Scanner scanner, UserDao dao){
        System.out.println("Podaj id użytkownika, którego chcesz usunąć:");
        int userId = getProperUser(scanner);
        System.out.println("Czy na pewno chcesz usunąć użytkownika o id " + userId + "?");
        if (scanner.nextLine().equalsIgnoreCase("TAK")){
            dao.delete(userId);
            System.out.println("Usunięto użytkownika o id " + userId);
        } else {
            System.out.println("Użytkownik nie został usunięty");
        }
    }

    static User[] getAllUsers(){
        UserDao dao = new UserDao();
        return dao.findAll();
    }

    static void printAllUsers(){
        User[] allUsers = getAllUsers();
        for (User user: allUsers) {
            System.out.println(user.toString());
        }
    }

    static boolean checkIfSelectedUserExists(int id){
        User[] allUsers = getAllUsers();
        boolean userExists = false;
        for(User user:allUsers){
            if (id == user.getId()) {
                userExists = true;
                break;
            }
        }
        return userExists;
    }

    public static int getProperUser(Scanner scanner){
        int id;
        boolean correctUser = false;
        do{
            id = scanner.nextInt();
            scanner.nextLine();
            if(checkIfSelectedUserExists(id)){
                correctUser = true;
            } else {
                System.out.println("Użytkownik o wybranym id nie istnieje.");
                printAllUsers();
            }
        } while (!correctUser);
        return id;
    }

    static boolean checkIfInsertedEmailIsUnique(String email){
        User[] allUsers = getAllUsers();
        boolean emailUnique = true;
        for(User user: allUsers){
            if(email.equals(user.getEmail())){
                emailUnique = false;
                break;
            }
        }
        return emailUnique;
    }

    static String  getProperEmail(Scanner scanner){
        String email;
        boolean uniqueEmail = false;
        do{
            email = scanner.nextLine();
            if(checkIfInsertedEmailIsUnique(email)){
                uniqueEmail = true;
            } else {
                System.out.println("Podany adres email już istnieje w bazie. Podaj inny adres email.");
            }
        } while (!uniqueEmail);
        return email;
    }
}
