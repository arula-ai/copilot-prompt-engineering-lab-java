package com.fidelity.promptlab.models;

/**
 * User preferences for display and notifications.
 */
public class UserPreferences {
    private Theme theme;
    private boolean notifications;
    private boolean twoFactorEnabled;
    private String defaultCurrency;

    public UserPreferences() {
        this.theme = Theme.LIGHT;
        this.notifications = true;
        this.twoFactorEnabled = false;
        this.defaultCurrency = "USD";
    }

    public enum Theme {
        LIGHT, DARK
    }

    // Getters and Setters
    public Theme getTheme() { return theme; }
    public void setTheme(Theme theme) { this.theme = theme; }

    public boolean isNotifications() { return notifications; }
    public void setNotifications(boolean notifications) { this.notifications = notifications; }

    public boolean isTwoFactorEnabled() { return twoFactorEnabled; }
    public void setTwoFactorEnabled(boolean twoFactorEnabled) { this.twoFactorEnabled = twoFactorEnabled; }

    public String getDefaultCurrency() { return defaultCurrency; }
    public void setDefaultCurrency(String defaultCurrency) { this.defaultCurrency = defaultCurrency; }
}
