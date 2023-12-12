document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const descricao = urlParams.get('descricao');
    const dataVencimento = urlParams.get('dataVencimento');
    const dataPagamento = urlParams.get('dataPagamento');
    const valor = urlParams.get('valor');

    const descricaoInput = document.getElementById('descricao');
    const dtvencimentoInput = document.getElementById('dtvencimento');
    const dtpagamentoInput = document.getElementById('dtpagamento');
    const valorInput = document.getElementById('valor');

    if (descricaoInput && dtvencimentoInput && dtpagamentoInput && valorInput) {
        descricaoInput.value = descricao || '';
        valorInput.value = valor || '';

        // Verifica se há uma data de vencimento antes de tentar preencher o campo
        if (dataVencimento) {
            const formattedDataVencimento = formatarData(dataVencimento);
            dtvencimentoInput.value = formattedDataVencimento || '';
        }

        // Verifica se há uma data de pagamento antes de tentar preencher o campo
        if (dataPagamento) {
            const formattedDataPagamento = formatarData(dataPagamento);
            dtpagamentoInput.value = formattedDataPagamento || '';
        }
    }
});

function formatarData(data) {
    if (!data) {
        return ''; // Retorna uma string vazia se a data for nula ou indefinida
    }

    const date = new Date(data);
    if (isNaN(date.getTime())) {
        return ''; // Retorna uma string vazia se a data não for válida
    }

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

function formatarDataParaJSON(data) {
    const date = new Date(data);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
}

async function atualizarDespesa() {
    const urlParams = new URLSearchParams(window.location.search);
    const idDespesa = urlParams.get('id');

    // Verifica se o ID é maior que 0
    if (!idDespesa || parseInt(idDespesa) <= 0) {
        console.error('ID de despesa inválido');
        return;
    }

    const descricao = document.getElementById('descricao').value;
    const dataVencimentoInput = document.getElementById('dtvencimento');
    const dataPagamentoInput = document.getElementById('dtpagamento');
    const valor = document.getElementById('valor').value;

    const idUsuario = 1; // O ID do usuário associado à despesa

    const url = `http://localhost:8080/senhor_financas_war_exploded/rest/despesa/atualizar`;

    try {
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                idDespesa,
                descricao,
                dataVencimento: formatarDataParaJSON(dataVencimentoInput.value),
                dataPagamento: formatarDataParaJSON(dataPagamentoInput.value),
                valor,
                idUsuario,
            }),
        });

        if (!response.ok) {
            throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
        }

        // Exibindo mensagem de alerta
        alert('Despesa atualizada com sucesso');

        // Redirecionamento para a página de despesas após a atualização
        window.location.href = './despesas.html';

    } catch (error) {
        console.error('Erro ao atualizar a despesa', error);
    }
}