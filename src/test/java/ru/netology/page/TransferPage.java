package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;


public class TransferPage {

    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromCardField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id = error-notification]");

    public TransferPage() {
        amountField.shouldBe(visible, Duration.ofSeconds(15));
        fromCardField.shouldBe(visible, Duration.ofSeconds(15));
        transferButton.shouldBe(visible, Duration.ofSeconds(15));
    }

    public DashboardPage transferMoney(int amount, DataHelper.CardInfo fromCard) {
        amountField.setValue(String.valueOf(amount));
        fromCardField.setValue(fromCard.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void shouldShowError() {
        errorNotification.shouldBe(visible)
                .shouldHave(text("Ошибка!"));
    }
}