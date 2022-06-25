let token = "";
let user = { username: "", fullname: "", mail: "" };
const storage = window.localStorage;
const host = "http://localhost:8080";
const tokenKey = "utn-web-app-sec-token";
const userKey = "usuario";

function logueo() {
    //comentar cuando se vaya a usar la app, es solo para usar sin base de datos
    // storage.setItem(tokenKey, "fgdfhbdgsjgcsjsnmmsrkk");
    // storage.setItem(userKey, $('#Uname').val());
    // window.location.replace("./index.html");

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
        success: function(response) {
            storage.setItem(tokenKey, JSON.parse(response).token);
            storage.setItem(userKey, $('#Uname').val());
            window.location.replace("./index.html");
        },
        error: function() {
            alert("Error contraseña inválida");
        }
    });
}


function cargarUsuario() {
    document.getElementById("lblBienvenido").innerHTML = "Bienvenido " + storage.getItem(userKey);
}

function crearTabla(data) {
    const tableData = data.map(function(value) {
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

function getUsuarios(url) {
    let data = [];
    //comentar cuando se use la app, es solo para usar sin base de datos
    // data = [{ username: 'fdigiorgio', fullname: 'Francisco Di Giorgio', mail: 'fdigiorgio@frba.utn.edu.ar' }, { username: 'jorgemtnz', fullname: 'jorge martinez', mail: 'jorgeemtnz@gmail.com' }];
    // alert(url);

    $.ajax({
        url: url,
        type: "GET",
        async: false,
        dataType: "html",
        headers: {
            "token": storage.getItem(tokenKey)
        },
        cache: false,
        success: function(response) {
            data = JSON.parse(response);
        },
        error: function() {
            alert("Error al buscar usuarios");
        }
    });

    crearTabla(data);
}

function buscarUsuario() {
    const url = `${host}/users?fullName=${$('#Uname').val()}`;
    getUsuarios(url);
}

function buscarUsuarios() {
    const url = `${host}/users?fullName`;
    getUsuarios(url);
}

function cerrarSesion() {
    window.localStorage.removeItem(userKey);
    window.localStorage.removeItem(tokenKey);
    window.localStorage.clear();
    alert("Adios");
    // return true or false, depending on whether you want to allow the `href` property to follow through or not
}

function validarSesion() {
    let usuario = storage.getItem(userKey);
    let token = storage.getItem(tokenKey);

    if (usuario == null || token == null) {
        alert("sesion Invalida");
        window.localStorage.clear();
        window.location.replace("./login.html");
    }
}