package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class TestBankFormsWithSelenide {

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void orderBookingForm() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification]").waitUntil(Condition.visible, 13000)
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + date));
    }

    @Test
    void orderBookingFormWithoutCity() {
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
    void orderBookingFormWithoutDate() {
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date]").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void orderBookingFormWithoutName() {
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
    void orderBookingFormNameOnEnglish() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ildar Gabdrahmanov");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name]").shouldHave(text("Имя и Фамилия указаные неверно." +
                " Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void orderBookingFormWithoutPhoneNumber() {
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
    void orderBookingFormWithoutPhoneWithoutPlus() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone]").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр," +
                " например, +79012345678."));
    }

    @Test
    void orderBookingFormNotClickCheckBox() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".input__inner [type=text]").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(date);
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(byText("Забронировать")).click();
        $(".input_invalid").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих" +
                " персональных данных"));
    }
}
