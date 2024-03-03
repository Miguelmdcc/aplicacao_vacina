package poov.programa14javafx.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import poov.programa14javafx.modelo.Aplicacao;
import poov.programa14javafx.modelo.Situacao;
import poov.programa14javafx.modelo.dao.core.GenericJDBCDAO;

public class AplicacaoDAO extends GenericJDBCDAO<Aplicacao, Long> {

    public AplicacaoDAO(Connection conexao) {
        super(conexao);
    }

    private static final String FIND_ALL_QUERY = "SELECT codigo_aplic, data, codigo_pessoa, codigo_vacina, situacao FROM aplicacao WHERE situacao='ATIVO' ";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "AND codigo_aplic=? ";
    private static final String FIND_BY_NAME_LIKE_QUERY = FIND_ALL_QUERY + "AND upper(codigo_aplic) like upper(?)";
    private static final String UPDATE_QUERY = "UPDATE aplicacao SET codigo_aplic=?, data=?, codigo_pessoa=?, codigo_vacina=?, situacao=? WHERE codigo_aplic=?";
    private static final String CREATE_QUERY = "INSERT INTO aplicacao (data, codigo_pessoa, codigo_vacina, situacao) VALUES (?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM aplicacao WHERE codigo_aplic=?";

    @Override
    protected Aplicacao toEntity(ResultSet resultSet) throws SQLException {
        Aplicacao Aplicacao = new Aplicacao();
        Aplicacao.setCodigo(resultSet.getLong("codigo_aplic"));
        Aplicacao.setPessoa(resultSet.getLong("codigo_pessoa"));
        Aplicacao.setVacina(resultSet.getLong("codigo_vacina"));
        Aplicacao.setAtual(resultSet.getDate("nascimento").toLocalDate());
        if (resultSet.getString("situacao").equals("ATIVO")) {
            Aplicacao.setSituacao(Situacao.ATIVO);
        } else {
            Aplicacao.setSituacao(Situacao.INATIVO);
        }
        return Aplicacao;
    }

    @Override
    protected void addParameters(PreparedStatement pstmt, Aplicacao entity) throws SQLException {
        pstmt.setLong(2, entity.getPessoa());
        pstmt.setLong(3, entity.getVacina());
        pstmt.setString(4, entity.getSituacao().toString());
        if (entity.getCodigo() != null) {
            pstmt.setLong(4, entity.getCodigo());
        }
        pstmt.setDate(1, java.sql.Date.valueOf(entity.getAtual()));
    }

    @Override
    protected String findByKeyQuery() {
        return FIND_BY_KEY_QUERY;
    }

    @Override
    protected String findAllQuery() {
        return FIND_ALL_QUERY;
    }

    @Override
    protected String updateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String createQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String removeQuery() {
        return REMOVE_QUERY;
    }

    public List<Aplicacao> findByNameLike(String nome) {
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_LIKE_QUERY);
            statement.setString(1, "%" + nome + "%");
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            showSQLException(e);
        }
        return new ArrayList<Aplicacao>();
    }

    @Override
    protected void setKeyInStatementFromEntity(PreparedStatement statement, Aplicacao entity) throws SQLException {
        statement.setLong(1, entity.getCodigo());
    }

    @Override
    protected void setKeyInStatement(PreparedStatement statement, Long key) throws SQLException {
        statement.setLong(1, key);
    }

    @Override
    protected void setKeyInEntity(ResultSet rs, Aplicacao entity) throws SQLException {
        entity.setCodigo(rs.getLong(1));
    }

}
