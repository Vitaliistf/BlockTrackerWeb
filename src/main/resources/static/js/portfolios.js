$(document).ready(function () {
    populatePortfolioList();
});

let chosenPortfolio = null;

function populatePortfolioList() {
    const portfolioList = $("#portfolio-list");
    portfolioList.empty();
    $.ajax({
        url: "/api/portfolios/",
        method: "GET",
        success: function (portfolios) {

            portfolios.forEach((portfolio) => {
                const listItem = $("<li>", {
                    class: "list-group-item",
                    text: portfolio.name
                });
                listItem.on("click", function () {
                    chosenPortfolio = portfolio;
                    fetchPortfolioData();
                });
                portfolioList.append(listItem);
            });
        }
    });
}

function fetchPortfolioData() {
    $.ajax({
        url: `/api/coins/by-portfolio/${chosenPortfolio.id}`,
        method: "GET",
        success: function (data) {
            chosenPortfolio.coins = data;
            displayPortfolio(data);
            calculateTotalValue(data);
        }
    });
    setTimeout(fetchPortfolioData, 5000);
}

function displayPortfolio(coins) {

    $("#portfolio-part-open").show();
    $("#portfolio-part-closed").hide();

    selectPortfolio(chosenPortfolio.name);

    const portfolioDetails = $("#portfolio-details");
    portfolioDetails.empty();

    if (coins.length > 0) {
        $("#coin-part-open").show();
        $("#coin-part-closed").hide();

        coins.forEach((coin) => {
            const row = $("<tr>");
            row.html(`
              <td>${coin.symbol}</td>
              <td>${coin.price} USD</td>
              <td>${coin.avgBuyingPrice} USD</td>
              <td>${coin.amount} ${coin.symbol}</td>
              <td>${coin.price * coin.amount} USD</td>
              <td>${(coin.price - coin.avgBuyingPrice) * coin.amount} USD</td>
            `);

            row.on("click", function () {
                $("#transactions-window").modal("show");
                fetchTransactionData(coin.id);
            });

            row.append(createDeleteCoinButton(coin.id));

            portfolioDetails.append(row);
        });
    } else {
        $("#coin-part-open").hide();
        $("#coin-part-closed").show();
    }
}

function createDeleteCoinButton(coinId) {
    const deleteButton = $("<button>", {
        id: `deleteTransactionButton${coinId}`,
    }).css({
        border: "none",
        backgroundColor: "background-color",
        cursor: "pointer"
    }).html(`<img src="/img/delete-icon.svg"/>`);

    deleteButton.on("click", function (e) {
        e.stopPropagation();
        deleteCoin(coinId);
    });

    return deleteButton;
}

function deleteCoin(coinId) {
    const apiUrl = `api/coins/${coinId}`;
    $.ajax({
        url: apiUrl,
        method: "DELETE",
    })
        .done(() => {
            fetchPortfolioData(chosenPortfolio.id);
        });
}

function selectPortfolio(portfolioName) {
    $("#portfolio-list li").removeClass("active");
    const portfolioToSelect = $("#portfolio-list li").filter(function () {
        return $(this).text() === portfolioName;
    });
    portfolioToSelect.addClass("active");
}

function calculateTotalValue(coins) {
    const totalValueElement = $("#total-value");
    const totalValue = coins.reduce((total, coin) => total + coin.price * coin.amount, 0);
    totalValueElement.text(`Balance: $${totalValue}`);
}