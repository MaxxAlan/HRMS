package hrms.utils;

import java.util.Scanner;

public class ConsoleUI {
    
    private static Scanner scanner = new Scanner(System.in);
    
    // High-compatibility characters (ASCII)
    private static final String TOP_LEFT = "+";
    private static final String TOP_RIGHT = "+";
    private static final String BOTTOM_LEFT = "+";
    private static final String BOTTOM_RIGHT = "+";
    private static final String HORIZONTAL = "-";
    private static final String VERTICAL = "|";

    public static void displayHeader(String title) {
        int width = 60;
        printLine(TOP_LEFT, TOP_RIGHT, width);
        System.out.println(VERTICAL + centerString(title, width) + VERTICAL);
        printLine(BOTTOM_LEFT, BOTTOM_RIGHT, width);
    }
    
    public static void displayError(String message) {
        System.out.println("\n  [!] ERROR: " + message);
    }
    
    public static void displaySuccess(String message) {
        System.out.println("\n  [*] SUCCESS: " + message);
    }
    
    public static String promptForNonEmptyString(String prompt) {
        System.out.print("  > " + prompt);
        return scanner.nextLine().trim();
    }
    
    public static void pause() {
        System.out.print("\n  -- Press Enter to continue --");
        scanner.nextLine();
    }

    public static boolean confirmAction(String message) {
        System.out.print("  ? " + message + " (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        return response.equals("Y") || response.equals("YES");
    }

    private static void printLine(String left, String right, int width) {
        System.out.print(left);
        for (int i = 0; i < width; i++) System.out.print(HORIZONTAL);
        System.out.println(right);
    }

    private static String centerString(String s, int width) {
        if (s.length() > width) s = s.substring(0, width - 3) + "...";
        int pad = (width - s.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pad; i++) sb.append(" ");
        sb.append(s);
        while (sb.length() < width) sb.append(" ");
        return sb.toString();
    }
}