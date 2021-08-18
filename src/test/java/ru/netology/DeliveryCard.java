package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCard {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    void positiveTest1() {
        $("[data-test-id=city] input").setValue("иров");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("+78798555879");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    void positiveTestDoubleName() {
        $("[data-test-id=city] input").setValue("Москва");
        String date = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("Куляк-Крид Вадим");
        $("[data-test-id=phone] input").setValue("+78798555879");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    void negativeTestCity() {
        $("[data-test-id=city] input").setValue("Кипр");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("+78798555879");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void negativeTestName() {
        $("[data-test-id=city] input").setValue("Москва");
        String date = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("yohan");
        $("[data-test-id=phone] input").setValue("+78798555879");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void negativeTestTel() {
        $("[data-test-id=city] input").setValue("Москва");
        String date = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("78798555879");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void negativeTestAgreement() {
        $("[data-test-id=city] input").setValue("Уфа");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("+78798555879");
        $("[role=button] .button__content").click();

        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldBe(visible).
                shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    void negativeTestNoCity() {
        $("[data-test-id=city] input").setValue("");
        String date = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("+78794155841");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void negativeTestDate() {
        $("[data-test-id=city] input").setValue("Москва");
        String date = LocalDate.now().plusDays(0).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("+78794155841");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=date] .input__sub").shouldBe(visible).
                shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }
}

