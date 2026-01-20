package com.fidelity.promptlab.models;

import java.math.BigDecimal;

/**
 * Represents a single holding within a portfolio.
 */
public class Holding {
    private String symbol;
    private String name;
    private int quantity;
    private BigDecimal averageCost;
    private BigDecimal currentPrice;
    private BigDecimal marketValue;
    private BigDecimal gainLoss;
    private BigDecimal gainLossPercent;

    public Holding() {}

    public Holding(String symbol, String name, int quantity, BigDecimal averageCost, BigDecimal currentPrice) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.averageCost = averageCost;
        this.currentPrice = currentPrice;
        calculateValues();
    }

    private void calculateValues() {
        this.marketValue = currentPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal costBasis = averageCost.multiply(BigDecimal.valueOf(quantity));
        this.gainLoss = marketValue.subtract(costBasis);
        if (costBasis.compareTo(BigDecimal.ZERO) != 0) {
            this.gainLossPercent = gainLoss.divide(costBasis, 4, java.math.RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        } else {
            this.gainLossPercent = BigDecimal.ZERO;
        }
    }

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getAverageCost() { return averageCost; }
    public void setAverageCost(BigDecimal averageCost) { this.averageCost = averageCost; }

    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }

    public BigDecimal getMarketValue() { return marketValue; }
    public void setMarketValue(BigDecimal marketValue) { this.marketValue = marketValue; }

    public BigDecimal getGainLoss() { return gainLoss; }
    public void setGainLoss(BigDecimal gainLoss) { this.gainLoss = gainLoss; }

    public BigDecimal getGainLossPercent() { return gainLossPercent; }
    public void setGainLossPercent(BigDecimal gainLossPercent) { this.gainLossPercent = gainLossPercent; }
}
