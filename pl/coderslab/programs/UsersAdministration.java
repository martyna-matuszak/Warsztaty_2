package pl.coderslab.programs;

import pl.coderslab.dao.UserDao;
import pl.coderslab.models.User;

import java.util.Scanner;

public class UsersAdministration {
    public static void main(String[] args) {
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

        scanner.close();
    }
}