package io.evilgeniuses.energy_optimization.parsing;


public class CustomUploadData {

    private String fileName;
    private long timestamp;

    public CustomUploadData(String fileName, long timestamp) {
        this.fileName = fileName;
        this.timestamp = timestamp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UploadData{" +
                "fileName='" + fileName + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
