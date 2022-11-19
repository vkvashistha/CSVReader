package in.geofriend;

import java.util.Arrays;
import java.util.Objects;

public class Record {
    private Field [] fields;
    public Record(Field [] fields) {
        this.fields = fields;
    }

    public Record(String [] values) {
        fields = new Field[values.length];
        for(int i=0; i<fields.length; i++) {
            fields[i] = new Field(values[i]);
        }
    }

    public Field getField(int index) {
        return fields[index];
    }

    @Override
    public String toString() {
        return "Record{" +
                "fields=" + Arrays.toString(fields) +
                '}';
    }

    public static class Field implements Comparable<Field>{
        public static final int FIELD_TYPE_INT = 1;
        public static final int FIELD_TYPE_STRING = 0;
        public static final int FIELD_TYPE_NUMBER = 2;

        private String value;
        private int fieldType = FIELD_TYPE_STRING;
        public Field(String value) {
            this.value = value;
        }

        public Field(String value, int fieldType) {
            this.value = value;
            this.fieldType = fieldType;
        }

        public String asString() {
            return value;
        }

        public Integer asInt() {
            return Integer.parseInt(value);
        }

        public Double asDouble() {
            return Double.parseDouble(value);
        }

        public boolean asBoolean() {
            return Boolean.parseBoolean(value);
        }

        public boolean isBlank() {
            return value.isEmpty();
        }

        @Override
        public int compareTo(Field o) {
            if(fieldType == FIELD_TYPE_STRING) {
                return value.compareTo(o.value);
            } else {
                return asDouble().compareTo(o.asDouble());
            }
        }

        @Override
        public String toString() {
            return "Field{" +
                    "value='" + value + '\'' +
                    ", fieldType=" + fieldType +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Field)) return false;
            Field field = (Field) o;
            return fieldType == field.fieldType && Objects.equals(value, field.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, fieldType);
        }

        public boolean isNumeric() {
            String regex = "^((?!-0?(\\.0+)?(e|$))-?(0|[1-9]\\d*)?(\\.\\d+)?(?<=\\d)(e-?(0|[1-9]\\d*))?|0x[0-9a-f]+)$";
            return value.matches(regex);
        }
    }
}