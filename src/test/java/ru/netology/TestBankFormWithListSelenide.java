package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class TestBankFormWithListSelenide {
    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void orderBookingForm() {
        $(".input__inner [type=text]").setValue("Са");
        $$("[class=popup__content]").last().find(byText("Самара")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate date = LocalDate.now();
        LocalDate meetDate = date.plusDays(7);
        int currentMonth = date.getMonthValue();
        int meetMonth = meetDate.getMonthValue();
        int meetDay = meetDate.getDayOfMonth();
        if (meetMonth != currentMonth) {
            $("[class='popup__container'] [data-step='1']").click();
        }
        $(byText(String.valueOf(meetDay))).click();
        $("[data-test-id=name] input").setValue("Ильдар Габдрахманов");
        $("[data-test-id=phone] input").setValue("+79992121212");
        $(".checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification]").waitUntil(Condition.visible, 13000)
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на "
                        + meetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }
}
