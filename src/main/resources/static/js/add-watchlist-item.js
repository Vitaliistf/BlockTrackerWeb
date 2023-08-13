$(document).ready(function () {
    $("#add-watchlist-item-pop-up-button").on("click", function () {
        $("#add-watchlist-item-window").modal("show");
        $("#add-watchlist-item-errors").hide();
    });

    $("#close-add-watchlist-item-window").on("click", function () {
        $("#add-watchlist-item-window").modal("toggle");
    });

    $("#add-watchlist-item-form").on("submit", addWatchlistItem);
});

function addWatchlistItem(event) {
    event.preventDefault();
    const symbol = $("#watchlist-item-symbol").val().toUpperCase();

    const apiUrl = "api/watchlist/";
    $.ajax({
        url: apiUrl,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({symbol}),
    })
        .done(() => {
            $("#add-watchlist-item-window").modal("hide");
            fetchWatchlistData();
        })
        .fail(response => {
            const errors = response.responseJSON.errors;
            let errorsField = $("#add-watchlist-item-errors");
            errorsField.show();
            errorsField.text(errors.join("\n"));
        });
}

