package model.dao;

import model.vo.ReceitaVO;

import java.sql.*;
import java.util.ArrayList;

public class ReceitaDAO {

    public void cadastrarReceitaDAO(ReceitaVO receitaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;

        String query = "INSERT INTO receita (idusuario, descricao, valor, datareceita) VALUES (?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, receitaVO.getIdUsuario());
            pstmt.setString(2, receitaVO.getDescricao());
            pstmt.setBigDecimal(3, receitaVO.getValor());
            pstmt.setTimestamp(4, Timestamp.valueOf(receitaVO.getDataReceita()));

            pstmt.executeUpdate();
            System.out.println("Receita cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar receita: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
    }

    public boolean verificarExistenciaReceitaDAO(ReceitaVO receitaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet rs = null;

        boolean existe = false;
        String query = "SELECT idreceita FROM receita WHERE idreceita = " + receitaVO.getIdReceita();

        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar existência da receita: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return existe;
    }

    public boolean atualizarReceitaDAO(ReceitaVO receitaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;

        boolean sucesso = false;
        String query = "UPDATE receita SET idusuario=?, descricao=?, valor=?, datareceita=? WHERE idreceita=?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, receitaVO.getIdUsuario());
            pstmt.setString(2, receitaVO.getDescricao());
            pstmt.setBigDecimal(3, receitaVO.getValor());
            pstmt.setTimestamp(4, Timestamp.valueOf(receitaVO.getDataReceita()));
            pstmt.setInt(5, receitaVO.getIdReceita());

            if (pstmt.executeUpdate() > 0) {
                sucesso = true;
                System.out.println("Receita atualizada com sucesso!");
            } else {
                System.out.println("Receita não encontrada para atualização.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar receita: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }

        return sucesso;
    }

    public boolean excluirReceitaDAO(ReceitaVO receitaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);

        boolean sucesso = false;
        String query = "DELETE FROM receita WHERE idreceita=" + receitaVO.getIdReceita();

        try {
            if (stmt.executeUpdate(query) > 0) {
                sucesso = true;
                System.out.println("Receita excluída com sucesso!");
            } else {
                System.out.println("Receita não encontrada para exclusão.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir receita: " + e.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return sucesso;
    }

    public ArrayList<ReceitaVO> consultarTodasReceitasDAO() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet rs = null;

        ArrayList<ReceitaVO> listaReceitas = new ArrayList<>();
        String query = "SELECT * FROM receita";

        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                ReceitaVO receita = new ReceitaVO();
                receita.setIdReceita(rs.getInt("idreceita"));
                receita.setIdUsuario(rs.getInt("idusuario"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setValor(rs.getBigDecimal("valor"));
                receita.setDataReceita(rs.getTimestamp("datareceita").toLocalDateTime());
                listaReceitas.add(receita);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar todas as receitas: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return listaReceitas;
    }

    public ReceitaVO consultarReceitaDAO(ReceitaVO receitaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet rs = null;

        ReceitaVO receita = null;
        String query = "SELECT * FROM receita WHERE idreceita=" + receitaVO.getIdReceita();

        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                receita = new ReceitaVO();
                receita.setIdReceita(rs.getInt("idreceita"));
                receita.setIdUsuario(rs.getInt("idusuario"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setValor(rs.getBigDecimal("valor"));
                receita.setDataReceita(rs.getTimestamp("datareceita").toLocalDateTime());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar receita: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return receita;
    }
}
