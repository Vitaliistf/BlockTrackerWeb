$(document).ready(function () {
    $("#close-transactions-window").on("click", function () {
        $("#transactions-window").modal("toggle");
    });
});

function fetchTransactionData(coinId) {
    $.ajax({
        url: `/api/transactions/by-coin/${coinId}`,
        method: "GET",
        success: function (data) {
            displayTransactions(data);
        }
    });
}

function displayTransactions(transactionData) {
    const transactionTable = $("#transaction-table");
    transactionTable.empty();

    $.each(transactionData, function (index, transaction) {
        const row = $("<tr>");
        row.html(`
            <td>${transaction.buy ? "Buy" : "Sell"}</td>
            <td>${transaction.amount} ${transaction.symbol}</td>
            <td>${transaction.price} USD</td>
            <td>${transaction.price * transaction.amount} USD</td>
            <td>${transaction.date}</td>
        `);
        transactionTable.append(row);

        row.append(createDeleteTransactionButton(transaction.id));
    });
}

function createDeleteTransactionButton(transactionId) {
    const deleteButton = $("<button>", {
        id: `deleteTransactionButton${transactionId}`,
    }).css({
        border: "none",
        backgroundColor: "background-color",
        cursor: "pointer"
    }).html(`<img src="/img/delete-icon.svg"/>`);

    deleteButton.on("click", function () {
        deleteTransaction(transactionId);
    });

    return deleteButton;
}

function deleteTransaction(transactionId) {
    const apiUrl = `api/transactions/${transactionId}`;
    $.ajax({
        url: apiUrl,
        method: "DELETE",
    })
        .done(() => {
            $("#transactions-window").modal("hide");
            fetchPortfolioData(chosenPortfolio.id);
        });
}
