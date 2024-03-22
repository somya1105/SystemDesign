package org.expenseTracker.service;

import lombok.Getter;
import org.expenseTracker.beans.Group;
import org.expenseTracker.beans.User;
import org.expenseTracker.beans.expense.EqualExpense;
import org.expenseTracker.beans.expense.ExactExpense;
import org.expenseTracker.beans.expense.Expense;
import org.expenseTracker.beans.expense.ExpenseType;
import org.expenseTracker.beans.expense.PercentExpense;
import org.expenseTracker.beans.split.PercentSplit;
import org.expenseTracker.beans.split.Split;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
public class ExpenseTrackerService {
    List<Expense> expenses;
    Map<String, Group> groupMap;
    Map<String, User> userMap;
    Map<String, Map<String, Double>> balanceSheet;

    public ExpenseTrackerService() {
        this.expenses = new ArrayList<>();
        this.userMap = new HashMap<>();
        this.balanceSheet = new HashMap<>();
    }
    //User Related

    public String addUser(User user) {
        userMap.put(user.getId(), user);
        return "User Added";
    }

    public String getAllUsers() {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : userMap.values()) {
            stringBuilder.append(user.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String addExpense(ExpenseType type, double amount, String userId, List<Split> splits, String label) {
        String paidBy = userId;
        Expense newExpense = createExpense(type, amount, userMap.get(paidBy), splits, label);
        newExpense.validate();
        expenses.add(newExpense);
        for (Split split : newExpense.getSplits()) {
            String paidTo = split.getUserId();
            Map<String, Double> balances = balanceSheet.get(paidBy);
            if (!balances.containsKey(paidTo)) {
                balances.put(paidTo, 0.0);
            }
            balances.put(paidTo, balances.get(paidTo) + split.getAmount());
            balances = balanceSheet.get(paidTo);
            if (!balances.containsKey(paidBy)) {
                balances.put(paidBy, 0.0);
            }
            balances.put(paidBy, balances.get(paidBy) - split.getAmount());
        }
        return "Expense Added";
    }

    public Expense createExpense(ExpenseType type, double amount, User paidBy, List<Split> splits, String label) {
        switch (type) {
            //change to cases for EQUAL, EXACT, PERCENT when implemented
            case EQUAL:
                int totalSplits = splits.size();
                double splitAmount = ((double) Math.round(amount * 100 / totalSplits)) / 100;
                for (Split split : splits) {
                    split.setAmount(splitAmount);
                }
                //to avoid decimal error. Like 100/3 = 33.33, adds up to 99.99. To avoid that
                splits.get(0).setAmount(splitAmount + amount - splitAmount * totalSplits);
                return new EqualExpense(amount, paidBy, new Date(), splits, label);
            case PERCENT:
                for (Split split : splits) {
                    PercentSplit percentSplit = (PercentSplit) split;
                    //double percentAmount = Math.round(amount*percentSplit.getPercent()/100);
                    split.setAmount(amount * percentSplit.getPercent() / 100);
                }
                return new PercentExpense(amount, paidBy, new Date(), splits, label);
            case EXACT:
                return new ExactExpense(amount, paidBy, new Date(), splits, label);
            default:
                System.out.println("Invalid split type");
                return null;
        }
    }

    public String showBalanceForUser(String userId) {
        StringBuilder stringBuilder = new StringBuilder();
        User thisUser = userMap.get(userId);
        if (thisUser == null) {
            return "User doesn't exist";
        }
        for (Map.Entry<String, Double> userBalance : balanceSheet.get(thisUser).entrySet()) {
            //We don't want to print balance to self, which will be Zero ideally
            if (userBalance != thisUser) {
                stringBuilder.append(printBalances(userId, userBalance));
            }
        }
        return stringBuilder.toString();
    }

    private String printBalances(String userId, Map.Entry<String, Double> userBalance) {
        StringBuilder stringBuilder = new StringBuilder();
        if (userBalance.getValue() != 0) {
            if (userBalance.getValue() < 0) {
                stringBuilder.append(userMap.get(userId).getName() + " owes " + Math.abs(userBalance.getValue()) + " to " + userMap.get(userBalance.getKey()).getName());
                return stringBuilder.toString();
            }
            stringBuilder.append(userMap.get(userBalance.getKey()).getName() + " owes " + Math.abs(userBalance.getValue()) + " to " + userMap.get(userId).getName());
            return stringBuilder.toString();
        }
        return ("No balances for " + userId);
    }

    public String showAllBalances() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String userId : userMap.keySet()){
            stringBuilder.append(showBalanceForUser(userId));
        }
        return stringBuilder.toString();
    }


    //Group Related
    public String addGroup(Group group) {
        groupMap.put(group.getId(), group);
        return "Group Added Successfully";
    }

    public String addUserToGroup(String id, String groupId) {
        if (!groupMap.containsKey(groupId))
            return "Group doesn't Exist";
        groupMap.get(groupId).getUsersList().add(id);
        return "Added User to the Group";
    }


    public String getAllGroups() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Group group : groupMap.values()) {
            stringBuilder.append(group.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getGroupById(String id) {
        if (!groupMap.containsKey(id))
            return "Group doesn't exist.";
        return groupMap.get(id).toString();
    }

    public String getGroupByUserId(String userId) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Group group : groupMap.values()) {
            if (group.getUsersList().contains(userId)) {
                stringBuilder.append(group).append("\n");
            }
        }
        if (stringBuilder.isEmpty()) return "No Groups found for the User";
        return stringBuilder.toString();
    }
}
