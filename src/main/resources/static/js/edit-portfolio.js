$(document).ready(function () {
    $("#delete-portfolio-button").on("click", deletePortfolio);

    $("#edit-portfolio-form").on("submit", editPortfolio);

    $("#close-edit-portfolio-window").on("click", function () {
        $("#edit-portfolio-window").modal("toggle");
    });

    $("#edit-portfolio-pop-up-button").on("click", function () {
        $("#edit-portfolio-window").modal("show");
        $("#edit-portfolio-errors").hide();
    });
});

function deletePortfolio(event) {
    event.preventDefault();

    $("#portfolio-part-open").hide();
    $("#portfolio-part-closed").show();

    const apiUrl = `api/portfolios/${chosenPortfolio.id}`;
    $.ajax({
        url: apiUrl,
        method: "DELETE",
    })
        .done(() => {
            $("#edit-portfolio-window").modal("hide");
            populatePortfolioList();
        });
}

function editPortfolio(event) {
    event.preventDefault();

    $("#portfolio-part-open").hide();
    $("#portfolio-part-closed").show();

    chosenPortfolio.name = $("#edit-portfolio-name").val();

    const apiUrl = `api/portfolios/${chosenPortfolio.id}`;
    $.ajax({
        url: apiUrl,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(chosenPortfolio),
    })
        .done(() => {
            $("#edit-portfolio-window").modal("hide");
            populatePortfolioList();
        })
        .fail(response => {
            const errors = response.responseJSON.errors;
            let errorsField = $("#edit-portfolio-errors");
            errorsField.show();
            errorsField.text(errors.join("\n"));
        });
}
