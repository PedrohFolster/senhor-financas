package model.dao;

import model.vo.PessoaVO;

import java.sql.*;
import java.util.ArrayList;

public class PessoaDAO {

    public boolean verificarCadastroPessoaBaseDadosDAO(PessoaVO pessoaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        boolean retorno = false;
        String query = "SELECT cpf FROM pessoa WHERE cpf = '" + pessoaVO.getCpf() + "'";
        try {
            resultado = stmt.executeQuery(query);
            if (resultado.next()) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query de método verificarCadastroPessoaBaseDadosDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }

    public PessoaVO cadastrarPessoaDAO(PessoaVO pessoaVO) {
        String query = "INSERT INTO pessoa (nome, cpf, idade) VALUES (?, ?, ?)";
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
        try {
            pstmt.setString(1, pessoaVO.getNome());
            pstmt.setString(2, pessoaVO.getCpf());
            pstmt.setInt(3, pessoaVO.getIdade());
            pstmt.execute();
            ResultSet resultado = pstmt.getGeneratedKeys();
            if (resultado.next()) {
                pessoaVO.setIdPessoa(resultado.getInt(1));
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método cadastrarPessoaDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeStatement(pstmt);
            Banco.closeConnection(conn);
        }
        return pessoaVO;
    }

    public boolean atualizarPessoaDAO(PessoaVO pessoaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        boolean retorno = false;
        String query = "UPDATE pessoa SET nome = '" + pessoaVO.getNome() +
                        "', cpf = '" + pessoaVO.getCpf() +
                        "', idade = " + pessoaVO.getIdade() +
                        " WHERE idpessoa = " + pessoaVO.getIdPessoa();
        try {
            if (stmt.executeUpdate(query) == 1) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método atualizarPessoaDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }

    public boolean excluirPessoaDAO(PessoaVO pessoaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        boolean retorno = false;
        String query = "DELETE FROM pessoa WHERE idpessoa = " + pessoaVO.getIdPessoa();
        try {
            if (stmt.executeUpdate(query) == 1) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método excluirPessoaDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }

    public ArrayList<PessoaVO> consultarTodasPessoasDAO() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);

        ResultSet resultado = null;
        ArrayList<PessoaVO> listaPessoas = new ArrayList<PessoaVO>();
        String query = "SELECT idpessoa, nome, cpf, idade FROM pessoa";

        try {
            resultado = stmt.executeQuery(query);
            while (resultado.next()) {
                PessoaVO pessoa = new PessoaVO();
                pessoa.setIdPessoa(Integer.parseInt(resultado.getString(1)));
                pessoa.setNome(resultado.getString(2));
                pessoa.setCpf(resultado.getString(3));
                pessoa.setIdade(resultado.getInt(4));
                listaPessoas.add(pessoa);
            }
        } catch (SQLException erro) {
            System.out.println("\nErro ao executar a query do método consultarTodasPessoasDAO!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return listaPessoas;
    }

}
