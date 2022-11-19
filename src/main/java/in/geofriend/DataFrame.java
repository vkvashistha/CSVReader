package in.geofriend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataFrame {
    private List<Record> records;
    private String[] cols;
    public DataFrame(String csvContent, char separator) {
        String []lines = split(csvContent,'\n');
        records = new ArrayList<>(lines.length);
        for(int i=0; i<lines.length; i++) {
            if(i == 0) {
                cols = split(lines[i],separator);
            } else {
                records.add(new Record(split(lines[i], separator)));
            }
        }
    }

    public DataFrame(String []cols, Record [] records) {
        this.records = Arrays.asList(records);
        this.cols = cols;
    }

    public DataFrame(String [] cols, List<Record> records) {
        this.records = records;
        this.cols = cols;
    }

    private DataFrame sort(final int index, final boolean ascending, final boolean sortAsNumeric) {
        Record[] recordArr = records.toArray(new Record[]{});
        Arrays.sort(recordArr, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                if(ascending) {
                    if(sortAsNumeric) {
                        if(o1.getField(index).isNumeric() && o2.getField(index).isNumeric()) {
                            return o1.getField(index).asDouble().compareTo(o2.getField(index).asDouble());
                        } else {
                            if(o1.getField(index).isNumeric() && !o2.getField(index).isNumeric()) {
                                return -1;
                            } else if(!o1.getField(index).isNumeric() && o2.getField(index).isNumeric()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    } else {
                        return o1.getField(index).compareTo(o2.getField(index));
                    }
                } else {
                    if(sortAsNumeric) {
                        if(o1.getField(index).isNumeric() && o2.getField(index).isNumeric()) {
                            return o2.getField(index).asDouble().compareTo(o1.getField(index).asDouble());
                        } else {
                            if (o1.getField(index).isNumeric() && !o2.getField(index).isNumeric()) {
                                return -1;
                            } else if (!o1.getField(index).isNumeric() && o2.getField(index).isNumeric()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    } else {
                        return o2.getField(index).compareTo(o1.getField(index));
                    }
                }
            }
        });
        return new DataFrame(cols,recordArr);
    }
    public DataFrame sort(int index, boolean ascending) {
        return sort(index, ascending, false);
    }

    public DataFrame sort(Comparator<Record> comparator) {
        Record[] recordArr = records.toArray(new Record[]{});
        Arrays.sort(recordArr, comparator);
        return new DataFrame(cols,recordArr);
    }

    public DataFrame filter(int index, boolean include, String ...values) {
        List<String> filterValues = Arrays.asList(values);
        List<Record> filteredList = records.stream().filter(record -> {
            if(include) {
                return (filterValues.contains(record.getField(index).asString()));
            } else {
                return !(filterValues.contains(record.getField(index).asString()));
            }
        }).collect(Collectors.toList());
        return new DataFrame(cols, filteredList);
    }

    public DataFrame filter(String columnName, boolean include, String ...values) {
        return filter(getColumnIndex(columnName), include, values);
    }

    public DataFrame sort(String columnName, boolean ascending) {
        return sort(getColumnIndex(columnName), ascending);
    }

    public DataFrame sortAsNumeric(String columnName, boolean ascending) {
        return sort(getColumnIndex(columnName), ascending, true);
    }

    public int totalRecords() {
        return records.size();
    }

    public int getColumnCount() {
        return cols.length;
    }

    public Record.Field[] values(int idx) {
        Record.Field []fields = new Record.Field[records.size()];
        for(int i=0; i<fields.length; i++) {
            fields[i] = records.get(i).getField(idx);
        }
        return fields;
    }

    public List<Record> values() {
        return records;
    }

    public Record.Field[] values(String colName) {
        return values(getColumnIndex(colName));
    }

    public Record.Field value(String colName, int index) {
        int colIdx = getColumnIndex(colName);
        return records.get(index).getField(colIdx);
    }

    public String[] getColumnNames() {
        return cols;
    }

    public int getColumnIndex(String colName) {
        for(int i=0; i<cols.length; i++) {
            if(cols[i].equals(colName)) {
                return i;
            }
        }
        return -1;
    }

    private String[] split(String content, char separator) {
        ArrayList<String> tokens = new ArrayList<>();
        int quoteCount = 0;
        StringBuilder token = new StringBuilder();
        for(int i=0; i<content.length(); i++) {
            char currentChar = content.charAt(i);
            if(currentChar == separator && quoteCount%2 == 0) {
                tokens.add(token.toString());
                token = new StringBuilder();
                quoteCount = 0;
            } else {
                if (currentChar == '"') {
                    quoteCount++;
                }
                token.append(currentChar);
            }
        }
        if(token.length() > 0) {
            tokens.add(token.toString());
        }
        return tokens.toArray(new String[0]);
    }
}