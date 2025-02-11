package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CardDataGenerator {

    // Генерация дат
    public static String getMonthInFuture() {
        return LocalDate.now().plusMonths(2).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYearInPast(int yearCount) {
        return LocalDate.now().minusYears(yearCount).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getYearInFuture(int yearCount) {
        return LocalDate.now().plusYears(yearCount).format(DateTimeFormatter.ofPattern("yy"));
    }

    // Генерация карт с разными статусами
    public static CardInfo generateApprovedCard() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1));
    }

    public static CardInfo generateDeclinedCard() {
        return generateCard("4444444444444442", getMonthInFuture(), getYearInFuture(1));
    }

    // Генерация карт с невалидными данными
    public static CardInfo generateCardWith14DigitNumber() {
        return generateCard(new Faker().number().digits(14), getMonthInFuture(), getYearInFuture(1));
    }

    public static CardInfo generateCardWithFakeNumber() {
        return generateCard("3333333333333333", getMonthInFuture(), getYearInFuture(1));
    }

    public static CardInfo generateCardWithEmptyNumber() {
        return generateCard("", getMonthInFuture(), getYearInFuture(1));
    }

    public static CardInfo generateCardWithEmptyMonth() {
        return generateCard("4444444444444441", "", getYearInFuture(1));
    }

    public static CardInfo generateCardWithEmptyYear() {
        return generateCard("4444444444444441", getMonthInFuture(), "");
    }

    public static CardInfo generateCardWithEmptyOwner() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), "");
    }

    public static CardInfo generateCardWithEmptyCvv() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), new Faker().name().fullName(), "");
    }

    public static CardInfo generateCardWithOneDigitMonth() {
        return generateCard("4444444444444441", "2", getYearInFuture(1));
    }

    public static CardInfo generateCardWithMonthOver12() {
        return generateCard("4444444444444441", "13", getYearInFuture(1));
    }

    public static CardInfo generateCardWithNullMonth() {
        return generateCard("4444444444444441", "00", LocalDate.now().format(DateTimeFormatter.ofPattern("yy")));
    }

    public static CardInfo generateCardWithOneDigitYear() {
        return generateCard("4444444444444441", getMonthInFuture(), "2");
    }

    public static CardInfo generateCardWithFutureYear() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(6));
    }

    public static CardInfo generateCardWithPastYear() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInPast(1));
    }

    public static CardInfo generateCardWithNullYear() {
        return generateCard("4444444444444441", getMonthInFuture(), "00");
    }

    public static CardInfo generateCardWithSingleDigitCvv() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), new Faker().name().fullName(), new Faker().number().digits(1));
    }

    public static CardInfo generateCardWithTwoDigitCvv() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), new Faker().name().fullName(), new Faker().number().digits(2));
    }

    public static CardInfo generateCardWithCvvZero() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), new Faker().name().fullName(), "000");
    }
    public static CardInfo generateCardWithOneWordOwner() {
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), new Faker().name().firstName());
    }

    public static CardInfo generateCardWithCyrillicOwner() {
        Faker faker = new Faker(new Locale("ru"));
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), faker.name().fullName());
    }

    public static CardInfo generateCardWithOwnerContainingDigits() {
        Faker faker = new Faker();
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), faker.name().firstName() + " " + faker.number().digit());
    }

    public static CardInfo generateCardWithOwnerContainingSpecialSymbols() {
        Faker faker = new Faker();
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1), faker.name().firstName() + " %$ * &");
    }

    public static CardInfo generateCardWithOwnerOnlySpaces() {
        Faker faker = new Faker();
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1),  " ");
    }

    public static CardInfo generateCardWithOwnerOnlyHyphens() {
        Faker faker = new Faker();
        return generateCard("4444444444444441", getMonthInFuture(), getYearInFuture(1),  "-");
    }

    // Вспомогательный метод для генерации карты
    private static CardInfo generateCard(String cardNumber, String month, String year) {
        Faker faker = new Faker();
        return new CardInfo(cardNumber, month, year, faker.name().fullName(), faker.number().digits(3));
    }

    private static CardInfo generateCard(String cardNumber, String month, String year, String owner) {
        Faker faker = new Faker();
        return new CardInfo(cardNumber, month, year, owner, faker.number().digits(3));
    }

    private static CardInfo generateCard(String cardNumber, String month, String year, String owner, String cvv) {
        return new CardInfo(cardNumber, month, year, owner, cvv);
    }
}