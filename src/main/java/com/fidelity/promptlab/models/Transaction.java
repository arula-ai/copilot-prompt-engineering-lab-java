package com.fidelity.promptlab.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a transaction in a portfolio.
 */
public class Transaction {
    private String id;
    private String portfolioId;
    private TransactionType type;
    private String symbol;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;
    private BigDecimal fees;
    private LocalDateTime executedAt;
    private TransactionStatus status;

    public Transaction() {
        this.executedAt = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }

    public enum TransactionType {
        BUY, SELL, DIVIDEND, TRANSFER
    }

    public enum TransactionStatus {
        PENDING, COMPLETED, FAILED, CANCELLED
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPortfolioId() { return portfolioId; }
    public void setPortfolioId(String portfolioId) { this.portfolioId = portfolioId; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getFees() { return fees; }
    public void setFees(BigDecimal fees) { this.fees = fees; }

    public LocalDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(LocalDateTime executedAt) { this.executedAt = executedAt; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }
}
