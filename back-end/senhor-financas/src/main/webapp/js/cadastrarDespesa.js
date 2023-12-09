function formatDateTime(date) {
    const year = date.getUTCFullYear();
    const month = String(date.getUTCMonth() + 1).padStart(2, '0');
    const day = String(date.getUTCDate()).padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}T00:00`;
    return formattedDate;
}

function cadastrarDespesa() {
    const descricao = document.getElementById('descricao').value;
    const valor = parseFloat(document.getElementById('valor').value);
    const dataVencimentoInput = document.getElementById('dtvencimento');
    const dataPagamentoInput = document.getElementById('dtpagamento');

    // Get the date values and format them as datetime strings
    const dataVencimento = new Date(dataVencimentoInput.value);
    const dataPagamento = new Date(dataPagamentoInput.value);

    const dataVencimentoUTC = new Date(Date.UTC(
        dataVencimento.getUTCFullYear(),
        dataVencimento.getUTCMonth(),
        dataVencimento.getUTCDate(),
        dataVencimento.getUTCHours(),
        dataVencimento.getUTCMinutes(),
        dataVencimento.getUTCSeconds()
    ));

    const dataPagamentoUTC = new Date(Date.UTC(
        dataPagamento.getUTCFullYear(),
        dataPagamento.getUTCMonth(),
        dataPagamento.getUTCDate(),
        dataPagamento.getUTCHours(),
        dataPagamento.getUTCMinutes(),
        dataPagamento.getUTCSeconds()
    ));

    const formattedDataVencimento = formatDateTime(dataVencimentoUTC);
    const formattedDataPagamento = formatDateTime(dataPagamentoUTC);

    const despesa = {
        descricao: descricao,
        valor: valor,
        dataVencimento: formattedDataVencimento,
        dataPagamento: formattedDataPagamento,
        idUsuario: 1,
    };

    // Substitua a URL abaixo pela sua URL de endpoint para cadastrar despesas
    fetch('http://localhost:8080/senhor_financas_war_exploded/rest/despesa/cadastrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(despesa),
    })
        .then(response => response.json())
        .then(data => {
            console.log('Despesa cadastrada com sucesso:', data);
            // Lógica adicional, se necessário
        })
        .catch((error) => {
            console.error('Erro ao cadastrar despesa:', error);
            // Lógica adicional em caso de erro
        });
}

// Adicione este código para prevenir o envio do formulário padrão
document.querySelector('form').addEventListener('submit', function (event) {
    event.preventDefault();
    cadastrarDespesa();
});
