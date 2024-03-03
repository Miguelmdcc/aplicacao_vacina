package poov.programa14javafx.testes;

import java.sql.SQLException;
import java.util.Scanner;

import poov.programa14javafx.modelo.Situacao;
import poov.programa14javafx.modelo.Vacina;
import poov.programa14javafx.modelo.dao.ConexaoFactoryPostgreSQL;
import poov.programa14javafx.modelo.dao.VacinaDAO;
import poov.programa14javafx.modelo.dao.core.ConnectionFactory;
import poov.programa14javafx.modelo.dao.core.DAOFactory;
import poov.programa14javafx.modelo.dao.core.GenericJDBCDAO;

public class RemocaoBanco2 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        ConnectionFactory conexaoFactory = new ConexaoFactoryPostgreSQL("localhost:5432/poov", "postgres", "12345");
        DAOFactory factory = new DAOFactory(conexaoFactory);
        try {
            System.out.print("Digite o codigo da vacina a remover: ");
            long codigo = Long.parseLong(s.nextLine());
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);

            Vacina vacina = dao.findById(codigo);
            if (vacina != null) {
                System.out.println(vacina);
                System.out.print("Deseja realmente remover essa vacina? (S/N): ");
                String opcao = s.nextLine();
                if (opcao.equalsIgnoreCase("S")) {
                    vacina.setSituacao(Situacao.INATIVO);
                    dao.update(vacina);
                }
            } else {
                System.out.println("Nao foi encontrada uma vacina com o codigo: " + codigo);
            }
            s.close();
        } catch (SQLException ex) {
            GenericJDBCDAO.showSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }

}
