package model.bo;

import model.vo.PessoaVO;

import java.util.ArrayList;

public class PessoaBO {

    public PessoaVO cadastrarPessoaBO(PessoaVO pessoaVO) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        if (pessoaDAO.verificarCadastroPessoaBaseDadosDAO(pessoaVO)) {
            System.out.println("\n Pessoa já cadastrada na base de Dados.");
        } else {
            if (pessoaVO.getIdade() >= 18) {
                pessoaVO = pessoaDAO.cadastrarPessoaDAO(pessoaVO);
            } else {
                System.out.println("\nPessoa é menor de idade.");
            }
        }
        return pessoaVO;
    }

    public boolean atualizarPessoaBO(PessoaVO pessoaVO) {
        boolean resultado = false;
        PessoaDAO pessoaDAO = new PessoaDAO();
        if (pessoaDAO.verificarCadastroPessoaBaseDadosDAO(pessoaVO)) {
            if (pessoaVO.getIdade() >= 18) {
                resultado = pessoaDAO.atualizarPessoaDAO(pessoaVO);
            } else {
                System.out.println("\nPessoa é menor de idade.");
            }
        } else {
            System.out.println("\nPessoa não existe na base de dados.");
        }
        return resultado;
    }

    public boolean excluirPessoaBO(PessoaVO pessoaVO) {
        boolean resultado = false;
        PessoaDAO pessoaDAO = new PessoaDAO();
        if (pessoaDAO.verificarCadastroPessoaBaseDadosDAO(pessoaVO)) {
            resultado = pessoaDAO.excluirPessoaDAO(pessoaVO);
        } else {
            System.out.println("\nPessoa não existe na base de dados.");
        }
        return resultado;
    }

    public ArrayList<PessoaVO> consultarTodasPessoasBO() {
        PessoaDAO pessoaDAO = new PessoaDAO();
        ArrayList<PessoaVO> listaPessoasVO = pessoaDAO.consultarTodasPessoasDAO();
        if (listaPessoasVO.isEmpty()) {
            System.out.println("\nA lista de pessoas está vazia.");
        }
        return listaPessoasVO;
    }

    public PessoaVO consultarPessoaBO(PessoaVO pessoaVO) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        PessoaVO pessoa = pessoaDAO.consultarPessoaDAO(pessoaVO);
        if (pessoa == null) {
            System.out.println("\nPessoa não localizada.");
        }
        return pessoa;
    }
}
