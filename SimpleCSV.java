import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

class SimpleCSV {
    private String file;
    private ArrayList<String> columns = new ArrayList<>();
    private HashMap<String, Integer> columnIndices = new HashMap<>();
    private ArrayList<ArrayList<String>> lines = new ArrayList<>();

    public SimpleCSV(String file) {
        this.file = file;
    }

    public boolean matchesColumns(String matchColumns) {
        File f = new File(file);
        if (!f.exists() || !f.isFile()) {
            return false; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String firstLine = reader.readLine();
            if (firstLine == null) {
                return false; 
            }
            return firstLine.equals(matchColumns);
        } catch (IOException e) {
            return false;
        }
    }

    public void createFile(String newcolumns) throws IOException{
        columns = new ArrayList<>(Arrays.asList(newcolumns.split(",")));
        clear();
        writeFile();
    }

    public void readFile() throws IOException {
        lines = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            columns = new ArrayList<>(Arrays.asList(reader.readLine().split(",")));
            for (int i = 0; i < columns.size(); i++) {
                columnIndices.put(columns.get(i), i);
            }

            while ((line = reader.readLine()) != null) {
                lines.add(new ArrayList<>(Arrays.asList(line.split(","))));
            }
        }
    }

    public void writeFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.join(",", columns));
            writer.write("\n");

            for (ArrayList<String> line : lines) {
                writer.write(String.join(",", line));
                writer.write("\n");
            }
        }
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public ArrayList<ArrayList<String>> getLines() {
        return lines;
    }

    public void printLines() {
        System.out.println(String.join(",", columns));

        for (ArrayList<String> line : lines) {
            System.out.println(String.join(",", line));
        }
    }

    public ArrayList<String> getColumn(String column) {
        if (!columnIndices.containsKey(column)) {
            throw new IllegalArgumentException("Column `" + column + "` does not exist.");
        }

        ArrayList<String> columns = new ArrayList<>();
        int columnIndex = columnIndices.get(column);

        for (ArrayList<String> line : lines) {
            columns.add(line.get(columnIndex));
        }

        return columns;
    }

    public ArrayList<String> getLine(int index) {
        if (index < 0 || index > lines.size() - 1) {
            throw new IllegalArgumentException("Index `" + index + "` is out of bounds.");
        }

        return lines.get(index);
    }

    public void addLine(String newLine) {
        ArrayList<String> splitLine = new ArrayList<>(Arrays.asList(newLine.split(",")));

        if (splitLine.size() != columns.size()) {
            throw new IllegalArgumentException("Error: `" + newLine + "` does not match columns length: " + columns.size());
        }

        lines.add(splitLine);
    }

    public void changeLine(int index, String newLine) {
        ArrayList<String> splitLine = new ArrayList<>(Arrays.asList(newLine.split(",")));

        if (splitLine.size() != columns.size()) {
            throw new IllegalArgumentException("Error: `" + newLine + "` does not match columns length: " + columns.size());
        }

        lines.set(index, splitLine);
    }

    public int getLineIndexWithColumns(String searchColumnsString, String valuesString) {
        String[] searchColumns = searchColumnsString.split(",");
        String[] values = valuesString.split(",");

        validateColumnSearch(searchColumns, values);

        ArrayList<String> line;
        int columnIndex;
        boolean match;

        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);

            match = true;

            for (int j = 0; j < searchColumns.length; j++) {
                columnIndex = columnIndices.get(searchColumns[j]);
                if (!line.get(columnIndex).equals(values[j])) {
                    match = false;
                }
            }

            if (match) {
                return i;
            }
        }

        return -1;
    }

    public void changeLineWithColumns(String searchColumnsString, String valuesString, String newLine) {
        String[] searchColumns = searchColumnsString.split(",");
        String[] values = valuesString.split(",");

        validateColumnSearch(searchColumns, values);

        ArrayList<String> line;
        int columnIndex;
        boolean match;

        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);

            match = true;

            for (int j = 0; j < searchColumns.length; j++) {
                columnIndex = columnIndices.get(searchColumns[j]);
                if (!line.get(columnIndex).equals(values[j])) {
                    match = false;
                }
            }

            if (match) {
                lines.set(i, new ArrayList<>(Arrays.asList(newLine.split(","))));
            }
        }
    }

    public void validateColumnSearch(String[] searchColumns, String[] values) {
        for (String column : searchColumns) {
                if (!columnIndices.containsKey(column)) {
                throw new IllegalArgumentException("Column `" + column + "` does not exist.");
            }
        }
        if (searchColumns.length != values.length) {
            throw new IllegalArgumentException("Length of search columns not equal to length of values.");
        }
    }

    public void clear() {
        lines.clear();
    }
}
