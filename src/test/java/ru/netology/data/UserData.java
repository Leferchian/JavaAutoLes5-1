package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserData {
    private String city;
    private String name;
    private String phone;
}
