package org.expenseTracker.beans.expense;

import org.expenseTracker.beans.User;
import org.expenseTracker.beans.split.PercentSplit;
import org.expenseTracker.beans.split.Split;

import java.util.Date;
import java.util.List;

public class PercentExpense extends Expense {

    public PercentExpense(double amount, User paidBy, Date now, List<Split> splits, String label) {
        super(label, amount, paidBy, now, splits);
    }

    @Override
    public boolean validate() {
        int checkPercent = 0;
        for (Split split : super.getSplits()) {
            if (!(split instanceof PercentSplit)) {
                return false;
            }
            checkPercent += ((PercentSplit) split).getPercent();
        }
        double totalPercent = 100;
        if (checkPercent != totalPercent) return false;
        return true;
    }
}
