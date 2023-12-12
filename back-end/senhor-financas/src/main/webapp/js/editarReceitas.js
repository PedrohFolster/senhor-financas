document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const descricao = urlParams.get('descricao');
    const dataReceita = urlParams.get('dataReceita');
    const valor = urlParams.get('valor');

    const descricaoInput = document.getElementById('descricao');
    const dtreceitaInput = document.getElementById('dtreceita');
    const valorInput = document.getElementById('valor');

    if (descricaoInput && dtreceitaInput && valorInput) {
        descricaoInput.value = descricao || '';
        valorInput.value = valor || '';

        // Verifica se há uma data de receita antes de tentar preencher o campo
        if (dataReceita) {
            const formattedDataReceita = formatarData(dataReceita);
            dtreceitaInput.value = formattedDataReceita || '';
        }
    }
});

async function atualizarReceita() {
    const urlParams = new URLSearchParams(window.location.search);
    const idReceita = urlParams.get('id');

    // Verifica se o ID é maior que 0
    if (!idReceita || parseInt(idReceita) <= 0) {
        console.error('ID de receita inválido');
        return;
    }

    const descricao = document.getElementById('descricao').value;
    const dtreceitaInput = document.getElementById('dtreceita');
    const valor = document.getElementById('valor').value;

    const idUsuario = 1; // O ID do usuário associado à receita

    const url = `http://localhost:8080/senhor_financas_war_exploded/rest/receita/atualizar`;

    try {
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                idReceita,
                descricao,
                dataReceita: formatarDataParaJSON(dtreceitaInput.value),
                valor,
                idUsuario,
            }),
        });

        if (!response.ok) {
            throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
        }

        // Exibindo mensagem de alerta
        alert('Receita atualizada com sucesso');

        // Redirecionamento para a página de receitas após a atualização
        window.location.href = './receitas.html';

    } catch (error) {
        console.error('Erro ao atualizar a receita', error);
    }
}

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
