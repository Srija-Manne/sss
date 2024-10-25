package workload.masterdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class InvigilatorController {

    @GetMapping("/uploadForm")
    public String showUploadForm() {
        return "uploadForm"; // This should map to Thymeleaf template "uploadForm.html"
    }

    @PostMapping("/uploadForm")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        String jdbcURL = "jdbc:mysql://localhost:3306/minidb";  // Update with your DB details
        String username = "root";  // Update with your username
        String password = "Srija@538";  // Update with your password

        List<String> allocationResults = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                br.readLine(); // Skip header row

                List<String> dates = new ArrayList<>();
                List<String> slots = new ArrayList<>();
                List<Integer> numbers = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    dates.add(values[0]);
                    slots.add(values[1]);
                    numbers.add(Integer.parseInt(values[2]));
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                String[] days = new String[dates.size()];

                for (int i = 0; i < dates.size(); i++) {
                    LocalDate date = LocalDate.parse(dates.get(i), formatter);
                    DayOfWeek dayOfWeek = date.getDayOfWeek();
                    days[i] = dayOfWeek.name().toLowerCase(); // Convert to lowercase
                }

                List<String> columnNames = getColumnNames(connection, "csv_import_table");

                for (int i = 0; i < dates.size(); i++) {
                    String column = days[i] + "_" + slots.get(i).toLowerCase(); // Convert slot to lowercase
                    if (columnNames.contains(column)) {
                        allocateInvigilatorsForSlot(connection, allocationResults, column, numbers.get(i));
                    } else {
                        allocationResults.add("Column " + column + " does not exist. Skipping...");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            allocationResults.add("Database connection error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            allocationResults.add("File processing error: " + e.getMessage());
        }

        model.addAttribute("results", allocationResults);
        return "result"; // Thymeleaf template for displaying results
    }

    private List<String> getColumnNames(Connection connection, String tableName) throws SQLException {
        List<String> columnNames = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        while (resultSet.next()) {
            columnNames.add(resultSet.getString("COLUMN_NAME").toLowerCase()); // Ensure column names are lowercase
        }
        return columnNames;
    }

    private void allocateInvigilatorsForSlot(Connection connection, List<String> allocationResults, String column, int requiredInvigilators) throws SQLException {
        // Changed the query to check faculty table for availability
        String query = "SELECT id, name FROM faculty WHERE " + column + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "Yes"); // Check if available
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                List<Faculty> availableFaculty = new ArrayList<>();
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");

                    int count = getAllocationCount(connection, id);
                    availableFaculty.add(new Faculty(id, name, count));
                }

                availableFaculty.sort(Comparator.comparingInt(f -> f.allocationCount));

                for (int j = 0; j < Math.min(requiredInvigilators, availableFaculty.size()); j++) {
                    Faculty selectedFaculty = availableFaculty.get(j);
                    allocationResults.add("Assigning " + selectedFaculty.name + " (ID: " + selectedFaculty.id + ")");
                    updateAllocationCount(connection, selectedFaculty.id);
                }
            }
        }
    }

    private int getAllocationCount(Connection connection, String facultyId) throws SQLException {
        String query = "SELECT count FROM freq WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, facultyId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                } else {
                    return 0;
                }
            }
        }
    }

    private void updateAllocationCount(Connection connection, String facultyId) throws SQLException {
        String query = "UPDATE freq SET count = count + 1 WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, facultyId);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                String insertQuery = "INSERT INTO freq (id, count) VALUES (?, 1)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, facultyId);
                    insertStatement.executeUpdate();
                }
            }
        }
    }

    static class Faculty {
        String id;
        String name;
        int allocationCount;

        Faculty(String id, String name, int allocationCount) {
            this.id = id;
            this.name = name;
            this.allocationCount = allocationCount;
        }
    }
}
