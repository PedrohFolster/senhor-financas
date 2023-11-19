package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.vo.DespesaVO;

public class DespesaDAO {

    public void cadastrarDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;

        String query = "INSERT INTO despesa (idusuario, descricao, valor, datavencimento, datapagamento) VALUES (?, ?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, despesaVO.getIdUsuario());
            pstmt.setString(2, despesaVO.getDescricao());
            pstmt.setBigDecimal(3, despesaVO.getValor());
            pstmt.setObject(4, despesaVO.getDataVencimento());
            pstmt.setObject(5, despesaVO.getDataPagamento());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar a despesa: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
    }

    public ArrayList<DespesaVO> consultarTodasDespesasDAO() {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<DespesaVO> listaDespesas = new ArrayList<>();
        String query = "SELECT * FROM despesa";

        try {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DespesaVO despesa = new DespesaVO();
                despesa.setIdDespesa(rs.getInt("iddespesa"));
                despesa.setIdUsuario(rs.getInt("idusuario"));
                despesa.setDescricao(rs.getString("descricao"));
                despesa.setValor(rs.getBigDecimal("valor"));
                despesa.setDataVencimento(rs.getObject("datavencimento", LocalDateTime.class));
                despesa.setDataPagamento(rs.getObject("datapagamento", LocalDateTime.class));

                listaDespesas.add(despesa);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar todas as despesas: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }

        return listaDespesas;
    }

    public DespesaVO consultarDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DespesaVO despesa = null;

        String query = "SELECT * FROM despesa WHERE iddespesa = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, despesaVO.getIdDespesa());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                despesa = new DespesaVO();
                despesa.setIdDespesa(rs.getInt("iddespesa"));
                despesa.setIdUsuario(rs.getInt("idusuario"));
                despesa.setDescricao(rs.getString("descricao"));
                despesa.setValor(rs.getBigDecimal("valor"));
                despesa.setDataVencimento(rs.getObject("datavencimento", LocalDateTime.class));
                despesa.setDataPagamento(rs.getObject("datapagamento", LocalDateTime.class));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar a despesa: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }

        return despesa;
    }

    public void atualizarDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;

        String query = "UPDATE despesa SET descricao = ?, valor = ?, datavencimento = ?, datapagamento = ? WHERE iddespesa = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, despesaVO.getDescricao());
            pstmt.setBigDecimal(2, despesaVO.getValor());
            pstmt.setObject(3, despesaVO.getDataVencimento());
            pstmt.setObject(4, despesaVO.getDataPagamento());
            pstmt.setInt(5, despesaVO.getIdDespesa());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a despesa: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
    }

    public void excluirDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;

        String query = "DELETE FROM despesa WHERE iddespesa = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, despesaVO.getIdDespesa());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir a despesa: " + e.getMessage());
        } finally {
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }
    }

    public boolean verificarExistenciaDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean existe = false;

        String query = "SELECT * FROM despesa WHERE iddespesa = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, despesaVO.getIdDespesa());
            rs = pstmt.executeQuery();

            existe = rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao verificar a existência da despesa: " + e.getMessage());
        } finally {
            Banco.closeResultSet(rs);
            Banco.closePreparedStatement(pstmt);
            Banco.closeConnection(conn);
        }

        return existe;
    }
}
