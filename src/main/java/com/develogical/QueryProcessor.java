package com.develogical;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.util.stream.Collectors;

public class QueryProcessor {

    public String process(String query) {
        if (query.toLowerCase().contains("shakespeare")) {
            return "William Shakespeare (26 April 1564 - 23 April 1616) was an " +
                    "English poet, playwright, and actor, widely regarded as the greatest " +
                    "writer in the English language and the world's pre-eminent dramatist.";
        }

        if (query.contains("your name")) {
            return "jjsk";
        }

        if (query.toLowerCase().contains("plus")) {
            Pattern pattern = Pattern.compile("what is (\\d+) plus (\\d+)\\?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                return String.valueOf(x + y);
            }
        }
        if (query.toLowerCase().contains("minus")) {
            Pattern pattern = Pattern.compile("what is (\\d+) minus (\\d+)\\?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                return String.valueOf(x - y);
            }
        }

        if (query.toLowerCase().contains("which of the following numbers is the largest")) {
            Pattern pattern = Pattern.compile("which of the following numbers is the largest: (.+)\\?",
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                String[] numbersStr = matcher.group(1).split(",");
                ArrayList<Integer> numbers = new ArrayList<>();
                for (String num : numbersStr) {
                    numbers.add(Integer.parseInt(num.trim()));
                }
                return String.valueOf(Collections.max(numbers));
            }
        }
        if (query.toLowerCase().startsWith("which of the following numbers is both a square and a cube")) {
            // Extract numbers from the query
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(query);
            ArrayList<Integer> numbers = new ArrayList<>();
            while (matcher.find()) {
                numbers.add(Integer.parseInt(matcher.group()));
            }
            for (int number : numbers) {
                double sixthRoot = Math.pow(number, 1.0 / 6.0);
                if (Math.abs(sixthRoot - Math.round(sixthRoot)) < 1e-9) { // Check if it's an integer
                    return String.valueOf(number); // Return the first valid number as a string
                }
            }
            return "None";
        }

        if (query.toLowerCase().startsWith("what is") && query.contains("multiplied by")) {
            Pattern pattern = Pattern.compile("(\\d+)\\s*multiplied by\\s*(\\d+)");
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                int num1 = Integer.parseInt(matcher.group(1));
                int num2 = Integer.parseInt(matcher.group(2));
                return String.valueOf(num1 * num2);
            }
        }
        if (query.toLowerCase().startsWith("which of the following numbers are primes")) {
            // Extract numbers and find the maximum
            List<Integer> numbers = new ArrayList<>();
            Matcher matcher = Pattern.compile("\\d+").matcher(query);
            int max = 0;
            while (matcher.find()) {
                int num = Integer.parseInt(matcher.group());
                numbers.add(num);
                max = Math.max(max, num);
            }
            // Sieve of Eratosthenes
            boolean[] isPrime = new boolean[max + 1];
            Arrays.fill(isPrime, 2, max + 1, true);
            for (int i = 2; i * i <= max; i++) {
                if (isPrime[i]) {
                    for (int j = i * i; j <= max; j += i)
                        isPrime[j] = false;
                }
            }
            // Collect primes and return as comma-separated string
            return numbers.stream()
                    .filter(num -> isPrime[num])
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
        }

        return "";
    }

}