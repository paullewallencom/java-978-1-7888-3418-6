package com.packt.j11intro.drmoolah;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SpendingDto {
    private final static CurrencyUnit USD = Monetary.getCurrency("USD");

    private LocalDate date;
    private String description;
    private Money amount;

    public SpendingDto(LocalDate date, String description, Money amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public static SpendingDto parseCsvLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new RuntimeException("Failed to parse line to SpendingDto, should have 3 columns, date, description, and amount. line[" + line + "]");
        }

        return new SpendingDto(LocalDate.parse(parts[0]), parts[1], Money.of(new BigDecimal(parts[2]), USD));
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
