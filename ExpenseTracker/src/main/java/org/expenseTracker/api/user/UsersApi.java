package org.expenseTracker.api.user;

import lombok.RequiredArgsConstructor;
import org.expenseTracker.beans.Group;
import org.expenseTracker.beans.User;
import org.expenseTracker.beans.expense.Expense;
import org.expenseTracker.beans.expense.ExpenseType;
import org.expenseTracker.service.ExpenseTrackerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UsersApi {
    private ExpenseTrackerService expenseTrackerService = new ExpenseTrackerService();

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        return expenseTrackerService.addUser(user);
    }

    @GetMapping("/getAllUsers")
    public String getUser() {
        return expenseTrackerService.getAllUsers();
    }

    @PostMapping("addExpense")
    public String addExpense(@RequestBody String expenseType, @RequestBody Expense expense) {
        return expenseTrackerService.addExpense(ExpenseType.valueOf(expenseType), expense.getAmount(), expense.getPaidBy().getId(), expense.getSplits(), expense.getLabel());
    }

    @GetMapping("getBalance/byId")
    public String getUser(@RequestParam("id") String id) {
        return expenseTrackerService.showBalanceForUser(id);
    }

    @GetMapping("/allBalances")
    public String getAllBalances() {
        return expenseTrackerService.showAllBalances();
    }

    //Group Related APIs
    @PostMapping("/addGroup")
    public String addGroup(@RequestBody Group group) {
        return expenseTrackerService.addGroup(group);
    }

    @PostMapping("/addUserToGroup")
    public String addUserToGroup(@RequestBody String id, @RequestBody String groupId) {
        return expenseTrackerService.addUserToGroup(id, groupId);
    }

    @GetMapping("/getAllGroups")
    public String getAllGroup() {
        return expenseTrackerService.getAllGroups();
    }

    @GetMapping("getGroup/byId")
    public String getGroupById(@RequestParam("id") String id) {
        return expenseTrackerService.getGroupById(id);
    }

    @GetMapping("getGroup/byUserId")
    public String getGroupByUserId(@RequestParam("id") String userId) {
        return expenseTrackerService.getGroupByUserId(userId);
    }


}

