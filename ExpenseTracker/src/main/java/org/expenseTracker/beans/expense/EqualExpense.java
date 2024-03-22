package org.expenseTracker.beans.expense;

import org.expenseTracker.beans.User;
import org.expenseTracker.beans.split.EqualSplit;
import org.expenseTracker.beans.split.Split;

import java.util.Date;
import java.util.List;

public class EqualExpense extends Expense {
    public EqualExpense(double amount, User paidBy, Date now, List<Split> splits, String label) {
        super(label, amount, paidBy, now, splits);
    }

    @Override
    public boolean validate() {
        for (Split split : super.getSplits()) {
            if (!(split instanceof EqualSplit)) return false;
        }
        return true;
    }
}