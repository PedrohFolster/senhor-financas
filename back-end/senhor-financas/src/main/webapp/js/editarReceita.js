// editarDespesa.js
document.addEventListener("DOMContentLoaded", function () {
    const botoesEditar = document.querySelectorAll(".botao-editar");

    botoesEditar.forEach(function (botao) {
        botao.addEventListener("click", function (event) {
            event.preventDefault();

            // Captura a linha da tabela
            const linha = event.target.closest("tr");

            // Obtém os dados da linha
            const idDespesa = linha.querySelector("td:first-child").innerText;
            const descricao = linha.querySelector("td:nth-child(2)").innerText;
            const dataVencimento = linha.querySelector("td:nth-child(3)").innerText;
            const dataPagamento = linha.querySelector("td:nth-child(4)").innerText;
            const valor = linha.querySelector("td:nth-child(5)").innerText;

            // Redireciona para a página de edição com os parâmetros na URL
            window.location.href = `./editarDespesas.html?id=${idDespesa}&descricao=${descricao}&dataVencimento=${dataVencimento}&dataPagamento=${dataPagamento}&valor=${valor}`;
        });
    });
});
