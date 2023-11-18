package model.bo;

import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;

import java.util.ArrayList;

public class UsuarioBO {

    public UsuarioVO cadastrarUsuarioBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.verificarCadastroUsuarioBaseDadosDAO(usuarioVO)) {
            System.out.println("\n Usuario já cadastrado na base de Dados.");
        }
        return usuarioVO;
    }

    public boolean atualizarUsuarioBO(UsuarioVO usuarioVO) {
        boolean resultado = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.verificarCadastroUsuarioBaseDadosDAO(usuarioVO)) {
            resultado = usuarioDAO.atualizarUsuarioDAO(usuarioVO);
        }  else {
            System.out.println("\nUsuario não existe na base de dados.");
        }
        return resultado;
    }

    public boolean excluirUsuarioBO(UsuarioVO usuarioVO) {
        boolean resultado = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.verificarCadastroUsuarioBaseDadosDAO(usuarioVO)) {
            resultado = usuarioDAO.excluirUsuarioDAO(usuarioVO);
        } else {
            System.out.println("\nUsuario não existe na base de dados.");
        }
        return resultado;
    }

    public ArrayList<UsuarioVO> consultarTodasUsuariosBO() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ArrayList<UsuarioVO> listaUsuariosVO = usuarioDAO.consultarTodasUsuariosDAO();
        if (listaUsuariosVO.isEmpty()) {
            System.out.println("\nA lista de usuarios está vazia.");
        }
        return listaUsuariosVO;
    }

    public UsuarioVO consultarUsuarioBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioVO usuario = usuarioDAO.consultarUsuarioDAO(usuarioVO);
        if (usuario == null) {
            System.out.println("\nUsuario não localizado.");
        }
        return usuario;
    }
}
