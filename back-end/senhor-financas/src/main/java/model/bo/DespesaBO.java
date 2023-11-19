package model.bo;

import java.util.ArrayList;
import model.dao.DespesaDAO;
import model.dao.ReceitaDAO;
import model.vo.DespesaVO;
import model.vo.ReceitaVO;

public class DespesaBO {

    public void cadastrarDespesaBO(DespesaVO despesaVO) {
        DespesaDAO despesaDAO = new DespesaDAO();
        despesaDAO.cadastrarDespesaDAO(despesaVO);
    }

    public ArrayList<DespesaVO> consultarTodasDespesasBO() {
        DespesaDAO despesaDAO = new DespesaDAO();
        return despesaDAO.consultarTodasDespesasDAO();
    }

    public DespesaVO consultarDespesaBO(int idDespesa) {
        DespesaDAO despesaDAO = new DespesaDAO();
        return despesaDAO.consultarDespesaDAO(idDespesa);
    }

    public boolean atualizarDespesaBO(DespesaVO despesaVO) {
        DespesaDAO despesaDAO = new DespesaDAO();
        return despesaDAO.atualizarDespesaDAO(despesaVO);
    }

    public boolean excluirDespesaBO(DespesaVO despesaVO) {
        DespesaDAO despesaDAO = new DespesaDAO();
        return despesaDAO.excluirDespesaDAO(despesaVO.getIdDespesa());
    }
}
