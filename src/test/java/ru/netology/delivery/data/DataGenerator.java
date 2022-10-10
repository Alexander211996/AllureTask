package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        Faker faker = new Faker(new Locale("ru"));
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String city = faker.options().option("Москва", "Санкт-Петербург", "Иваново", "Владивосток");
        return city;
    }
    public static String generateInvalidCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String invalidCity = faker.options().option("London", "Dresden", "Not exists", "sdfsdf", "2312");
        return invalidCity;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generateDoubleName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.options().option("Анна-Мария", "Алла-Виктория", "Марк-Арсений", "Александр-Кристиан");
        return name;
    }

    public static String generateInvalidName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.options().option("q12312", "F#@#", ")(*&&*й", "dfsdf232@н");
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.numerify("+79#########");
        return phone;
    }

    public static String generateInvalidPhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.numerify("#######");
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            return (UserInfo) user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
