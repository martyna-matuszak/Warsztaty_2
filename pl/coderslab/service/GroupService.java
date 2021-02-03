package pl.coderslab.service;

import pl.coderslab.dao.GroupDao;
import pl.coderslab.models.Group;
import pl.coderslab.programs.AdminConsole;

import java.util.Scanner;

public class GroupService {

    static final String optionsCommand = "Wybierz jedną z opcji:\n" +
            "add – dodanie grupy,\n" +
            "edit – edycja grupy,\n" +
            "delete – usunięcie grupy,\n" +
            "quit – zakończenie programu.";

    public static void groupsAdministration (){

        GroupDao dao = new GroupDao();
        printAllGroups();

        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("quit")){
            String command = scanner.nextLine();
            switch (command) {
                case "add" -> addGroup(scanner, dao);
                case "edit" -> editGroup(scanner, dao);
                case "delete" -> deleteGroup(scanner, dao);
                default -> System.out.println(optionsCommand);
            }
        }System.out.println(AdminConsole.optionsCommand);
    }

    static void addGroup(Scanner scanner, GroupDao dao){
        Group group = new Group();
        System.out.println("Podaj nazwę nowej grupy:");
        group.setName(scanner.nextLine());
        group = dao.create(group);
        System.out.println("Dodano grupę    " + group);
    }

    static void editGroup(Scanner scanner, GroupDao dao){
        System.out.println("Podaj id grupy, którą chcesz edytować:");
        int groupId = getProperGroup(scanner);
        Group group = dao.read(groupId);
        System.out.println("Czy chcesz zmienić nazwę grupy?");
        if (scanner.nextLine().equalsIgnoreCase("TAK")){
            System.out.println("Podaj nową nazwę grupy:");
            group.setName(scanner.nextLine());
        }
        dao.update(group);
        System.out.println("Zmieniono dane grupy o id " + groupId);
    }

    static void deleteGroup(Scanner scanner, GroupDao dao){
        System.out.println("Podaj id grupy, którą chcesz usunąć:");
        int groupId = getProperGroup(scanner);
        System.out.println("Czy na pewno chcesz usunąć grupę o id " + groupId + "?");
        if (scanner.nextLine().equalsIgnoreCase("TAK")){
            dao.delete(groupId);
            System.out.println("Usunięto grupę o id " + groupId);
        } else {
            System.out.println("Grupa nie została usunięta");
        }
    }

    static Group[] getAllGroups(){
        GroupDao dao = new GroupDao();
        return dao.findAll();
    }

    static void printAllGroups(){
        Group[] allGroups = getAllGroups();
        for (Group group: allGroups) {
            System.out.println(group.toString());
        }
    }

    static boolean checkIfSelectedGroupExists(int id){
        Group[] allGroups = getAllGroups();
        boolean groupExists = false;
        for(Group group: allGroups){
            if (id == group.getId()) {
                groupExists = true;
                break;
            }
        }
        return groupExists;
    }

    static int getProperGroup(Scanner scanner){
        int id;
        boolean correctGroup = false;
        do{
            id = scanner.nextInt();
            scanner.nextLine();
            if(checkIfSelectedGroupExists(id)){
                correctGroup = true;
            } else {
                System.out.println("Grupa o wybranym id nie istnieje.");
                printAllGroups();
            }
        } while (!correctGroup);
        return id;
    }
}
