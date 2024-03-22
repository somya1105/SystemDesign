package org.expenseTracker.beans.split;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.expenseTracker.beans.User;


@Data
@AllArgsConstructor
@Getter
@Setter
public abstract class Split {
    private double amount;
    private String userId;

    Split(String userId) {
        this.userId = userId;
    }
}
