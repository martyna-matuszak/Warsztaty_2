package pl.coderslab.programs;

import pl.coderslab.service.SolutionService;
import pl.coderslab.service.UserService;

import java.util.Scanner;

public class UserConsole {

    static final  String optionsCommand = "Wybierz jedną z opcji:\n" + "add – dodawanie rozwiązania,\n" + "view – przeglądanie swoich rozwiązań\n" + "quit – zakończenie pogramu.";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Wpisz swój identyfikator użytkownika:");
        int userId = UserService.getProperUser(scanner);
        boolean correctPassword = UserService.userAuthentification(scanner,userId);

        if (!correctPassword){
            System.out.println("Wprowadzono 3 razy niepoprawne hasło, brak dostępu!");
        } else {
            System.out.println(optionsCommand);

            while (!scanner.hasNext("quit")){
                switch (scanner.nextLine()) {
                    case "add" -> SolutionService.addSolutionForUser(scanner, userId);
                    case "view" -> SolutionService.viewSolutionsForUser(userId);
                    default -> System.out.println(optionsCommand);
                }
            }
        }
    }



}
