package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        val verificationCode = DataHelper.getVerificationCodeFor();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferSomeSumFromFirstToSecondCard() {
        val firstCardBalance = dashboardPage.getCurrentBalanceOfFirstCard();
        val secondCardBalance = dashboardPage.getCurrentBalanceOfSecondCard();
        val moneyTransferPage = dashboardPage.clickSecondCardToRefill();
        int amount = 8000;
        moneyTransferPage.moneyTransferCard(getFirstCardInfo(), amount);
        val firstCardBalanceUpdated = dashboardPage.getCurrentBalanceOfFirstCard();
        val secondCardBalanceUpdated = dashboardPage.getCurrentBalanceOfSecondCard();
        val rechargedCardBalance = checkBalanceOfCardRecharged(firstCardBalance, amount);
        val refilledCardBalance = checkBalanceOfCardRefilled(secondCardBalance, amount);
        assertEquals(rechargedCardBalance, firstCardBalanceUpdated);
        assertEquals(refilledCardBalance, secondCardBalanceUpdated);
    }

    @Test
    void shouldTransferSomeSumFromSecondToFirstCard() {
        val firstCardBalance = dashboardPage.getCurrentBalanceOfFirstCard();
        val secondCardBalance = dashboardPage.getCurrentBalanceOfSecondCard();
        val moneyTransferPage = dashboardPage.clickFirstCardToRefill();
        int amount = 8000;
        moneyTransferPage.moneyTransferCard(getSecondCardInfo(), amount);
        val firstCardBalanceUpdated = dashboardPage.getCurrentBalanceOfFirstCard();
        val secondCardBalanceUpdated = dashboardPage.getCurrentBalanceOfSecondCard();
        val rechargedCardBalance = checkBalanceOfCardRecharged(secondCardBalance, amount);
        val refilledCardBalance = checkBalanceOfCardRefilled(firstCardBalance, amount);
        assertEquals(rechargedCardBalance, secondCardBalanceUpdated);
        assertEquals(refilledCardBalance, firstCardBalanceUpdated);
    }

    @Test
    void shouldTransfer0() {
        val firstCardBalance = dashboardPage.getCurrentBalanceOfFirstCard();
        val secondCardBalance = dashboardPage.getCurrentBalanceOfSecondCard();
        val moneyTransferPage = dashboardPage.clickSecondCardToRefill();
        int amount = 0;
        moneyTransferPage.moneyTransferCard(getFirstCardInfo(), amount);
        val firstCardBalanceUpdated = dashboardPage.getCurrentBalanceOfFirstCard();
        val secondCardBalanceUpdated = dashboardPage.getCurrentBalanceOfSecondCard();
        val rechargedCardBalance = checkBalanceOfCardRecharged(firstCardBalance, amount);
        val refilledCardBalance = checkBalanceOfCardRefilled(secondCardBalance, amount);
        assertEquals(rechargedCardBalance, firstCardBalanceUpdated);
        assertEquals(refilledCardBalance, secondCardBalanceUpdated);
    }

    @Test
    void shouldTransferExtraLimit() {
        val moneyTransferPage = dashboardPage.clickSecondCardToRefill();
        int amount = 12000;
        moneyTransferPage.moneyTransferCard(getFirstCardInfo(), amount);
        moneyTransferPage.errorTransfer();
    }
}