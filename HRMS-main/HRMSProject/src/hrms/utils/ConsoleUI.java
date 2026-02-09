package hrms.utils;

import java.util.Scanner;

public class ConsoleUI {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void displayHeader(String title) {
        System.out.println("\n========================================");
        System.out.println("   " + title);
        System.out.println("========================================");
    }
    
    public static void displayError(String message) {
        System.out.println("❌ ERROR: " + message);
    }
    
    public static void displaySuccess(String message) {
        System.out.println("✅ SUCCESS: " + message);
    }
    
    public static String promptForNonEmptyString(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input; // Để trống cũng được để xử lý logic "Enter to skip"
    }
    
    public static void pause() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static boolean confirmAction(String message) {
        System.out.print(message + " (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        return response.equals("Y") || response.equals("YES");
    }
}
