package pl.coderslab.programs;

import pl.coderslab.service.ExerciseService;
import pl.coderslab.service.GroupService;
import pl.coderslab.service.SolutionService;
import pl.coderslab.service.UserService;

import java.util.Scanner;

public class AdminConsole {

    public static final String optionsCommand = "Wybierz dane, które chcesz edytować:\n" +
            "users - dane użytkowników,\n" +
            "groups - grupy,\n" +
            "exercises - informacje o zadaniach,\n" +
            "solutions - rozwiązania zadań,\n" +
            "exit - zakończenie pogramu.";

    public static void main(String[] args) {
        System.out.println("PANEL ADMINISTRATORA");
        System.out.println(optionsCommand);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("exit")){
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("users")){
                UserService.usersAdministration();
            } else if (command.equalsIgnoreCase("groups")){
                GroupService.groupsAdministration();
            } else if (command.equalsIgnoreCase("exercises")){
                ExerciseService.exercisesAdministration();
            } else if (command.equalsIgnoreCase("solutions")){
                SolutionService.solutionsAdministration();
            } else {
                System.out.println(optionsCommand);
            }
        }
        scanner.close();
    }



}
