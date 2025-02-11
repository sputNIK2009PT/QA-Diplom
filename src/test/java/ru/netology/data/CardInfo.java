package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfo {
    private String cardNumber;
    private String month;
    private String year;
    private String cardHolder;
    private String cvv;
}
