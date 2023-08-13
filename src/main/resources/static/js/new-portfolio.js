$(document).ready(function () {
    $("#new-portfolio-pop-up-button").on("click", function () {
        $("#new-portfolio-window").modal("show");
        $("#new-portfolio-errors").hide();
    });

    $("#close-new-portfolio-window").on("click", function () {
        $("#new-portfolio-window").modal("toggle");
    });

    $("#new-portfolio-form").on("submit", newPortfolio);
});

function newPortfolio(event) {
    event.preventDefault();
    const name = $("#new-portfolio-name").val();

    const apiUrl = "api/portfolios/";
    $.ajax({
        url: apiUrl,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({name}),
    })
        .done(() => {
            $("#new-portfolio-window").modal("hide");
            populatePortfolioList();
        })
        .fail(response => {
            const errors = response.responseJSON.errors;
            let errorsField = $("#new-portfolio-errors");
            errorsField.show();
            errorsField.text(errors.join("\n"));
        });
}