# SimpleCSV

Simple CSV file reader &amp; writer for Java

I needed a super lightweight CSV handler for a project **that doesn't require advanced CSV parsing (e.g. quotation marks)**. If you need a more advanced CSV library that can parse escaped commas and custom delimators, checkout **OpenCSV**.

SimpleCSV provides basic CSV file operations such as creating and checking files with specific headers, compound querying, and reading and writing to file. 

## Features

- Creating CSV files with specified headers
- Checking if a CSV file has specified headers
- Reading CSV files into memory and vice versa
- Getting all rows and columns
- Getting all values of a column
- Adding new rows with validation
- Compound querying (searching based on multiple conditions)
- Reading and writing to rows via index or compound query
- Clearing all rows

## Important Notes

- The class expects CSV files with simple comma-separated values **without** quoted fields or escaped commas
- Column headers and values are case-sensitive
- When adding or modifying rows, the number of values must exactly match the number of columns
   - IllegalArgumentException is raised otherwise
- The class uses zero-based indexing for rows

## Usage

### 1. Initialize

```java
SimpleCSV csv = new SimpleCSV("data.csv");
```

### 2. Create a new CSV file with headers

```java
csv.createFile("Name,Age,City");
```

**Warning: clears the existing CSV file.**

### Alternatively, check if the CSV file matches specific headers

```java
if (csv.matchesColumns("Name,Age,City)) {}
```

### 3. Read existing CSV file

```java
csv.readFile();
```

### 4. Add a new row

```java
csv.addLine("John,25,New York");
```

### 5. Modify a row by index

```java
csv.changeLine(0, "John,26,New York");
```

### 6. Find row index by matching columns

```java
int index = csv.getLineIndexWithColumns("Name,City", "John,New York");
```

### 7. Modify a row by matching columns

```java
csv.changeLineWithColumns("Name,City", "John,New York", "John,27,Boston");
```

### 8. Write changes back to file

```java
csv.writeFile();
```

### 9. Access columns or rows

```java
ArrayList<String> names = csv.getColumn("Name");
ArrayList<String> firstRow = csv.getLine(0);
```

### 10. Print all data

```java
csv.printLines();
```

### 11. Clear all data rows (keeps header)

```java
csv.clear();
```
