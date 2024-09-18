package com.zxw.utils;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class SQLLogParser {

    // Pattern to match SQL queries
    private static final Pattern SQL_PATTERN = Pattern.compile(
        "(SELECT .*? FROM .*? WHERE .*?);|"
        + "(INSERT INTO .*? VALUES .*?);|"
        + "(UPDATE .*? SET .*? WHERE .*?);|"
        + "(DELETE FROM .*? WHERE .*?);", 
        Pattern.DOTALL
    );

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java SQLLogParser <path to log file>");
            return;
        }

        String logFilePath = "C:\\Users\\youyu\\Desktop\\slow_queries.log";
        Set<String> uniqueSqlQueries = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            StringBuilder sqlQuery = new StringBuilder();
            boolean capturing = false;

            while ((line = br.readLine()) != null) {
                // Check if the line starts with an SQL keyword and start capturing
                if (line.matches("^(SELECT|INSERT|UPDATE|DELETE).*")) {
                    capturing = true;
                }

                // If capturing, append the line to sqlQuery
                if (capturing) {
                    sqlQuery.append(line).append(" ");
                }

                // Check if the line ends with a semicolon and stop capturing
                if (capturing && line.endsWith(";")) {
                    Matcher matcher = SQL_PATTERN.matcher(sqlQuery.toString());
                    if (matcher.find()) {
                        uniqueSqlQueries.add(matcher.group(0).trim());
                    }
                    capturing = false;
                    sqlQuery.setLength(0); // Clear the StringBuilder
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print unique SQL queries
        for (String sql : uniqueSqlQueries) {
            System.out.println(sql);
        }
    }
}
