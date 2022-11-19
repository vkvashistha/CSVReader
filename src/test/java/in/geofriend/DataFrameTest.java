package in.geofriend;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DataFrameTest {

    @Test
    public void testCSVParsedValues() throws IOException {
        DataFrame dataFrame = CSVReader.readCSV("Data.csv");
        String []expectedColumns = "Country,Age,Salary,Purchased".split(",");
        String []expectedCountries = new String[] {"France", "Spain", "Germany", "Spain", "Germany", "France", "Spain","France","Germany","France"};
        String []expectedAge = new String[] {"44", "27", "30", "38", "40", "35", "","48","50","37"};
        String []expectedSalary = new String[] {"72000", "48000", "54000","61000", "", "58000", "52000", "79000","83000","67000"};
        String []expectedPurchased = new String[] {"No", "Yes", "No", "No","Yes", "Yes", "No", "Yes","No","Yes"};

        String []actualColumns = dataFrame.getColumnNames();
        Record.Field[] actualCountries = dataFrame.values("Country");
        Record.Field[] actualAge = dataFrame.values("Age");
        Record.Field[] actualSalary = dataFrame.values("Salary");
        Record.Field[] actualPurchased = dataFrame.values("Purchased");
        assertArrayEqual("Purchase Coliumn values not matching", expectedPurchased, actualPurchased);
        assertArrayEqual("Age Column values not matching", expectedAge, actualAge);
        assertArrayEqual("Header values not matching", expectedColumns, actualColumns);
        assertArrayEqual("Salary Coliumn values not matching", expectedSalary, actualSalary);
        assertArrayEqual("Countries Coliumn values not matching", expectedCountries, actualCountries);

    }

    @Test
    public void testCSVSortValues() throws IOException {
        DataFrame dataFrame = CSVReader.readCSV("Data.csv");
        String []expectedCountries = new String[] {"France", "France", "France","France","Germany","Germany","Germany","Spain", "Spain", "Spain"};
        String []expectedAge = new String[] {"27", "30","35","37","38","40","44","48","50",""};
        Record.Field[] actualCountries = dataFrame.sort("Country", true).values("Country");
        Record.Field[] actualAge = dataFrame.sortAsNumeric("Age",true).values("Age");
        assertArrayEqual("Age Coliumn values not matching", expectedAge, actualAge);
        assertArrayEqual("Countries Coliumn values not matching", expectedCountries, actualCountries);
    }

    @Test
    public void testCSVFilterValues() throws IOException {
        DataFrame dataFrame = CSVReader.readCSV("Data.csv");
        String []expectedColumns = "Country,Age,Salary,Purchased".split(",");
        String []expectedCountries = new String[] {"Spain", "France", "France", "France"};
        String []expectedAge = new String[] { "27",  "35","48","37"};
        String []expectedSalary = new String[] { "48000", "58000",  "79000","67000"};
        String []expectedPurchased = new String[] { "Yes","Yes", "Yes","Yes"};

        String []actualColumns = dataFrame.getColumnNames();
        DataFrame filteredDataframe = dataFrame
                .filter("Age", false, "")
                .filter("Salary", false, "")
                .filter("Purchased", true, "Yes");
        Record.Field[] actualCountries = filteredDataframe.values("Country");
        Record.Field[] actualAge = filteredDataframe.values("Age");
        Record.Field[] actualSalary = filteredDataframe.values("Salary");
        Record.Field[] actualPurchased = filteredDataframe.values("Purchased");
        assertArrayEqual("Purchase Coliumn values not matching", expectedPurchased, actualPurchased);
        assertArrayEqual("Age Column values not matching", expectedAge, actualAge);
        assertArrayEqual("Header values not matching", expectedColumns, actualColumns);
        assertArrayEqual("Salary Coliumn values not matching", expectedSalary, actualSalary);
        assertArrayEqual("Countries Coliumn values not matching", expectedCountries, actualCountries);
    }

    @Test
    public void testCSVFilterAndSortValues() throws IOException {
        DataFrame dataFrame = CSVReader.readCSV("Data.csv");
        String []expectedColumns = "Country,Age,Salary,Purchased".split(",");
        String []expectedCountries = new String[] {"Spain", "France", "France", "France"};
        String []expectedAge = new String[] { "27",  "35","37","48"};
        String []expectedSalary = new String[] { "48000", "58000","67000", "79000"};
        String []expectedPurchased = new String[] { "Yes","Yes", "Yes","Yes"};

        String []actualColumns = dataFrame.getColumnNames();
        DataFrame filteredDataframe = dataFrame
                .filter("Age", false, "")
                .filter("Salary", false, "")
                .filter("Purchased", true, "Yes")
                .sort("Age",true);
        Record.Field[] actualCountries = filteredDataframe.values("Country");
        Record.Field[] actualAge = filteredDataframe.values("Age");
        Record.Field[] actualSalary = filteredDataframe.values("Salary");
        Record.Field[] actualPurchased = filteredDataframe.values("Purchased");
        assertArrayEqual("Purchase Coliumn values not matching", expectedPurchased, actualPurchased);
        assertArrayEqual("Age Column values not matching", expectedAge, actualAge);
        assertArrayEqual("Header values not matching", expectedColumns, actualColumns);
        assertArrayEqual("Salary Coliumn values not matching", expectedSalary, actualSalary);
        assertArrayEqual("Countries Coliumn values not matching", expectedCountries, actualCountries);
    }

    private void assertArrayEqual(String []expected, String []actual) {
        Assert.assertEquals("Column Count not same", expected.length, actual.length);
        for(int i=0; i<expected.length; i++) {
            Assert.assertEquals(expected[i], expected[i]);
        }
    }

    private void assertArrayEqual(String errorMessage, String []expected, String []actual) {
        Assert.assertEquals(errorMessage+ ": Column Count not same", expected.length, actual.length);
        for(int i=0; i<expected.length; i++) {
            String finalErrorMessage = errorMessage + ": " + "Array elements are not equal at position:"+i;
            Assert.assertEquals(finalErrorMessage, expected[i], expected[i]);
        }
    }

    private void assertArrayEqual(String errorMessage, String []expected, Record.Field []actual) {
        Assert.assertEquals("Column Count not same", expected.length, actual.length);
        for(int i=0; i<expected.length; i++) {
            String finalErrorMessage = errorMessage + ": " + "Array elements are not equal at position:"+i;
            Assert.assertEquals(finalErrorMessage,expected[i], actual[i].asString());
        }
    }

    private void assertArrayEqual(String []expected, Record.Field []actual) {
        Assert.assertEquals("Column Count not same", expected.length, actual.length);
        for(int i=0; i<expected.length; i++) {
            String finalErrorMessage ="Array elements are not equal at position:"+i;
            Assert.assertEquals(finalErrorMessage+i,expected[i], actual[i].asString());
        }
    }
}