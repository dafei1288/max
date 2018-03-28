package com.dafei1288.max.data;

public class CsvLoader implements DataLoader{
    private boolean includHeader = true;
    private String delimiter = ",";
    private String Filepath = "";


    public boolean isIncludHeader() {
        return includHeader;
    }

    public void setIncludHeader(boolean includHeader) {
        this.includHeader = includHeader;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public CsvLoader(){}

    public CsvLoader(boolean includHeader, String delimiter) {
        this.includHeader = includHeader;
        this.delimiter = delimiter;
    }
}
