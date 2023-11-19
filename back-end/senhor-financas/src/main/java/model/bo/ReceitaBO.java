package model.bo;

import model.dao.ReceitaDAO;
import model.vo.ReceitaVO;

import java.util.ArrayList;

public class ReceitaBO {

    public ReceitaVO cadastrarReceitaBO(ReceitaVO receitaVO) {
        ReceitaDAO receitaDAO = new ReceitaDAO();
        receitaDAO.cadastrarReceitaDAO(receitaVO);
        return receitaVO;
    }

    public boolean atualizarReceitaBO(ReceitaVO receitaVO) {
        boolean resultado = false;
        ReceitaDAO receitaDAO = new ReceitaDAO();

        if (receitaDAO.verificarExistenciaReceitaDAO(receitaVO)) {
            resultado = receitaDAO.atualizarReceitaDAO(receitaVO);
        }

        return resultado;
    }

    public boolean excluirReceitaBO(ReceitaVO receitaVO) {
        boolean resultado = false;
        ReceitaDAO receitaDAO = new ReceitaDAO();

        if (receitaDAO.verificarExistenciaReceitaDAO(receitaVO)) {
            resultado = receitaDAO.excluirReceitaDAO(receitaVO);
        }

        return resultado;
    }

    public ArrayList<ReceitaVO> consultarTodasReceitasBO() {
        ReceitaDAO receitaDAO = new ReceitaDAO();
        return receitaDAO.consultarTodasReceitasDAO();
    }

    public ReceitaVO consultarReceitaBO(ReceitaVO receitaVO) {
        ReceitaDAO receitaDAO = new ReceitaDAO();
        return receitaDAO.consultarReceitaDAO(receitaVO);
    }
}
