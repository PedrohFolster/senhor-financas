document.addEventListener('DOMContentLoaded', () => {
    const formDespesas = document.getElementById('formDespesas');

    if (formDespesas) {
        formDespesas.addEventListener('submit', async function (event) {
            event.preventDefault();

            const cadastrarBotao = document.getElementById('cadastrarBotao');

            if (cadastrarBotao) {
                cadastrarBotao.disabled = true;

                try {
                    await cadastrarNovaDespesa();
                } finally {
                    cadastrarBotao.disabled = false;
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
    const cadastrarBotao = document.getElementById('cadastrarBotao');
    if (cadastrarBotao) {
        cadastrarBotao.addEventListener('click', function (event) {
            event.preventDefault(); // Evita o comportamento padrão do formulário
            cadastrarNovaDespesa(); // Chama a função para cadastrar a despesa
        });
    }

    // Adiciona um evento de clique aos botões de editar
    const botoesEditar = document.querySelectorAll('.botao-editar');
    botoesEditar.forEach((botao) => {
        botao.addEventListener('click', function (event) {
            event.preventDefault();

            // Encontrar a linha da tabela
            const linha = event.target.closest('tr');

            // Certifique-se de que a linha existe
            if (linha) {
                // Encontrar o elemento com o ID na primeira célula da linha
                const idDespesaElement = linha.querySelector('td[data-id]');

                // Certifique-se de que o elemento com o ID existe
                if (idDespesaElement) {
                    const idDespesa = idDespesaElement.dataset.id;

                    // Redirecionar para a página de edição com o ID
                    console.log('ID da Despesa:', idDespesa);
                    window.location.href = `./editarDespesa.html?id=${idDespesa}`;
                } else {
                    console.error('Elemento de ID não encontrado na linha da tabela.');
                }
            } else {
                console.error('Linha da tabela não encontrada.');
            }
        });
    });

    atualizarTabelaDespesas();
});

function limparTabela() {
    // Tenta encontrar o corpo da tabela
    const tbody = document.getElementById('tbodyDespesas');

    // Certifica-se de que o corpo da tabela foi encontrado
    if (tbody) {
        // Limpa o conteúdo do corpo da tabela
        tbody.innerHTML = '';

        // Zera o valor total
        const totalDespesasElement = document.getElementById('totalDespesas');
        if (totalDespesasElement) {
            totalDespesasElement.textContent = '0.00';
        } else {
            console.error('Elemento de totalDespesas não encontrado.');
        }
    } else {
        console.error('Corpo da tabela não encontrado.');
    }
}

async function atualizarTabelaDespesas() {
    const tabelaDespesasBody = document.getElementById('tbodyDespesas');
    const totalDespesasElement = document.getElementById('totalDespesas');

    if (!tabelaDespesasBody || !totalDespesasElement) {
        console.error('Elemento com ID "tbodyDespesas" ou "totalDespesas" não encontrado.');
        return;
    }

    try {
        // Recupera o ID do usuário logado da sessionStorage
        const idUsuarioLogado = parseInt(sessionStorage.getItem('idUsuarioLogado'), 10);

        if (isNaN(idUsuarioLogado)) {
            console.error('ID do usuário logado não encontrado ou inválido.');
            return;
        }

        const response = await fetch(
            `http://localhost:8080/senhor_financas_war_exploded/rest/despesa/listar/${idUsuarioLogado}`
        );

        if (!response.ok) {
            throw new Error(`Erro na resposta do servidor: ${response.status} ${response.statusText}`);
        }

        const data = await response.json();
        let totalDespesas = 0; // Variável para armazenar o total

        // Limpar o conteúdo apenas se houver dados
        if (data.length > 0) {
            tabelaDespesasBody.innerHTML = '';

            data.forEach((despesa) => {
                let tr = tabelaDespesasBody.insertRow();

                let td_id = tr.insertCell();
                let td_descricao = tr.insertCell();
                let td_dataVencimento = tr.insertCell();
                let td_dataPagamento = tr.insertCell();
                let td_valor = tr.insertCell();
                let td_acoes = tr.insertCell();

                td_id.innerText = despesa.idDespesa;
                td_descricao.innerText = despesa.descricao;
                td_dataVencimento.innerText = formatarDataParaJSON(despesa.dataVencimento);
                td_dataPagamento.innerText = despesa.dataPagamento ? formatarDataParaJSON(despesa.dataPagamento) : '-';
                td_valor.innerText = formatarValor(despesa.valor);

                // Adiciona o valor ao total
                totalDespesas += despesa.valor;

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

            // Atualiza o texto do total
            totalDespesasElement.innerText = formatarValor(totalDespesas);
        } else {
            // Caso não haja dados, limpe o total
            totalDespesasElement.innerText = formatarValor(0);
        }
    } catch (error) {
        console.error('Erro ao obter a lista de despesas', error);
    }
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
        await atualizarTabelaDespesas();
        updateTotalDespesas();

        // Mostrar mensagem de sucesso com alert
        mostrarMensagem('Despesa excluída com sucesso', 'success');
    } catch (error) {
        console.error('Erro ao excluir a despesa', error);

        // Mostrar mensagem de erro com alert
        mostrarMensagem('Erro ao excluir a despesa', 'error');
    }
}

function mostrarMensagem(mensagem, tipo) {
    if (tipo === 'success') {
        alert(`SUCESSO: ${mensagem}`);
    } else {
        alert(`ERRO: ${mensagem}`);
    }
}


async function cadastrarNovaDespesa() {
    const descricaoInput = document.getElementById('descricao');
    const valorInput = document.getElementById('valor');
    const dataVencimentoInput = document.getElementById('dataVencimento');
    const dataPagamentoInput = document.getElementById('dataPagamento');

    if (!descricaoInput || !valorInput || !dataVencimentoInput) {
        console.error('Erro: Elemento de input não encontrado.');
        return;
    }

    // Recupera o ID do usuário logado da sessionStorage
    const idUsuarioLogado = parseInt(sessionStorage.getItem('idUsuarioLogado'), 10);

    if (isNaN(idUsuarioLogado)) {
        console.error('ID do usuário logado não encontrado ou inválido.');
        return;
    }

    const novaDespesa = {
        descricao: descricaoInput.value,
        valor: parseFloat(valorInput.value),
        dataVencimento: formatarDataParaJSON(dataVencimentoInput.value),
        dataPagamento: dataPagamentoInput && dataPagamentoInput.value.trim() ? formatarDataParaJSON(dataPagamentoInput.value.trim()) : null,
        idUsuario: idUsuarioLogado
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

        // Mostrar mensagem de sucesso com alert
        mostrarMensagem('Despesa cadastrada com sucesso', 'success');

        // Redirecionar para a página de despesas
        window.location.href = './despesas.html';

    } catch (error) {
        console.error('Erro ao cadastrar a nova despesa:', error);

        // Mostrar mensagem de erro com alert
        mostrarMensagem('Erro ao cadastrar a despesa', 'error');
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