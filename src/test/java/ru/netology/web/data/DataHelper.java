package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    DataHelper() {

    }
    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("aysav", "987654");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor() {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559000000000001");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559000000000002");
    }
    public static int checkBalanceOfCardRecharged(int balance, int amountUpdated) {
        return balance - amountUpdated;
    }

    public static int checkBalanceOfCardRefilled(int balance, int amountUpdated) {
        return balance + amountUpdated;
    }


}
