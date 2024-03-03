package poov.programa14javafx.controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import poov.programa14javafx.modelo.Situacao;
import poov.programa14javafx.modelo.Vacina;
import poov.programa14javafx.modelo.dao.VacinaDAO;
import poov.programa14javafx.modelo.dao.core.DAOFactory;
import poov.programa14javafx.modelo.dao.core.GenericJDBCDAO;

public class auxiliarcontroller {

    @FXML
    private TextField cod;

    @FXML
    private TextArea desc;

    @FXML
    private Label codLabel;

    @FXML
    private TextField name;

    @FXML
    void cancel(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void ok(ActionEvent event) {
        if (valido) {
            if (validarCampos()) {
                try {
                    factory.abrirConexao();
                    VacinaDAO dao = factory.getDAO(VacinaDAO.class);
                    vacina = new Vacina();
                    vacina.setNome(name.getText());
                    vacina.setDescricao(desc.getText());
                    dao.create(vacina);
                } catch (SQLException ex) {
                    GenericJDBCDAO.showSQLException(ex);
                } finally {
                    name.clear();
                    desc.clear();
                    factory.fecharConexao();
                }
                ((Button) event.getSource()).getScene().getWindow().hide();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Um dos campos está vazio");
                alert.showAndWait();
            }
        } else {
            try {
                factory.abrirConexao();
                VacinaDAO dao = factory.getDAO(VacinaDAO.class);
                if (validarCampos()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Edição");
                    alert.setContentText("Editar vacina");
                    alert.setHeaderText("Deseja realmente editar a vacina?");
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        vacina.setNome(name.getText());
                        vacina.setDescricao(desc.getText());
                        dao.update(vacina);
                        ((Button) event.getSource()).getScene().getWindow().hide();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Um dos campos está vazio");
                    alert.showAndWait();
                }

            } catch (SQLException ex) {
                GenericJDBCDAO.showSQLException(ex);
            } finally {
                factory.fecharConexao();
            }

        }
    }

    private boolean valido = false;
    public DAOFactory factory;
    private Vacina vacina;

    public auxiliarcontroller() {
        System.out.println("auxiliarcontroller criado");
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
        cod.setText(String.valueOf(vacina.getCodigo()));
        name.setText(vacina.getNome());
        desc.setText(vacina.getDescricao());
    }

    public Vacina getVacina() {
        return vacina;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
        if (!valido) {
            cod.setDisable(true);
            cod.setVisible(true);
            codLabel.setVisible(true);
        }
        if (valido) {
            cod.setVisible(false);
            codLabel.setVisible(false);
            name.clear();
            desc.clear();
        }
    }

    public void limpar() {
        valido = false;
        vacina = new Vacina();
        cod.setText("");
        name.setText("");
        desc.setText("");
    }

    private boolean validarCampos() {
        return !name.getText().isBlank() && !desc.getText().isBlank();
    }

    public void setDaofac2(DAOFactory factory) {
        this.factory = factory;
    }
}
