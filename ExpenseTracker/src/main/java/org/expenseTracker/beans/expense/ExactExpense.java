package org.expenseTracker.beans.expense;

import org.expenseTracker.beans.User;
import org.expenseTracker.beans.split.EqualSplit;
import org.expenseTracker.beans.split.ExactSplit;
import org.expenseTracker.beans.split.Split;

import java.util.Date;
import java.util.List;

public class ExactExpense extends Expense {
    public ExactExpense(double amount, User paidBy, Date now, List<Split> splits, String label) {
        super(label, amount, paidBy, now, splits);
    }

    @Override
    public boolean validate() {
        int checkAmount = 0;
        for (Split split : super.getSplits()) {
            if (!(split instanceof ExactSplit)) {
                return false;
            }
            checkAmount += ((EqualSplit) split).getAmount();
        }
        if (checkAmount != super.getAmount()) return false;
        return true;
    }
}