package com.Project.Project.AppleCouponShop.beans;

import com.Project.Project.AppleCouponShop.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetails {
    //private String userId;
    private String email;
    private String password;
    private ClientType userType;

    public UserDetails(String email, ClientType userType) {
        this.email = email;
        this.userType = userType;
    }
}
