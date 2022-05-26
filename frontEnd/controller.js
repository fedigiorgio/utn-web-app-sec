let token = "";
let user = { username: "", fullname: "", mail: "" };

function logueo() {
    //comentar cuando se vaya a usar la app
    //window.location.replace("file:///C:/Users/Jorge_Martinez/Documents/TPSegWeb/LoginForm/mainPage.html");
    //user = { username: "jorgemtnz", fullname: "jorge", mail: "jorge@msn.com" };
    //descomentar cuando se este usando la app
    
    $.ajax({
        url: "http://localhost:8080/users/login",
        type: "POST",
        dataType: "html",
        headers: {
            "Content-Type": "application/json"
        },
        data: JSON.stringify({ "userName": $('#Uname').val(), "password": $('#Pass').val() }),
        cache: false,
        success: function(response) {
            token = response;
            console.log(response);
        },
        error: function(xhr, ajaxOptions, thrownError) {
            alert(thrownError);
        }
    });
}


function cargarUsuario() {
    document.getElementById("lblBienvenido").innerHTML = "Bienvenido " + user.fullname;
}

function crearTablaUsuarios() {
    //comentar cuando se use la app
    const data = [{ username: 'fdigiorgio', fullname: 'Francisco Di Giorgio', mail: 'fdigiorgio@frba.utn.edu.ar' }, { username: 'jorgemtnz', fullname: 'jorge martinez', mail: 'jorgeemtnz@gmail.com' }];

    //descomentar cuando se use la app
    /**
     $.ajax({
         //definir la url correcta
        url: "../users?token=" + JSON.stringify(token),
        type: "GET",
        dataType: "html",
        cache: false,
        success: function (response ) {            
            data = response;
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(thrownError);
        }
    });
     */

    const tableData = data.map(function(value) {
        return (
            `<tr>
                    <td>${value.username}</td>
                    <td>${value.fullname}</td>
                    <td>${value.mail}</td>
                </tr>`
        );
    }).join('');
    const tableBody = document.querySelector("#tableBody");
    tableBody.innerHTML = tableData;

}