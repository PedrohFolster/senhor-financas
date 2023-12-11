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
        const resultado = await fetch('http://localhost:8080/senhor_financas_war_exploded/rest/usuario/logar', options);
        const usuario = await resultado.json();

        if (usuario.idUsuario !== 0) {
            alert("Login realizado com Sucesso!");

            // Armazene as informações do usuário na sessionStorage
            sessionStorage.setItem('usuario', JSON.stringify(usuario));

            // Obtenha as informações completas do usuário usando o endpoint com base no ID
            const idUsuarioLogado = usuario.idUsuario;
            const infoUsuarioResponse = await fetch(`http://localhost:8080/senhor_financas_war_exploded/rest/usuario/pesquisar/${idUsuarioLogado}`);
            const infoUsuario = await infoUsuarioResponse.json();

            // Faça o que for necessário com as informações completas do usuário
            console.log('Informações do usuário:', infoUsuario);

            // Redirecione para a página principal
            window.location.href="/senhor_financas_war_exploded/principal.html";
        } else {
            alert("Credenciais inválidas. Verifique seu login e senha!");
        }
    } catch (error) {
        console.error("Erro ao realizar o login:", error);
        alert("Houve um problema no processo de login!");
    }

    // Verifica se form não é nulo antes de chamar reset
    if (form) {
        form.reset();
    }
}
