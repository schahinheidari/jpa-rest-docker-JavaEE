"use strict";

function greet() {
    document.getElementById('result').innerHTML = '<h1>' + nameJs + '</h1>';
    return false;
}

// document.getElementById('go').addEventListener('click', greet);


function showId(id) {
    console.log("id::::" + id)

    toastr.success(
        "ID: " + id, "Detected", {
            "closeButton": true,
            "debug": false,
            "newestOnTop": true,
            "progressBar": true,
            "positionClass": "toast-top-left",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "2000",
            "hideDuration": "2000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        });


}






function showMessage(message) {
    toastr.error(
        "Message",message, {
            "closeButton": true,
            "debug": false,
            "newestOnTop": true,
            "progressBar": true,
            "positionClass": "toast-top-left",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "2000",
            "hideDuration": "2000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        });


}


function toast3(){
    document.getElementById('myBtn').click(function (event) {
        event.preventDefault();

        $.post("/email", {'email': $('#email').val()}, function () {
            toastr.show({
                title: 'Success',
                message: 'Provided email address is valid',
                position: 'topRight',
                timeout: 500000,
                color: 'green'
            });

        }).fail(function () {
            toastr.show({
                title: 'Fail',
                message: 'This is not a valid email address',
                position: 'topRight',
                color: 'red'
            });
        });
    })
}