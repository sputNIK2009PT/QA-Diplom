package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardDataGenerator;
import ru.netology.data.DataBaseUtils;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {
    public static String url = System.getProperty("sut.url");
    private StartPage startPage;
    private DataBaseUtils dataBaseUtils;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open(url);
        startPage = new StartPage();
        dataBaseUtils = new DataBaseUtils();
        dataBaseUtils.clearDB();
    }

    // Группа тестов для валидных данных
    @Test
    void shouldSuccessfullyProcessCreditWithApprovedCard() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateApprovedCard());
        paymentPage.clickContinueButton();
        paymentPage.testOperationIsApproved();
    }

    @Test
    void shouldDeclineCreditWithDeclinedCard() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateDeclinedCard());
        paymentPage.clickContinueButton();
        paymentPage.testOperationIsCanceled();
    }

    // Группа тестов для проверки базы данных
    @Test
    void shouldSaveApprovedCreditStatusInDatabase() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateApprovedCard());
        paymentPage.clickContinueButton();
        paymentPage.waitRequest();
        String status = dataBaseUtils.getCreditStatus();
        dataBaseUtils.clearDB();
        dataBaseUtils.close();
        assertEquals("APPROVED", status);
    }

    @Test
    void shouldSaveDeclinedCreditStatusInDatabase() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateDeclinedCard());
        paymentPage.clickContinueButton();
        paymentPage.waitRequest();
        String status = dataBaseUtils.getCreditStatus();
        dataBaseUtils.clearDB();
        dataBaseUtils.close();
        assertEquals("DECLINED", status);
    }

    @Test
    void shouldNotSaveCreditWithFakeCardInDatabase() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithFakeNumber());
        paymentPage.clickContinueButton();
        paymentPage.waitRequest();
        String orderCount = dataBaseUtils.getOrderCount();
        dataBaseUtils.clearDB();
        dataBaseUtils.close();
        assertEquals("0", orderCount);
    }

    // Группа тестов для невалидных данных карты
    @Test
    void shouldShowErrorForCardNumberWith14Digits() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWith14DigitNumber());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForEmptyCardNumber() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyNumber());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForFakeCardNumber() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithFakeNumber());
        paymentPage.clickContinueButton();
        paymentPage.testOperationIsCanceled();
    }

    // Группа тестов для невалидных данных месяца
    @Test
    void shouldShowErrorForMonthWithOneDigit() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOneDigitMonth());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForMonthOver12() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithMonthOver12());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpirationDateIsIncorrect();
    }

    @Test
    void shouldShowErrorForMonthWithNullValue() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithNullMonth());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpirationDateIsIncorrect();
    }

    @Test
    void shouldShowErrorForEmptyMonth() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyMonth());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    // Группа тестов для невалидных данных года
    @Test
    void shouldShowErrorForYearWithOneDigit() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOneDigitYear());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForPreviousYear() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithPastYear());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpired();
    }

    @Test
    void shouldShowErrorForYearWithNullValue() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithNullYear());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpired();
    }

    @Test
    void shouldShowErrorForEmptyYear() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyYear());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForYearInFuture() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithFutureYear());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpirationDateIsIncorrect();
    }

    // Группа тестов для невалидных данных CVV
    @Test
    void shouldShowErrorForCvvWithOneDigit() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithSingleDigitCvv());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForCvvWithTwoDigits() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithTwoDigitCvv());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForCvvWithZeroValue() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithCvvZero());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForEmptyCvv() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyCvv());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    // Группа тестов для невалидных данных владельца
    @Test
    void shouldShowErrorForOwnerWithOneWord() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOneWordOwner());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForOwnerWithCyrillicSymbols() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithCyrillicOwner());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForOwnerWithDigits() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOwnerContainingDigits());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForOwnerWithSpecialSymbols() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOwnerContainingSpecialSymbols());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForEmptyOwner() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyOwner());
        paymentPage.clickContinueButton();
        paymentPage.testFieldShouldBeFilled();
    }

    @Test
    void shouldShowErrorForOwnerWithOnlySpaces() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOwnerOnlySpaces());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForOwnerWithOnlyHyphens() {
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOwnerOnlyHyphens());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }
}