package model.bo;

import java.util.ArrayList;
import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;

public class UsuarioBO {

    public UsuarioVO cadastrarUsuarioBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
    }

    public ArrayList<UsuarioVO> consultarTodosUsuariosBO() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.consultarTodosUsuariosDAO();
    }

    public UsuarioVO consultarUsuarioBO(int idUsuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.consultarUsuarioDAO(idUsuario);
    }

    public boolean atualizarUsuarioBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.atualizarUsuarioDAO(usuarioVO);
    }

    public boolean excluirUsuarioBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.excluirUsuarioDAO(usuarioVO.getIdUsuario());
    }
}
