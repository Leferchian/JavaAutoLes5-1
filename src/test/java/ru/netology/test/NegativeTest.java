package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.ExactText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerate;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.HOME;
import static org.openqa.selenium.Keys.SHIFT;

public class NegativeTest {
    private SelenideElement nameField = $("div [data-test-id='name'] input");
    private SelenideElement cityField = $("div [data-test-id='city'] input");
    private SelenideElement dateField = $("div [data-test-id='date'] input");
    private SelenideElement phoneField = $("div [data-test-id='phone'] input");
    private SelenideElement checkbox = $("span.checkbox__box");
    private SelenideElement buttonPlan = $("div.grid-row span.button__text");
    private SelenideElement buttonReplan = $("div.notification__content span.button__text");

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldPlanAndReplanMeetWithoutShift() {
        var validUser = DataGenerate.generateUser("ru");
        var daysAddFirst = 3;
        var firstDate = DataGenerate.generateDate(daysAddFirst);
        var daysAddSecond = 3;
        var secondDate = DataGenerate.generateDate(daysAddSecond);
        cityField.setValue(validUser.getCity());
        dateField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        dateField.setValue(firstDate);
        nameField.setValue(validUser.getName());
        phoneField.setValue(validUser.getPhone());
        checkbox.click();
        buttonPlan.click();
        $("div [data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(40));
        cityField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        cityField.setValue(validUser.getCity());
        dateField.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateField.setValue(secondDate);
        nameField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        nameField.setValue(validUser.getName());
        phoneField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        phoneField.setValue(validUser.getPhone());
        buttonPlan.click();
        buttonReplan.click();
        $("div [data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondDate), Duration.ofSeconds(40));

    }

    @Test
    void shouldReplanAfterCloseReplanNotification() {
        var validUser = DataGenerate.generateUser("ru");
        var daysAddFirst = 4;
        var firstDate = DataGenerate.generateDate(daysAddFirst);
        var daysAddSecond = 7;
        var secondDate = DataGenerate.generateDate(daysAddSecond);
        cityField.setValue(validUser.getCity());
        dateField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        dateField.setValue(firstDate);
        nameField.setValue(validUser.getName());
        phoneField.setValue(validUser.getPhone());
        checkbox.click();
        buttonPlan.click();
        $("div [data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(40));
        cityField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        cityField.setValue(validUser.getCity());
        dateField.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateField.setValue(secondDate);
        nameField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        nameField.setValue(validUser.getName());
        phoneField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        phoneField.setValue(validUser.getPhone());
        buttonPlan.click();
        $("div [data-test-id='replan-notification'] span.icon_name_close").click();
        buttonPlan.click();
        buttonReplan.click();
        $("div [data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondDate), Duration.ofSeconds(40));


    }

    @Test
    void shouldTrowNotificationOfError() {
        var validUser = DataGenerate.generateUser("ru");
        var daysAddFirst = 4;
        var firstDate = DataGenerate.generateDate(daysAddFirst);
        var daysAddSecond = 1;
        var secondDate = DataGenerate.generateDate(daysAddSecond);
        cityField.setValue(validUser.getCity());
        dateField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        dateField.setValue(firstDate);
        nameField.setValue(validUser.getName());
        phoneField.setValue(validUser.getPhone());
        checkbox.click();
        buttonPlan.click();
        $("div [data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(40));
        cityField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        cityField.setValue(validUser.getCity());
        dateField.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateField.setValue(secondDate);
        nameField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        nameField.setValue(validUser.getName());
        phoneField.sendKeys(Keys.SHIFT, HOME, Keys.BACK_SPACE);
        phoneField.setValue(validUser.getPhone());
        buttonPlan.click();
        $("[data-test-id='date'] span.input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }
}
