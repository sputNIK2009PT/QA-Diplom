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
    }

    // Группа тестов для валидных данных
    @Test
    void shouldSuccessfullyProcessCreditWithApprovedCard() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateApprovedCard());
        paymentPage.clickContinueButton();
        paymentPage.testOperationIsApproved();
    }

    @Test
    void shouldDeclineCreditWithDeclinedCard() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateDeclinedCard());
        paymentPage.clickContinueButton();
        paymentPage.testOperationIsCanceled();
    }

    // Группа тестов для проверки базы данных
    @Test
    void shouldSaveApprovedCreditStatusInDatabase() {
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        dataBaseUtils.clearDB();
        val startPage = new StartPage();
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
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        dataBaseUtils.clearDB();
        val startPage = new StartPage();
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
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        dataBaseUtils.clearDB();
        val startPage = new StartPage();
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
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWith14DigitNumber());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForEmptyCardNumber() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyNumber());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForFakeCardNumber() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithFakeNumber());
        paymentPage.clickContinueButton();
        paymentPage.testOperationIsCanceled();
    }

    // Группа тестов для невалидных данных месяца
    @Test
    void shouldShowErrorForMonthWithOneDigit() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOneDigitMonth());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForMonthOver12() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithMonthOver12());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpirationDateIsIncorrect();
    }

    @Test
    void shouldShowErrorForMonthWithNullValue() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithNullMonth());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpirationDateIsIncorrect();
    }

    @Test
    void shouldShowErrorForEmptyMonth() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyMonth());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    // Группа тестов для невалидных данных года
    @Test
    void shouldShowErrorForYearWithOneDigit() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOneDigitYear());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForPreviousYear() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithPastYear());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpired();
    }

    @Test
    void shouldShowErrorForYearWithNullValue() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithNullYear());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpired();
    }

    @Test
    void shouldShowErrorForEmptyYear() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyYear());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForYearInFuture() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithFutureYear());
        paymentPage.clickContinueButton();
        paymentPage.testCardExpirationDateIsIncorrect();
    }

    // Группа тестов для невалидных данных CVV
    @Test
    void shouldShowErrorForCvvWithOneDigit() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithSingleDigitCvv());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForCvvWithTwoDigits() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithTwoDigitCvv());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForCvvWithZeroValue() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithCvvZero());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForEmptyCvv() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyCvv());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    // Группа тестов для невалидных данных владельца
    @Test
    void shouldShowErrorForOwnerWithOneWord() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOneWordOwner());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForOwnerWithCyrillicSymbols() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithCyrillicOwner());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForOwnerWithDigits() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOwnerContainingDigits());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForOwnerWithSpecialSymbols() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOwnerContainingSpecialSymbols());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForEmptyOwner() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithEmptyOwner());
        paymentPage.clickContinueButton();
        paymentPage.testFieldShouldBeFilled();
    }

    @Test
    void shouldShowErrorForOwnerWithOnlySpaces() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOwnerOnlySpaces());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }

    @Test
    void shouldShowErrorForOwnerWithOnlyHyphens() {
        val startPage = new StartPage();
        val paymentPage = startPage.clickCreditButton();
        paymentPage.fillCardInfo(CardDataGenerator.generateCardWithOwnerOnlyHyphens());
        paymentPage.clickContinueButton();
        paymentPage.testFormatIsInvalid();
    }
}