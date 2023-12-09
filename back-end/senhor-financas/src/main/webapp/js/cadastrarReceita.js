document.addEventListener('DOMContentLoaded', () => {
    atualizarTabelaReceitas();
});

async function atualizarTabelaReceitas() {
    const tabelaReceitasBody = document.getElementById('tabelaReceitasBody');

    if (!tabelaReceitasBody) {
        console.error('Elemento com ID "tabelaReceitasBody" não encontrado.');
        return;
    }

    let options = {
        method: "GET",
        headers: { "Content-type": "application/json" }
    };

    try {
        const response = await fetch('http://localhost:8080/senhor_financas_war_exploded/rest/receita/listar/1', options);

        if (!response.ok) {
            throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
        }

        const data = await response.json();

        tabelaReceitasBody.innerHTML = '';

        data.forEach(receita => {
            let tr = tabelaReceitasBody.insertRow();

            let td_id = tr.insertCell();
            let td_descricao = tr.insertCell();
            let td_data = tr.insertCell();
            let td_valor = tr.insertCell();
            let td_acoes = tr.insertCell();

            td_id.innerText = receita.idReceita;
            td_descricao.innerText = receita.descricao;
            td_data.innerText = formatarData(receita.dataReceita);
            td_valor.innerText = formatarValor(receita.valor);

            let botoesAcoes = document.createElement('div');
            botoesAcoes.className = 'botoes-acoes';

            let botaoEditar = document.createElement('button');
            botaoEditar.type = 'button';
            botaoEditar.className = 'botao-editar';
            botaoEditar.textContent = 'Editar';
            botaoEditar.addEventListener('click', () => editarReceita(receita));
            botoesAcoes.appendChild(botaoEditar);

            let botaoExcluir = document.createElement('button');
            botaoExcluir.type = 'button';
            botaoExcluir.className = 'botao-excluir';
            botaoExcluir.textContent = 'Excluir';
            botaoExcluir.addEventListener('click', () => excluirReceita(receita));
            botoesAcoes.appendChild(botaoExcluir);

            td_acoes.appendChild(botoesAcoes);
        });
    } catch (error) {
        console.error('Erro ao obter a lista de receitas', error);
    }
}

function formatarData(data) {
    const date = new Date(data);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

function formatarValor(valor) {
    return valor.toFixed(2);
}

function editarReceita(receita) {
    console.log('Editar receita:', receita);
    // Implemente a lógica de edição da receita
}

async function excluirReceita(receita) {
    if (!receita || !receita.idReceita) {
        console.error('ID da receita não encontrado');
        return;
    }

    const idReceita = receita.idReceita;

    let options = {
        method: "DELETE",
        headers: { "Content-type": "application/json" },
    };

    try {
        const response = await fetch(`http://localhost:8080/senhor_financas_war_exploded/rest/receita/deletar`, {
            ...options,
            body: JSON.stringify({ "idReceita": idReceita })
        });

        if (!response.ok) {
            throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
        }

        console.log('Receita excluída com sucesso');
        atualizarTabelaReceitas();
    } catch (error) {
        console.error('Erro ao excluir a receita', error);
    }
}
