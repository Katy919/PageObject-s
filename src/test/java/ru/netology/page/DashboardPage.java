package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement header = $("[data-test-id=dashboard]");

    public DashboardPage() {
        header.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    private SelenideElement getCardElement(DataHelper.CardInfo cardInfo) {
        return cards.find(Condition.attribute("data-test-id", cardInfo.getId()));
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = getCardElement(cardInfo).getText();
        return extractBalance(text);
    }

    public TransferPage selectCard(DataHelper.CardInfo cardInfo) {
        getCardElement(cardInfo).$("button")
                .click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}



