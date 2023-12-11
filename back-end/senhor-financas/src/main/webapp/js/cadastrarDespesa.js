document.addEventListener('DOMContentLoaded', () => {
    const formDespesas = document.getElementById('formDespesas');

    if (formDespesas) {
        formDespesas.addEventListener('submit', async function (event) {
            event.preventDefault();

            const cadastrarBotaoDespesa = document.getElementById('cadastrarBotaoDespesa');

            if (cadastrarBotao) {
                cadastrarBotaoDespesa.disabled = true;

                try {
                    await cadastrarNovaDespesa();
                } finally {
                    cadastrarBotaoDespesa.disabled = false;
                }
            }
        });
    }

    const buscarBotao = document.getElementById('buscarBotao');
    if (buscarBotao) {
        buscarBotao.addEventListener('click', function (event) {
            event.preventDefault();
            atualizarTabelaDespesas();
        });
    }

    // Adiciona um evento de clique ao botão "Cadastrar"
    const cadastrarBotaoDespesa = document.getElementById('cadastrarBotao');
    if (cadastrarBotaoDespesa) {
        cadastrarBotaoDespesa.addEventListener('click', function (event) {
            event.preventDefault(); // Evita o comportamento padrão do formulário
            cadastrarNovaDespesa(); // Chama a função para cadastrar a despesa
        });
    }

    // Adiciona um evento de clique aos botões de editar
    const botoesEditar = document.querySelectorAll('.botao-editar');
    botoesEditar.forEach((botao) => {
        botao.addEventListener('click', function (event) {
            event.preventDefault();

            const linha = event.target.closest('tr');
            const idDespesa = linha.querySelector('td:first-child').innerText;

            window.location.href = `./editarDespesas.html?id=${idDespesa}`;
        });
    });

    atualizarTabelaDespesas();
});

async function atualizarTabelaDespesas() {
    const tabelaDespesasBody = document.getElementById('tbodyDespesas');

    if (!tabelaDespesasBody) {
        console.error('Elemento com ID "tbodyDespesas" não encontrado.');
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/senhor_financas_war_exploded/rest/despesa/listar/1');

        if (!response.ok) {
            throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
        }

        const data = await response.json();

        if (data.length > 0) {
            tabelaDespesasBody.innerHTML = '';

            data.forEach((despesa) => {
                let tr = tabelaDespesasBody.insertRow();

                let td_id = tr.insertCell();
                let td_descricao = tr.insertCell();
                let td_dtVencimento = tr.insertCell();
                let td_dtPagamento = tr.insertCell();
                let td_valor = tr.insertCell();
                let td_acoes = tr.insertCell();

                td_id.innerText = despesa.idDespesa;
                td_descricao.innerText = despesa.descricao;
                td_dtVencimento.innerText = formatarData(despesa.dataVencimento);
                td_dtPagamento.innerText = despesa.dataPagamento ? formatarData(despesa.dataPagamento) : '-';
                td_valor.innerText = formatarValor(despesa.valor);

                let botoesAcoes = document.createElement('div');
                botoesAcoes.className = 'botoes-acoes';

                let botaoEditar = document.createElement('button');
                botaoEditar.type = 'button';
                botaoEditar.className = 'botao-editar';
                botaoEditar.textContent = 'Editar';
                botaoEditar.addEventListener('click', () => editarDespesa(despesa));
                botoesAcoes.appendChild(botaoEditar);

                let botaoExcluir = document.createElement('button');
                botaoExcluir.type = 'button';
                botaoExcluir.className = 'botao-excluir';
                botaoExcluir.textContent = 'Excluir';
                botaoExcluir.addEventListener('click', () => excluirDespesa(despesa));
                botoesAcoes.appendChild(botaoExcluir);

                td_acoes.appendChild(botoesAcoes);
            });
        }
    } catch (error) {
        console.error('Erro ao obter a lista de despesas', error);
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

async function excluirDespesa(despesa) {
    if (!despesa || !despesa.idDespesa) {
        console.error('ID da despesa não encontrado');
        return;
    }

    const idDespesa = despesa.idDespesa;

    let options = {
        method: 'DELETE',
        headers: { 'Content-type': 'application/json' },
    };

    try {
        const response = await fetch(
            `http://localhost:8080/senhor_financas_war_exploded/rest/despesa/deletar`,
            {
                ...options,
                body: JSON.stringify({ idDespesa: idDespesa }),
            }
        );

        if (!response.ok) {
            throw new Error(
                `Erro na resposta do servidor: ${response.status} ${response.statusText}`
            );
        }

        console.log('Despesa excluída com sucesso');
        atualizarTabelaDespesas();
    } catch (error) {
        console.error('Erro ao excluir a despesa', error);
    }
}

async function cadastrarNovaDespesa() {
    const descricaoInput = document.getElementById('descricao');
    const valorInput = document.getElementById('valor');
    const dtvencimentoInput = document.getElementById('dtvencimento');
    const dtpagamentoInput = document.getElementById('dtpagamento');

    if (!descricaoInput || !valorInput || !dtvencimentoInput || !dtpagamentoInput) {
        console.error('Erro: Elemento de input não encontrado.');
        return;
    }

    const novaDespesa = {
        descricao: descricaoInput.value,
        valor: parseFloat(valorInput.value),
        dtvencimento: dtvencimentoInput.value,
        dtpagamento: dtpagamentoInput.value,
        idUsuario: 1
    };

    try {
        const response = await fetch('http://localhost:8080/senhor_financas_war_exploded/rest/despesa/cadastrar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(novaDespesa),
        });

        if (!response.ok) {
            throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
        }

        const data = await response.json();
        console.log('Nova despesa cadastrada com sucesso:', data);

        // Adicione aqui qualquer lógica adicional ou redirecionamento

    } catch (error) {
        console.error('Erro ao cadastrar a nova despesa:', error);
    }
}

async function editarDespesa(despesa) {
    if (!despesa || !despesa.idDespesa) {
        console.error('ID da despesa não encontrado');
        return;
    }

    const idDespesa = despesa.idDespesa;
    const descricao = despesa.descricao;
    const dataVencimento = despesa.dataVencimento;
    const dataPagamento = despesa.dataPagamento;
    const valor = despesa.valor;

    window.location.href = `./editarDespesas.html?id=${idDespesa}&descricao=${descricao}&dataVencimento=${dataVencimento}&dataPagamento=${dataPagamento}&valor=${valor}`;
}
