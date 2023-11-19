package model.dao;

import model.vo.DespesaVO;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DespesaDAO {

    public void cadastrarDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement stmt = null;

        String sql = "INSERT INTO despesa (idusuario, descricao, valor, datavencimento, datapagamento) VALUES (?, ?, ?, ?, ?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, despesaVO.getIdUsuario());
            stmt.setString(2, despesaVO.getDescricao());
            stmt.setBigDecimal(3, despesaVO.getValor());
            stmt.setTimestamp(4, Timestamp.valueOf(despesaVO.getDataVencimento()));
            stmt.setTimestamp(5, Timestamp.valueOf(despesaVO.getDataPagamento()));

            stmt.executeUpdate();
            System.out.println("Despesa cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar despesa: " + e.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
    }

    public boolean verificarExistenciaDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        boolean existe = false;

        String query = "SELECT iddespesa FROM despesa WHERE iddespesa = " + despesaVO.getIdDespesa();

        try {
            resultado = stmt.executeQuery(query);
            existe = resultado.next();
        } catch (SQLException e) {
            System.out.println("Erro ao verificar existência da despesa: " + e.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return existe;
    }

    public void atualizarDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement stmt = null;

        String sql = "UPDATE despesa SET idusuario=?, descricao=?, valor=?, datavencimento=?, datapagamento=? WHERE iddespesa=?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, despesaVO.getIdUsuario());
            stmt.setString(2, despesaVO.getDescricao());
            stmt.setBigDecimal(3, despesaVO.getValor());
            stmt.setTimestamp(4, Timestamp.valueOf(despesaVO.getDataVencimento()));
            stmt.setTimestamp(5, Timestamp.valueOf(despesaVO.getDataPagamento()));
            stmt.setInt(6, despesaVO.getIdDespesa());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Despesa atualizada com sucesso!");
            } else {
                System.out.println("Falha ao atualizar despesa. Verifique o ID da despesa.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar despesa: " + e.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
    }

    public void excluirDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);

        String sql = "DELETE FROM despesa WHERE iddespesa=" + despesaVO.getIdDespesa();

        try {
            int linhasAfetadas = stmt.executeUpdate(sql);
            if (linhasAfetadas > 0) {
                System.out.println("Despesa excluída com sucesso!");
            } else {
                System.out.println("Falha ao excluir despesa. Verifique o ID da despesa.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir despesa: " + e.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
    }

    public ArrayList<DespesaVO> consultarTodasDespesasDAO() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        ArrayList<DespesaVO> listaDespesas = new ArrayList<>();

        String query = "SELECT * FROM despesa";

        try {
            resultado = stmt.executeQuery(query);
            while (resultado.next()) {
                DespesaVO despesa = new DespesaVO();
                despesa.setIdDespesa(resultado.getInt("iddespesa"));
                despesa.setIdUsuario(resultado.getInt("idusuario"));
                despesa.setDescricao(resultado.getString("descricao"));
                despesa.setValor(resultado.getBigDecimal("valor"));
                despesa.setDataVencimento(resultado.getTimestamp("datavencimento").toLocalDateTime());
                Timestamp dataPagamento = resultado.getTimestamp("datapagamento");
                despesa.setDataPagamento(dataPagamento != null ? dataPagamento.toLocalDateTime() : null);

                listaDespesas.add(despesa);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar todas as despesas: " + e.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return listaDespesas;
    }

    public DespesaVO consultarDespesaDAO(DespesaVO despesaVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        DespesaVO despesa = new DespesaVO();

        String query = "SELECT * FROM despesa WHERE iddespesa=" + despesaVO.getIdDespesa();

        try {
            resultado = stmt.executeQuery(query);
            if (resultado.next()) {
                despesa.setIdDespesa(resultado.getInt("iddespesa"));
                despesa.setIdUsuario(resultado.getInt("idusuario"));
                despesa.setDescricao(resultado.getString("descricao"));
                despesa.setValor(resultado.getBigDecimal("valor"));
                despesa.setDataVencimento(resultado.getTimestamp("datavencimento").toLocalDateTime());
                Timestamp dataPagamento = resultado.getTimestamp("datapagamento");
                despesa.setDataPagamento(dataPagamento != null ? dataPagamento.toLocalDateTime() : null);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar despesa: " + e.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return despesa;
    }
}
