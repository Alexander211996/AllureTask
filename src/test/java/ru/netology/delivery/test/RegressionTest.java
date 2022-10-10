package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Keys;
import org.testng.annotations.*;

import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class RegressionTest {

    @BeforeMethod
    public void openUrl() {
        open("http://localhost:9999/");
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    @Test
    public void shouldSendFormWithCorrectData() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(3)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно запланирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldBe(Condition.text(DataGenerator.generateDate(3)));
    }

    @Test
    public void shouldSendFormForNextDay() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(4)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно запланирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldBe(Condition.text(DataGenerator.generateDate(4)));
    }

    @Test
    public void shouldNotSendFormWithEmptyCity() {
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(3)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldNotSendFormWithNotRussianOrNonExistingCity() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateInvalidCity("en"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(4)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldNotSendFormWithToEarlyDate() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(1)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldSendFormWithOneYearDate() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(365)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно запланирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldBe(Condition.text(DataGenerator.generateDate(365)));
    }

    @Test
    public void shouldNotSendFormWithNoNameAndSurname() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(3)));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldSendFormWithDoubleName() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(3)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateDoubleName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно запланирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldBe(Condition.text(DataGenerator.generateDate(3)));

    }

    @Test
    public void shouldNotSendFormWithSymbolsInName() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(3)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateInvalidName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotSendFormWithNameInEnglish() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(3)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("en"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotSendFormWithInvalidPhone() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(4)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generateInvalidPhone("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotSendFormWithNoPhone() {
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, DataGenerator.generateDate(4)));
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}
