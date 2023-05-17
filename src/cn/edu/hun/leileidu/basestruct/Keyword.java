package cn.edu.hun.leileidu.basestruct;


public class Keyword {
    protected String value = null;
    protected int fileNumber = 0;
    public Keyword(String value) {
        this.value = value;
    }

    public Keyword(String value, int fileNumber) {
        this.value = value;
        this.fileNumber = fileNumber;
    }

    public String getValue() {
        return value;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "value='" + value + '\'' +
                ", fileNumber=" + fileNumber +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Keyword){
            Keyword k = (Keyword) obj;
            return this.value.equals(k.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        return result;
    }
}