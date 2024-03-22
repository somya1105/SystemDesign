package org.expenseTracker.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@Jacksonized
public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
}
