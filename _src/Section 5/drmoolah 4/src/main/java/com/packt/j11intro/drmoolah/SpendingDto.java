package com.packt.j11intro.drmoolah;

public class SpendingDto {
    private String date;
    private String description;
    private Double amount;

    public SpendingDto(String date, String description, Double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public static SpendingDto parseCsvLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new RuntimeException("Failed to parse line to SpendingDto, should have 3 columns, date, description, and amount. line["+line+"]");
        }

        return new SpendingDto(parts[0], parts[1], Double.valueOf(parts[2]));
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
