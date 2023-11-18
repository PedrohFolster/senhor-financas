package model.dao;

import model.vo.UsuarioVO;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    public boolean verificarCadastroUsuarioBaseDadosDAO(UsuarioVO usuarioVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        boolean retorno = false;
        String query = "SELECT cpf FROM usuario WHERE cpf = '" + usuarioVO.getCpf() + "'";
        try {
            resultado = stmt.executeQuery(query);
            if (resultado.next()) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query de método verificarCadastroUsuarioBaseDadosDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }

    public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) {
        String query = "INSERT INTO usuario (nome, cpf, idade) VALUES (?, ?, ?)";
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        try {
            pstmt.setString(1, usuarioVO.getNome());
            pstmt.setString(2, usuarioVO.getCpf());
            pstmt.setString(3, usuarioVO.getEmail());
            pstmt.setString(4, usuarioVO.getDatanascimento());
            pstmt.setString(5, usuarioVO.getLogin());
            pstmt.setString(6, usuarioVO.getSenha());
            pstmt.execute();
            ResultSet resultado = pstmt.getGeneratedKeys();
            if (resultado.next()) {
                usuarioVO.setIdUsuario(resultado.getInt(1));
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método cadastrarUsuarioDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return usuarioVO;
    }

    public boolean atualizarUsuarioDAO(UsuarioVO usuarioVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        boolean retorno = false;
        String query = "UPDATE usuario SET nome = '" + usuarioVO.getNome() +
                "', cpf = '" + usuarioVO.getCpf() +
                "', email = " + usuarioVO.getEmail() +
                "', datanascimento = " + usuarioVO.getDatanascimento() +
                "', login = " + usuarioVO.getLogin() +
                "', senha = " + usuarioVO.getSenha() +
                " WHERE idusuario = " + usuarioVO.getIdUsuario();
        try {
            if (stmt.executeUpdate(query) == 1) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método atualizarUsuarioDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }

    public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        boolean retorno = false;
        String query = "DELETE FROM usuario WHERE idusuario = " + usuarioVO.getIdUsuario();
        try {
            if (stmt.executeUpdate(query) == 1) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método excluirUsuarioDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }

    public ArrayList<UsuarioVO> consultarTodasUsuariosDAO() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);

        ResultSet resultado = null;
        ArrayList<UsuarioVO> listaUsuarios = new ArrayList<UsuarioVO>();
        String query = "SELECT idusuario, nome, cpf, email, datanascimento, login, senha FROM usuario";

        try {
            resultado = stmt.executeQuery(query);
            while (resultado.next()) {
                UsuarioVO usuario = new UsuarioVO();
                usuario.setIdUsuario(Integer.parseInt(resultado.getString(1)));
                usuario.setNome(resultado.getString(2));
                usuario.setCpf(resultado.getString(3));
                usuario.setEmail(resultado.getString(4));
                usuario.setDatanascimento(resultado.getString(5));
                usuario.setLogin(resultado.getString(6));
                usuario.setSenha(resultado.getString(7));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método consultarTodasUsuariosDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return listaUsuarios;
    }

    public UsuarioVO consultarUsuarioDAO(UsuarioVO usuarioVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);

        ResultSet resultado = null;
        UsuarioVO usuario = new UsuarioVO();
        String query = "SELECT idusuario, nome, cpf, email, datanascimento, login, senha " +
                "FROM usuario " +
                "WHERE idusuario = " + usuarioVO.getIdUsuario();
        try {
            resultado = stmt.executeQuery(query);
            if (resultado.next()) {
                usuario.setIdUsuario(Integer.parseInt(resultado.getString(1)));
                usuario.setNome(resultado.getString(2));
                usuario.setCpf(resultado.getString(3));
                usuario.setEmail(resultado.getString(4));
                usuario.setDatanascimento(resultado.getString(5));
                usuario.setLogin(resultado.getString(6));
                usuario.setSenha(resultado.getString(7));
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método consultarUsuarioDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return usuario;
    }
}
