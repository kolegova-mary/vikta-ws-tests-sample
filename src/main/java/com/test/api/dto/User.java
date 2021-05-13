package com.test.api.dto;

import lombok.Data;

@Data
public class User {
    String loginName;
    Integer id;
    String password;
    String email;
    String surname;
    String firstName;
    String middleName;
    String pathToAvatarImage;
    String[] addressIds;
    String[] paymentCardIds;

    public User(String loginName, Integer id, String password, String email,
                String surname, String firstName, String middleName,
                String pathToAvatarImage, String[] addressIds,
                String[] paymentCardIds) {
        this.loginName = loginName;
        this.id = id;
        this.password = password;
        this.email = email;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.pathToAvatarImage = pathToAvatarImage;
        this.addressIds = addressIds;
        this.paymentCardIds = paymentCardIds;
    }

    public User() {
    }
}
