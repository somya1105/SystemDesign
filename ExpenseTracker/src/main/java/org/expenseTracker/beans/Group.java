package org.expenseTracker.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Data
@AllArgsConstructor
@Getter
@Setter
@Jacksonized
public class Group {
    private String id;
    private String name;
    private Set<String> usersList;
    Boolean simplify;
}
