document.addEventListener('DOMContentLoaded', () => {
    const formReceitas = document.getElementById('formCadastrarReceita');

    if (formReceitas) {
        formReceitas.addEventListener('submit', function (event) {
            event.preventDefault();

            // Desabilitar o botão antes de enviar a requisição
            const cadastrarBotao = document.getElementById('cadastrarBotao');
            cadastrarBotao.disabled = true;

            cadastrarNovaReceita()
                .finally(() => {
                    // Reabilitar o botão após a conclusão da requisição
                    cadastrarBotao.disabled = false;
                });
        });
    }

    atualizarTabelaReceitas();
});

async function atualizarTabelaReceitas() {
    const tabelaReceitasBody = document.getElementById('tbodyReceitas');

    if (!tabelaReceitasBody) {
        console.error('Elemento com ID "tbodyReceitas" não encontrado.');
        return;
    }

    try {
        const response = await fetch(
            'http://localhost:8080/senhor_financas_war_exploded/rest/receita/listar/1'
        );

        if (!response.ok) {
            throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
        }

        const data = await response.json();

        // Limpar o conteúdo apenas se houver dados
        if (data.length > 0) {
            tabelaReceitasBody.innerHTML = '';

            data.forEach((receita) => {
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
        }
    } catch (error) {
        console.error('Erro ao obter a lista de receitas', error);
    }
}

function formatarData(data) {
    const date = new Date(data);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
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
        method: 'DELETE',
        headers: { 'Content-type': 'application/json' },
    };

    try {
        const response = await fetch(
            `http://localhost:8080/senhor_financas_war_exploded/rest/receita/deletar`,
            {
                ...options,
                body: JSON.stringify({ idReceita: idReceita }),
            }
        );

        if (!response.ok) {
            throw new Error(
                `Erro na resposta do servidor: ${response.status} ${response.statusText}`
            );
        }

        console.log('Receita excluída com sucesso');
        atualizarTabelaReceitas();
    } catch (error) {
        console.error('Erro ao excluir a receita', error);
    }
}

function cadastrarNovaReceita() {
    const descricaoInput = document.getElementById('descricao');
    const dtreceitaInput = document.getElementById('dtreceita');
    const valorInput = document.getElementById('valor');

    if (!descricaoInput || !dtreceitaInput || !valorInput) {
        console.error('Erro: Elemento de input não encontrado.');
        return;
    }

    const novaReceita = {
        descricao: descricaoInput.value,
        dataReceita: new Date(dtreceitaInput.value).toISOString(),
        valor: parseFloat(valorInput.value),
        idUsuario: 1
    };

    console.log('Nova receita:', novaReceita);

    return fetch('http://localhost:8080/senhor_financas_war_exploded/rest/receita/cadastrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(novaReceita),
    })
        .then(async (response) => {
            if (!response.ok) {
                throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
            }
            const data = await response.json();
            console.log('Nova receita cadastrada com sucesso:', data);
            atualizarTabelaReceitas();
        })
        .catch((error) => {
            console.error('Erro ao cadastrar a nova receita:', error);
        });
}
