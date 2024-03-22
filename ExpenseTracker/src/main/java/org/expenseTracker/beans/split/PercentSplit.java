package org.expenseTracker.beans.split;

import org.expenseTracker.beans.User;

public class PercentSplit extends Split {
    double percent;

    public PercentSplit(User user, Double percent) {
        super(user);
        this.percent = percent;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}