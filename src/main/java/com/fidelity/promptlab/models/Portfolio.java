package com.fidelity.promptlab.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's investment portfolio.
 */
public class Portfolio {
    private String id;
    private String userId;
    private String name;
    private List<Holding> holdings;
    private BigDecimal totalValue;
    private LocalDateTime lastUpdated;
    private RiskProfile riskProfile;

    public Portfolio() {
        this.holdings = new ArrayList<>();
        this.totalValue = BigDecimal.ZERO;
        this.lastUpdated = LocalDateTime.now();
    }

    public enum RiskProfile {
        CONSERVATIVE,
        MODERATE,
        AGGRESSIVE
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Holding> getHoldings() { return holdings; }
    public void setHoldings(List<Holding> holdings) { this.holdings = holdings; }

    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public RiskProfile getRiskProfile() { return riskProfile; }
    public void setRiskProfile(RiskProfile riskProfile) { this.riskProfile = riskProfile; }
}
