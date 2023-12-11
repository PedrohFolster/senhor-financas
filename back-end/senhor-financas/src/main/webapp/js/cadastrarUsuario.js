async function cadastrarPessoa() {
    let form = document.getElementById('cadastroForm');

    let options = {
        method: "POST",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify({
            id: 0,
            nome: document.querySelector('#nome').value,
            cpf: document.querySelector('#cpf').value,
            email: document.querySelector('#email').value,
            dataNascimento: document.querySelector('#dtnascimento').value,
            login: document.querySelector('#login').value,
            senha: document.querySelector('#senha').value
        })
    };

    const resultado = await fetch('http://localhost:8080/senhor_financas_war_exploded/rest/usuario/cadastrar', options);
    const pessoa = await resultado.json();

    if (pessoa.idUsuario !== 0) {
        alert("Cadastro realizado com sucesso.");
        window.location.href = '/senhor_financas_war_exploded/';
    } else {
        alert("Houve um problema no cadastro da pessoa.");
    }

    form.reset();
}

document.getElementById('cadastroForm').addEventListener('submit', function (event) {
    event.preventDefault();
    cadastrarPessoa();
});
