$(document).ready(function () {
    fetchWatchlistData();
});

function fetchWatchlistData() {
    $.ajax({
        url: `/api/watchlist`,
        method: "GET",
        success: function (data) {
            displayWatchlist(data);
        }
    });

    setTimeout(fetchWatchlistData, 5000);
}

function createDeleteButton(watchlistItemId) {
    const deleteButton = $("<button>", {
        id: `deleteWatchlistItemButton${watchlistItemId}`,
    }).css({
        border: "none",
        backgroundColor: "background-color",
        cursor: "pointer"
    }).html(`<img src="/img/delete-icon.svg"/>`);

    deleteButton.on("click", function () {
        handleDeleteWatchlistItem(watchlistItemId);
    });

    return deleteButton;
}

function displayWatchlist(watchlistData) {
    const watchlistTable = $("#watchlist-details");
    watchlistTable.empty();

    if (watchlistData.length > 0) {
        $("#watchlist-part-open").show();
        $("#watchlist-part-closed").hide();

        watchlistData.forEach((watchlistItem) => {
            const button = createDeleteButton(watchlistItem.id);

            const row = $("<tr>");
            row.html(`
                <td>${watchlistItem.symbol}</td>
                <td>${watchlistItem.price} USD</td>
                <td>${watchlistItem.changeRate}%</td>
                <td>${watchlistItem.changePrice} USD</td>
                <td>${watchlistItem.high} USD</td>
                <td>${watchlistItem.low} USD</td>
                <td>${watchlistItem.vol} ${watchlistItem.symbol}</td>
                <td>${watchlistItem.volValue} USD</td>
            `);

            row.append(button);
            watchlistTable.append(row);
        });
    } else {
        $("#watchlist-part-open").hide();
        $("#watchlist-part-closed").show();
    }
}

function handleDeleteWatchlistItem(watchlistItemId) {
    const apiUrl = `api/watchlist/${watchlistItemId}`;
    $.ajax({
        url: apiUrl,
        method: "DELETE",
    })
        .done(() => {
            fetchWatchlistData();
        });
}