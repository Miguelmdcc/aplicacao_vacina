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

public class UpdateBanco {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        ConnectionFactory conexaoFactory = new ConexaoFactoryPostgreSQL("localhost:5432/poov", "postgres", "12345");
        DAOFactory factory = new DAOFactory(conexaoFactory);
        try {
            System.out.print("Digite o codigo da vacina a alterar: ");
            long codigo = Long.parseLong(s.nextLine());
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);

            Vacina vacina = dao.findById(codigo);

            if (vacina != null) {
                System.out.println(vacina);
                System.out.print("Deseja realmente alterar essa vacina? (S/N): ");
                String opcao = s.nextLine();
                if (opcao.equalsIgnoreCase("S")) {
                    opcao = "1";
                    do {
                        System.out.println("1 - Nome");
                        System.out.println("2 - Descricao");
                        System.out.println("3 - Situacao");
                        System.out.println("4 - Terminar");
                        System.out.print("Opcao: ");
                        opcao = s.nextLine();
                        switch (opcao) {
                            case "1":
                                System.out.print("Nome: ");
                                vacina.setNome(s.nextLine());
                                break;
                            case "2":
                                System.out.print("Descricao: ");
                                vacina.setDescricao(s.nextLine());
                                break;
                            case "3":
                                vacina.setSituacao(Situacao.INATIVO);
                                System.out.println("Situacao alterada");
                                break;
                        }
                    } while (!opcao.equals("4"));

                    dao.update(vacina);

                    s.close();
                }
            } else {
                System.out.println("Nao foi encontrada uma vacina com o codigo: " + codigo);
            }
        } catch (SQLException ex) {
            GenericJDBCDAO.showSQLException(ex);
        } finally {
            factory.fecharConexao();
        }

    }

}
