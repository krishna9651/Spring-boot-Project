package my.apps.rewards.dto;

public class Record {

    private String yearMoth;
    private double amount;

    public Record(String yearMoth, double amount) {
        this.yearMoth = yearMoth;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getYearMoth() {
        return yearMoth;
    }
}
