const form = document.querySelector('#formulario');

async function logarUsuario() {
    const options = {
        method: "POST",
        headers: { "Content-type": "application/json" },
        body: JSON.stringify({
            login: document.querySelector('#login').value,
            senha: document.querySelector('#senha').value
        })
    };

    try {
        const resultado = await fetch('http://localhost:8080/senhor-financas/rest/usuario/logar', options);
        const usuario = await resultado.json();

        if (usuario.idUsuario != 0) {
            alert("Login realizado com Sucesso!");
            sessionStorage.setItem('usuario', JSON.stringify(usuario));
            window.location.href="../modules/receita/principal_receita.html";
        } else {
            alert("Credenciais inválidas. Verifique seu login e senha!");
        }
    } catch (error) {
        console.error("Erro ao realizar o login:", error);
        alert("Houve um problema no processo de login!");
    }

    form.reset();
}