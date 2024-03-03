package poov.programa14javafx.testes;

import java.sql.SQLException;
import java.util.Scanner;

import poov.programa14javafx.modelo.Vacina;
import poov.programa14javafx.modelo.dao.ConexaoFactoryPostgreSQL;
import poov.programa14javafx.modelo.dao.VacinaDAO;
import poov.programa14javafx.modelo.dao.core.ConnectionFactory;
import poov.programa14javafx.modelo.dao.core.DAOFactory;
import poov.programa14javafx.modelo.dao.core.GenericJDBCDAO;

public class LeituraBanco {

    public static void main(String[] args) {

        ConnectionFactory conexaoFactory = new ConexaoFactoryPostgreSQL("localhost:5432/poov", "postgres", "12345");
        DAOFactory factory = new DAOFactory(conexaoFactory);
        try {
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);

            Scanner s = new Scanner(System.in);
            long codigo;
            System.out.print("Digite o código da vacina: ");
            codigo = Long.parseLong(s.nextLine());

            Vacina vacina = dao.findById(codigo);
            System.out.println(vacina);

            s.close();

        } catch (SQLException ex) {
            GenericJDBCDAO.showSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }

}
