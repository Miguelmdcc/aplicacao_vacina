package poov.programa14javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import poov.programa14javafx.modelo.Vacina;

public class TelaPrincipalController implements Initializable {

    @FXML
    private Button buttonAbrirJanela;

    @FXML
    private Button buttonEnviar;

    @FXML
    private Button buttonLimparJanela;

    @FXML
    private Button buttonReceber;

    @FXML
    private TextArea textAreaDescricao;

    @FXML
    private TextArea textAreaDescricaoRecebido;

    @FXML
    private TextField textFieldCodigo;

    @FXML
    private TextField textFieldCodigoRecebido;

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldNomeRecebido;

    private Stage stageTelaSecundaria;
    private TelaSecundariaController controllerTelaSecundaria;
    private Vacina vacina;

    public TelaPrincipalController() {
        System.out.println("TelaPrincipalController criado");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaSecundaria.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stageTelaSecundaria = new Stage();
            stageTelaSecundaria.setScene(scene);
            stageTelaSecundaria.setTitle("Programa 14 - Tela 2");
            stageTelaSecundaria.getIcons().add(new Image(getClass().getResourceAsStream("/images/java.png")));
            controllerTelaSecundaria = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttonEnviarClicado(ActionEvent event) {
        Vacina vacina = new Vacina();
        vacina.setCodigo(Long.parseLong(textFieldCodigo.getText()));
        vacina.setNome(textFieldNome.getText());
        vacina.setDescricao(textAreaDescricao.getText());
        controllerTelaSecundaria.setVacina(vacina);
        controllerTelaSecundaria.setValido(false);
    }

    @FXML
    void buttonAbrirJanelaClicado(ActionEvent event) {
        stageTelaSecundaria.showAndWait();
    }

    @FXML
    void buttonLimparJanelaClicado(ActionEvent event) {
        controllerTelaSecundaria.limpar();
    }

    @FXML
    void buttonReceberClicado(ActionEvent event) {
        if (controllerTelaSecundaria.isValido()) {
            vacina = controllerTelaSecundaria.getVacina();
            textFieldCodigoRecebido.setText(String.valueOf(vacina.getCodigo()));
            textFieldNomeRecebido.setText(vacina.getNome());
            textAreaDescricaoRecebido.setText(vacina.getDescricao());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("A janela não contém dados válidos.");
            alert.showAndWait();
        }
    }
}
