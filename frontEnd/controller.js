let token = "";
let user = { username: "", fullname: "", mail: "" };
const storage = window.localStorage;
const host = "http://localhost:8080";
const tokenKey = "utn-web-app-sec-token";

function logueo() {
    //comentar cuando se vaya a usar la app
    //window.location.replace("file:///C:/Users/Jorge_Martinez/Documents/TPSegWeb/LoginForm/mainPage.html");
    //user = { username: "jorgemtnz", fullname: "jorge", mail: "jorge@msn.com" };
    //descomentar cuando se este usando la app

    $.ajax({
        url: `${host}/users/login`,
        type: "POST",
        dataType: "html",
        headers: {
            "Content-Type": "application/json"
        },
        data: JSON.stringify({ "userName": $('#Uname').val(), "password": $('#Pass').val() }),
        cache: false,
        success: function (response) {
            storage.setItem(tokenKey, JSON.parse(response).token);
            window.location.replace("./mainPage.html");
            user.username = $('#Uname').val();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr);
        }
    });
}


function cargarUsuario() {
    document.getElementById("lblBienvenido").innerHTML = "Bienvenido " + user.username;
}

function crearTablaUsuarios() {
    //comentar cuando se use la app
    let data = [];
    //data = [{ username: 'fdigiorgio', fullname: 'Francisco Di Giorgio', mail: 'fdigiorgio@frba.utn.edu.ar', password: 'p123' }, { username: 'jorgemtnz', fullname: 'jorge martinez', mail: 'jorgeemtnz@gmail.com', password: 'p123' }];

    //descomentar cuando se use la app
    JSON.stringify(token)
    $.ajax({
        url: `${host}/users`,
        type: "GET",
        async: false,
        dataType: "html",
        headers: {
            "token": storage.getItem(tokenKey)
        },
        cache: false,
        success: function (response) {
            data = JSON.parse(response);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr);
        }
    });

    const tableData = data.map(function (value) {
        return (
            `<tr>
                    <td>${value.username}</td>
                    <td>${value.fullName}</td>
                    <td>${value.mail}</td>
                </tr>`
        );
    }).join('');
    const tableBody = document.querySelector("#tableBody");
    tableBody.innerHTML = tableData;

}