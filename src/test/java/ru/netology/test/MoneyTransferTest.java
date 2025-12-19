package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();
        int amount = 2000;
        int firstCardBalanceBefore = dashboardPage.getCardBalance(firstCard);
        int secondCardBalanceBefore = dashboardPage.getCardBalance(secondCard);
        var transferPage = dashboardPage.selectCard(firstCard);
        dashboardPage = transferPage.transferMoney(amount, secondCard);
        int firstCardBalanceAfter = dashboardPage.getCardBalance(firstCard);
        int secondCardBalanceAfter = dashboardPage.getCardBalance(secondCard);

        assertEquals(firstCardBalanceBefore + amount, firstCardBalanceAfter);
        assertEquals(secondCardBalanceBefore - amount, secondCardBalanceAfter);
    }

    @Test
    void shouldShowErrorWhenTransferAmountOverBalance() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();
        int amount = 20_000;
        var transferPage = dashboardPage.selectCard(firstCard);
        transferPage.transferMoney(amount, secondCard);
        transferPage.shouldShowError();
    }
}

