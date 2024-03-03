package poov.programa14javafx.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import poov.programa14javafx.modelo.Pessoa;
import poov.programa14javafx.modelo.Situacao;
import poov.programa14javafx.modelo.Vacina;
import poov.programa14javafx.modelo.dao.core.GenericJDBCDAO;

public class PessoaDAO extends GenericJDBCDAO<Pessoa, Long> {

    public PessoaDAO(Connection conexao) {
        super(conexao);
    }

    private static final String FIND_ALL_QUERY = "SELECT codigo_pessoa, nome_pessoa, cpf, nascimento, situacao FROM pessoa WHERE situacao='ATIVO' ";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "AND codigo_pessoa=? ";
    private static final String FIND_BY_NAME_LIKE_QUERY = FIND_ALL_QUERY + "AND upper(nome_pessoa) like upper(?)";
    private static final String UPDATE_QUERY = "UPDATE pessoa SET nome_pessoa=?, cpf=?, situacao=? WHERE codigo_pessoa=?";
    private static final String CREATE_QUERY = "INSERT INTO pessoa (nome_pessoa, cpf, situacao) VALUES (?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM pessoa WHERE codigo_pessoa=?";

    @Override
    protected Pessoa toEntity(ResultSet resultSet) throws SQLException {
        Pessoa Pessoa = new Pessoa();
        Pessoa.setCodigo(resultSet.getLong("codigo_pessoa"));
        Pessoa.setNome(resultSet.getString("nome_pessoa"));
        Pessoa.setCpf(resultSet.getString("cpf"));
        Pessoa.setDataNascimento(resultSet.getDate("nascimento").toLocalDate());
        if (resultSet.getString("situacao").equals("ATIVO")) {
            Pessoa.setSituacao(Situacao.ATIVO);
        } else {
            Pessoa.setSituacao(Situacao.INATIVO);
        }
        return Pessoa;
    }

    @Override
    protected void addParameters(PreparedStatement pstmt, Pessoa entity) throws SQLException {
        pstmt.setString(1, entity.getNome());
        pstmt.setString(2, entity.getCpf());
        pstmt.setString(3, entity.getSituacao().toString());
        if (entity.getCodigo() != null) {
            pstmt.setLong(4, entity.getCodigo());
        }
        pstmt.setDate(4, java.sql.Date.valueOf(entity.getDataNascimento()));
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

    public List<Pessoa> findByNameLike(String nome) {
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_LIKE_QUERY);
            statement.setString(1, "%" + nome + "%");
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            showSQLException(e);
        }
        return new ArrayList<Pessoa>();
    }

    @Override
    protected void setKeyInStatementFromEntity(PreparedStatement statement, Pessoa entity) throws SQLException {
        statement.setLong(1, entity.getCodigo());
    }

    @Override
    protected void setKeyInStatement(PreparedStatement statement, Long key) throws SQLException {
        statement.setLong(1, key);
    }

    @Override
    protected void setKeyInEntity(ResultSet rs, Pessoa entity) throws SQLException {
        entity.setCodigo(rs.getLong(1));
    }

    public List<Pessoa> buscar(String codigo, String nome, String cpf, LocalDate apartir, LocalDate ate) {
        try {
            PreparedStatement statement;
            int indice = 1;

            String sql = "SELECT * FROM PESSOA WHERE ";
            if (!codigo.isEmpty()) {
                sql += "CODIGO_PESSOA = ? AND ";
            }
            if (!nome.isEmpty()) {
                sql += "upper(nome_pessoa) like upper(?) AND ";
            }
            if (!cpf.isEmpty()) {
                sql += "CPF LIKE ? AND ";
            }
            if (apartir != null) {
                sql += "NASCIMENTO BETWEEN '" + apartir + "' AND '" + LocalDate.now() + "' AND ";
            }
            if (ate != null) {
                sql += "NASCIMENTO <= '" + ate + "' AND ";
            }
            sql += "SITUACAO = 'ATIVO'";
            // System.out.println(sql);
            statement = connection.prepareStatement(sql);
            if (!codigo.isEmpty()) {
                statement.setLong(indice, Long.parseLong(codigo));
                indice++;
            }
            if (!nome.isEmpty()) {
                statement.setString(indice, "%" + nome + "%");
                indice++;
            }
            if (!cpf.isEmpty()) {
                statement.setString(indice, "%" + cpf + "%");
                indice++;
            }

            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException ex) {
            GenericJDBCDAO.showSQLException(ex);
        }

        return new ArrayList<Pessoa>();
    }

}
