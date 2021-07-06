package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement amountField = $("[data-test-id='amount'] .input__control");
    private SelenideElement fromField = $("[data-test-id='from'] .input__control");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public MoneyTransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage moneyTransferCard(DataHelper.CardInfo fromCardInfo, int amount) {
        amountField.doubleClick();
        amountField.setValue(String.valueOf(amount));
        fromField.doubleClick();
        fromField.setValue(fromCardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void errorTransfer() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка!"));
    }
}
