$(document).ready(function () {
    $("form#form-login").submit((e) => {
        e.preventDefault();
        $("#error-username").html("");

        setTimeout(() => {
            if ($("#input-username").val() != "zyot249")
                $("#error-username").html("Incorrect Username, try again?");
            else
                window.location.href = "./homepage.html";
        }, 100);
    });
});