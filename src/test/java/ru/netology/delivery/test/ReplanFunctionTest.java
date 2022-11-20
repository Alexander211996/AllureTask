package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.Keys;
import org.testng.annotations.*;

import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class ReplanFunctionTest {

    @BeforeClass
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterClass
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @BeforeMethod
    public void openUrl() {
        open("http://localhost:9999/");
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    @Test
    public void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] .input__control").setValue(validUser.getCity());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        DataGenerator.generateDate(daysToAddForFirstMeeting)));
        $("[data-test-id='name'] .input__control").setValue(validUser.getName());
        $("[data-test-id='phone'] .input__control").setValue(validUser.getPhone());
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Встреча успешно запланирована"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text(DataGenerator.generateDate(daysToAddForFirstMeeting)));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        DataGenerator.generateDate(daysToAddForSecondMeeting)));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Перепланировать?"))
                .shouldBe(Condition.visible, Duration.ofSeconds(3));
        $$("button").find(Condition.exactText("Перепланировать")).click();
        $(withText("Встреча успешно запланирована"))
                .shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldBe(Condition.text(DataGenerator.generateDate(daysToAddForSecondMeeting)));
    }

}

