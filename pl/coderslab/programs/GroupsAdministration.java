package pl.coderslab.programs;

import pl.coderslab.dao.GroupDao;
import pl.coderslab.models.Group;

import java.util.Scanner;

public class GroupsAdministration {
    public static void main(String[] args) {
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
        scanner.close();
    }
}
