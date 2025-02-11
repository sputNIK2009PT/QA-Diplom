package ru.netology.pages;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    public StartPage() {
        $("h2").shouldBe(text("Путешествие дня"));
    }

    public PaymentPage clickPaymentButton() {
        $$("button").filterBy(exactText("Купить")).first().click();
        return new PaymentPage("Оплата по карте");
    }

    public PaymentPage clickCreditButton() {
        $$("button").filterBy(exactText("Купить в кредит")).first().click();
        return new PaymentPage("Кредит по данным карты");
    }
}
