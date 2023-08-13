$(document).ready(function () {
    $("#add-transaction-form").on("submit", newTransaction);

    $("#close-add-transaction-window").on("click", function () {
        $("#add-transaction-window").modal("toggle");
    });

    $("#add-transaction-pop-up-button").on("click", function () {
        $("#add-transaction-window").modal("show");
        $("#add-transaction-errors").hide();
    });
});

function newTransaction(event) {
    event.preventDefault();

    const transactionDTO = {
        symbol: $("#symbol").val().toUpperCase(),
        amount: $("#amount").val(),
        price: $("#price").val(),
        buy: $("#buy-radio").prop("checked"),
        portfolioId: chosenPortfolio.id,
        coinId: chosenPortfolio.coins.find(coin => coin.symbol === $("#symbol").val().toUpperCase())?.id || 0
    };

    const apiUrl = "api/transactions/";
    $.ajax({
        url: apiUrl,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(transactionDTO),
    })
        .done(() => {
            $("#add-transaction-window").modal("hide");
            fetchPortfolioData(chosenPortfolio.id);
        })
        .fail(response => {
            const errors = response.responseJSON.errors;
            const errorsField = $("#add-transaction-errors");
            errorsField.show();
            errorsField.text(errors.join("\n"));
        });
}
