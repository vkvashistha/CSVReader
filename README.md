# CSVReader for Java
Read CSV or TSV files into `DataFrame` and perform common operations such as `sort` and `filter`.

## Usage
For example, let's say we have `Data.csv` with following content:-
```
Country,Age,Salary,Purchased
France,44,72000,No
Spain,27,48000,Yes
Germany,30,54000,No
Spain,38,61000,No
Germany,40,,Yes
France,35,58000,Yes
Spain,,52000,No
France,48,79000,Yes
Germany,50,83000,No
France,37,67000,Yes
```

Following code snippet loads csv content:-
```
DataFrame dataFrame = CSVReader.readCSV("Data.csv");
```

Following code snippet filter out rows where Age and Salary columns having null/empty string, select rows where Purchased column value is "Yes". Then finally sort it by Age.
```
DataFrame filteredDataframe = dataFrame
                .filter("Age", false, "")
                .filter("Salary", false, "")
                .filter("Purchased", true, "Yes")
                .sort("Age",true);
```

## API Reference
### DataFrame
1. ```DataFrame DataFrame.filter(String columnName, boolean include, String []values)```
2. ```DataFrame DataFrame.filter(int colIndex, boolean include, String []values)```
3. ```DataFrame DataFrame.sort(String columnName, boolean ascending)```
4. ```DataFrame DataFrame.sort(int colIndex, boolean ascending)```
5. ```Record.Field[] DataFrame.values(String columnName)```
6. ```Record.Field[] DataFrame.values(int colIndex)```
7. ```String[] DataFrame.getColumnNames()```
8. ```int DataFrame.getColumnCount()```

### CSVReader
1. DataFrame CSVReader.readCSV(String filePath)
2. DataFrame CSVReader.readTSV(String filePath)

### Record
1. Record.Field Record.getField(int colIndex)

### Record.Field
```
1. String Record.Field.asString()
2. Integer Record.Field.asInt()
3. Double Record.Field.asNumber()
```