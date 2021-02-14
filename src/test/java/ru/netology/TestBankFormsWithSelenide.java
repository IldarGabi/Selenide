package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class TestBankFormsWithSelenide {

    @BeforeEach
    void Setup() {
        open("http://localhost:9999/");
    }

    @Test
    void OrderBookingForm() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $(Selectors.withText("Успешно!")).waitUntil(Condition.visible, 13000);
    }

    @Test
    void OrderBookingFormWithoutCity() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void OrderBookingFormWithoutDate() {
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date]").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void OrderBookingFormWithoutName() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void OrderBookingFormNameOnEnglish() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ildar Gabdrahmanov");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name]").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void OrderBookingFormWithoutPhoneNumber() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void OrderBookingFormWithoutPhoneWithoutPlus() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone]").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void OrderBookingFormNotClickCheckBox() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(byText("Забронировать")).click();
        $(".checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
