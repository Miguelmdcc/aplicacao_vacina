package poov.programa14javafx.testes;

import java.sql.SQLException;
import java.util.Scanner;

import poov.programa14javafx.modelo.Vacina;
import poov.programa14javafx.modelo.dao.ConexaoFactoryPostgreSQL;
import poov.programa14javafx.modelo.dao.VacinaDAO;
import poov.programa14javafx.modelo.dao.core.ConnectionFactory;
import poov.programa14javafx.modelo.dao.core.DAOFactory;
import poov.programa14javafx.modelo.dao.core.GenericJDBCDAO;

public class EscritaBanco {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        Vacina v = new Vacina();

        ConnectionFactory conexaoFactory = new ConexaoFactoryPostgreSQL("localhost:5432/poov", "postgres", "12345");
        DAOFactory factory = new DAOFactory(conexaoFactory);

        try {
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);

            System.out.print("Digite o nome: ");
            v.setNome(s.nextLine());
            System.out.print("Digite a descricao: ");
            v.setDescricao(s.nextLine());

            dao.create(v);

            s.close();
        } catch (SQLException ex) {
            GenericJDBCDAO.showSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }

}
