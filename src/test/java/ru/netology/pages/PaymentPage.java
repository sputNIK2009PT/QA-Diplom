package ru.netology.pages;

import ru.netology.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    public PaymentPage(String text) {
        $$("h3").find(exactText(text)).shouldBe(visible);
    }

    public void fillCardInfo(CardInfo card) {
        $$(".input__top").filterBy(exactText("Номер карты")).first()
                .parent().find("input.input__control").setValue(card.getCardNumber());
        $$(".input__top").filterBy(exactText("Месяц")).first()
                .parent().find("input.input__control").setValue(card.getMonth());
        $$(".input__top").filterBy(exactText("Год")).first()
                .parent().find("input.input__control").setValue(card.getYear());
        $$(".input__top").filterBy(exactText("Владелец")).first()
                .parent().find("input.input__control").setValue(card.getCardHolder());
        $$(".input__top").filterBy(exactText("CVC/CVV")).first()
                .parent().find("input.input__control").setValue(card.getCvv());
    }

    public void clickContinueButton() {
        $$("button").filterBy(exactText("Продолжить")).first().click();
    }

    public void waitRequest() {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 15000) {
            if ($(".notification_status_error").isDisplayed() || $(".notification_status_ok").isDisplayed()) {
                return;
            }
        }
    }

    public void testOperationIsApproved() {
        $(byText("Операция одобрена Банком.")).parent().$("[class=\"notification__content\"]").shouldBe(visible, Duration.ofSeconds(15));
    }

    public void testOperationIsCanceled() {
        $(byText("Ошибка! Банк отказал в проведении операции.")).parent().$("[class=\"notification__content\"]").shouldBe(visible, Duration.ofSeconds(15));
    }

    public void testFormatIsInvalid() {
        $(byText("Неверный формат")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void testCardExpirationDateIsIncorrect() {
        $(byText("Неверно указан срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void testCardExpired() {
        $(byText("Истёк срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void testFieldShouldBeFilled() {
        $(byText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
    }
}