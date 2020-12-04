package com.company;
import java.io.*;
import java.util.*;

public class Main {

    static int step = 0;

    private static int getN() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter length of n: ");

        String n = scanner.nextLine();  // Read user input
        return Integer.parseInt(n);
    }

    private static void display(String state, String input, Stack<Character> stack, String rule) {

        // Setup unread inputs.
        String unreadInput = input.substring(step);

        if(!stack.isEmpty())
            System.out.println(step + "\t\t\t" + state + "\t\t\t" + unreadInput + "\t\t\t\t" + stack.peek() + "\t\t" + rule);
        else
            System.out.println(step + "\t\t\t" + state + "\t\t\t" + unreadInput + "\t\t\t\t" + "e" + "\t\t" + rule);

        step++; // Increments each time display is called.
    }

    public static void main(String[] args) {
        // Setup. 
        Stack<Character> stack = new Stack<>();
        String[] inputs = {"$", // n = 0
                            "ab$", // n = 1
                            "aabb$", // n = 2
                            "aaabbb$", // n = 3
                            "aaaabbbb$", // n = 4
                            "aaaaabbbbb$", // n = 5
                            "aaaaaabbbbbb$", // n = 6
                            "aaaaaaabbbbbbb$", // n = 7
                            "aaaaaaaabbbbbbbb$", // n = 8
                            "aaaaaaaaabbbbbbbbb$", // n = 9
                            "aaaaaaaaaabbbbbbbbbb$"}; // n = 10
        int n = getN(); // Determine which input to use.
        int i = 0; // Iterator.
        boolean recentlyPopped = false; // Flag.

        // Headers for Table.
        System.out.println("Step\t\tState\t\tUnread Input\t\tStack\t\tΔ Rule");
        display("q0,", inputs[n], stack, "");

	    do {
	        char currentChar = inputs[n].charAt(i);

            // PDA reads an 'a'.
            if (currentChar == 'a') {
                // First time.
                if(stack.empty())
                    display("q0,", inputs[n], stack, "[1] (q0, a, Z0) -> (q0, aZ0)");
                // Subsequent runs.
                else
                    display("q0,", inputs[n], stack, "[2] (q0, a, a) -> (q0, aa)");
                stack.push(currentChar);
            }

            // PDA reads a 'b'.
            else if (currentChar == 'b') {

                // First time encountering b.
                if(!recentlyPopped) {
                    stack.pop();
                    recentlyPopped = true;
                    display("q0,", inputs[n], stack, "[3] (q0, b, a) -> (q1, e)");
                }

                // Subsequent b's.
                else {
                    stack.pop();
                    display("q1,", inputs[n], stack, "[4] (q1, b, a) -> (q1, e)");
                }
            }

        i++; // Increment iterator.
        } while(!stack.empty());

	    // Last input should be $
        display("q1,", inputs[n], stack, "[5] (q1, $, e) -> (qf, e)");
    }
}
