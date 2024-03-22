package org.expenseTracker.beans.expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.expenseTracker.beans.User;
import org.expenseTracker.beans.split.Split;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public abstract class Expense {
    private String label;
    private double amount;
    private User paidBy;
    private Date createdDate;
    private List<Split> splits;

    public abstract boolean validate();
}
