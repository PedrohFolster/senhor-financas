package model.bo;

import model.dao.DespesaDAO;
import model.vo.DespesaVO;

import java.util.ArrayList;

public class DespesaBO {

    public void cadastrarDespesaBO(DespesaVO despesaVO) {
        DespesaDAO despesaDAO = new DespesaDAO();

        if (despesaDAO.verificarExistenciaDespesaDAO(despesaVO)) {
            System.out.println("Despesa já cadastrada na base de dados.");
        } else {
            despesaDAO.cadastrarDespesaDAO(despesaVO);
        }
    }

    public void atualizarDespesaBO(DespesaVO despesaVO) {
        DespesaDAO despesaDAO = new DespesaDAO();

        if (despesaDAO.verificarExistenciaDespesaDAO(despesaVO)) {
            despesaDAO.atualizarDespesaDAO(despesaVO);
        } else {
            System.out.println("Despesa não encontrada na base de dados.");
        }
    }

    public void excluirDespesaBO(DespesaVO despesaVO) {
        DespesaDAO despesaDAO = new DespesaDAO();

        if (despesaDAO.verificarExistenciaDespesaDAO(despesaVO)) {
            despesaDAO.excluirDespesaDAO(despesaVO);
        } else {
            System.out.println("Despesa não encontrada na base de dados.");
        }
    }

    public ArrayList<DespesaVO> consultarTodasDespesasBO() {
        DespesaDAO despesaDAO = new DespesaDAO();
        ArrayList<DespesaVO> listaDespesas = despesaDAO.consultarTodasDespesasDAO();
        if (listaDespesas.isEmpty()) {
            System.out.println("A lista de despesas está vazia.");
        }
        return listaDespesas;
    }

    public DespesaVO consultarDespesaBO(DespesaVO despesaVO) {
        DespesaDAO despesaDAO = new DespesaDAO();
        DespesaVO despesa = despesaDAO.consultarDespesaDAO(despesaVO);
        if (despesa.getIdDespesa() == 0) {
            System.out.println("Despesa não encontrada.");
        }
        return despesa;
    }
}
