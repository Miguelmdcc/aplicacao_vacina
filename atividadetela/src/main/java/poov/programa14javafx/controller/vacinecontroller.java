package poov.programa14javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import poov.programa14javafx.modelo.Aplicacao;
import poov.programa14javafx.modelo.Pessoa;
import poov.programa14javafx.modelo.Situacao;
import poov.programa14javafx.modelo.Vacina;
import poov.programa14javafx.modelo.dao.AplicacaoDAO;
import poov.programa14javafx.modelo.dao.PessoaDAO;
import poov.programa14javafx.modelo.dao.VacinaDAO;
import poov.programa14javafx.modelo.dao.core.DAOFactory;
import poov.programa14javafx.modelo.dao.core.GenericJDBCDAO;

public class vacinecontroller implements Initializable {

    @FXML
    private TextField cod;

    @FXML
    private TextField codRecebido;

    @FXML
    private TableColumn<Vacina, String> coddesc;

    @FXML
    private TableColumn<Vacina, String> codname;

    @FXML
    private TableColumn<Vacina, Long> codtable;

    @FXML
    private TextField desc;

    @FXML
    private TextField descRecebido;

    @FXML
    private TextField name;

    @FXML
    private TextField nameRecebido;

    @FXML
    private TableView<Vacina> table;

    @FXML
    private DatePicker apartirde;

    @FXML
    private DatePicker ate;

    @FXML
    private TextField cpf;

    @FXML
    private TableColumn<Pessoa, String> cpftable;

    @FXML
    private TableColumn<Pessoa, LocalDate> nascimentotable;

    @FXML
    private TextField pessoacodigo;

    @FXML
    private TableColumn<Pessoa, Long> pessoacodtable;

    @FXML
    private TextField pessoanome;

    @FXML
    private TableColumn<Pessoa, String> pessoanometable;

    @FXML
    private TableView<Pessoa> pessoatable;

    @FXML
    void searchpessoa(ActionEvent event) {
        try {

            factory.abrirConexao();
            PessoaDAO dao = factory.getDAO(PessoaDAO.class);
            String codigo = pessoacodigo.getText();
            String nome = pessoanome.getText();
            String descri = cpf.getText();
            LocalDate apartir = apartirde.getValue();
            LocalDate dataate = ate.getValue();

            List<Pessoa> pessoas = dao.buscar(codigo, nome, descri, apartir, dataate);

            if (pessoas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Nenhuma pessoa encontrada!");
                alert.showAndWait();
                pessoatable.refresh();
            } else {
                pessoatable.getItems().clear();
                pessoatable.getItems().addAll(pessoas);
            }
        } catch (SQLException ex) {
            GenericJDBCDAO.showSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }

    @FXML
    void edit(ActionEvent event) {
        controllerTelaSecundaria.setDaofac2(factory);
        controllerTelaSecundaria.setValido(false);
        int index = table.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            vacina = table.getSelectionModel().getSelectedItem();
            controllerTelaSecundaria.setVacina(vacina);
            stageTelaSecundaria.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma vacina selecionada");
            alert.showAndWait();
        }
        table.refresh();
    }

    @FXML
    void nova(ActionEvent event) {
        controllerTelaSecundaria.setDaofac2(factory);
        controllerTelaSecundaria.setValido(true);
        stageTelaSecundaria.showAndWait();
        table.refresh();
    }

    @FXML
    void remove(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("remoção");
        alert.setContentText("remover vacina");
        alert.setHeaderText("Deseja realmente remover a vacina?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                System.out.print("Digite o codigo da vacina a alterar: ");
                Vacina vacina = new Vacina();
                int index = table.getSelectionModel().getSelectedIndex();
                factory.abrirConexao();
                VacinaDAO dao = factory.getDAO(VacinaDAO.class);

                if (index != -1) {
                    vacina = table.getSelectionModel().getSelectedItem();
                    vacina.setSituacao(Situacao.INATIVO);
                    dao.update(vacina);
                }
            } catch (SQLException ex) {
                GenericJDBCDAO.showSQLException(ex);
            } finally {
                table.refresh();
                factory.fecharConexao();
            }
        }
        // controllerTelaSecundaria.limpar();
    }

    @FXML
    void search(ActionEvent event) {
        try {
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);
            String codigo = cod.getText();
            String nome = name.getText();
            String descri = desc.getText();

            List<Vacina> vacinas = dao.buscar(codigo, nome, descri);

            if (vacinas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Nenhuma vacina encontrada!");
                alert.showAndWait();
            } else {
                table.getItems().clear();
                table.getItems().addAll(vacinas);
            }
        } catch (SQLException ex) {
            GenericJDBCDAO.showSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }

    private TextFormatter<String> createNumberOnlyTextFormatter() {
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) {
                return change;
            }
            String text = change.getControlNewText();
            if (text.length() == 0) {
                // permite campo vazio
                return change;
            } else {
                // verifica se o texto, com a mudança, é um long válido
                try {
                    Long.parseLong(text);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return change;
        });
    }

    private Stage stageTelaSecundaria;
    private auxiliarcontroller controllerTelaSecundaria;
    private Vacina vacina;
    private Pessoa pessoa;
    public DAOFactory factory;

    public vacinecontroller() {
        System.out.println("vacinecontroller criado");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auxiliartable.fxml"));
        Parent root;
        codname.setCellValueFactory(
                new PropertyValueFactory<>("nome"));
        codtable.setCellValueFactory(
                new PropertyValueFactory<>("codigo"));
        coddesc.setCellValueFactory(
                new PropertyValueFactory<>("descricao"));

        pessoanometable.setCellValueFactory(
                new PropertyValueFactory<>("nome"));
        pessoacodtable.setCellValueFactory(
                new PropertyValueFactory<>("codigo"));
        cpftable.setCellValueFactory(
                new PropertyValueFactory<>("cpf"));
        nascimentotable.setCellValueFactory(
                new PropertyValueFactory<>("dataNascimento"));
        // table.setItems(listadevacina());
        cod.setTextFormatter(createNumberOnlyTextFormatter());
        pessoacodigo.setTextFormatter(createNumberOnlyTextFormatter());
        // cpf.setTextFormatter(createNumberOnlyTextFormatter());
        // Alterar a maneira de mostrar uma informação em uma célula
        nascimentotable.setCellFactory(col -> {
            TableCell<Pessoa, LocalDate> cell = new TableCell<Pessoa, LocalDate>() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    // Cleanup the cell before populating it
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        // Format the birth date in mm/dd/yyyy format
                        String formattedDob = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(item);
                        this.setText(formattedDob);
                    }
                }
            };
            return cell;
        });
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stageTelaSecundaria = new Stage();
            stageTelaSecundaria.setScene(scene);
            stageTelaSecundaria.setTitle("Vacinas - Modificar");
            stageTelaSecundaria.getIcons().add(new Image(getClass().getResourceAsStream("/images/java.png")));
            controllerTelaSecundaria = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDaofac(DAOFactory factory) {
        this.factory = factory;
    }

    @FXML
    void aplicacao(ActionEvent event) {
        int selectVacina = table.getSelectionModel().getSelectedIndex();
        int selectPessoa = pessoatable.getSelectionModel().getSelectedIndex();
        try {
            factory.abrirConexao();
            AplicacaoDAO dao = factory.getDAO(AplicacaoDAO.class);

            if (selectVacina != -1 && selectPessoa != -1) {
                Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
                alert0.setTitle("Aplicação");
                alert0.setContentText("Aplicar vacina");
                alert0.setHeaderText("Deseja realmente aplicar a vacina?");
                if (alert0.showAndWait().get() == ButtonType.OK) {
                    Vacina vacinap = table.getSelectionModel().getSelectedItem();
                    Pessoa pessoap = pessoatable.getSelectionModel().getSelectedItem();
                    LocalDate agora = LocalDate.now();
                    Aplicacao aplicacao = new Aplicacao(pessoap.getCodigo(), vacinap.getCodigo(), agora);
                    System.out.println(aplicacao);
                    dao.create(aplicacao);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro na Aplicação");
                alert.setContentText("Vacina ou Pessoa não inserida");
                alert.setHeaderText("Insira ambos campos na Aplicação");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            GenericJDBCDAO.showSQLException(ex);
        } finally {
            name.clear();
            desc.clear();
            cod.clear();
            pessoacodigo.clear();
            pessoanome.clear();
            cpf.clear();
            factory.fecharConexao();
        }

    }

    public void atualiza() {
        search(null);
        searchpessoa(null);
    }
    /*
     * @FXML
     * void buttonEnviarClicado(ActionEvent event) {
     * Vacina vacina = new Vacina();
     * vacina.setCodigo(Long.parseLong(cod.getText()));
     * vacina.setNome(name.getText());
     * vacina.setDescricao(desc.getText());
     * controllerTelaSecundaria.setVacina(vacina);
     * controllerTelaSecundaria.setValido(false);
     * }
     * 
     * @FXML
     * void buttonReceberClicado(ActionEvent event) {
     * if (controllerTelaSecundaria.isValido()) {
     * vacina = controllerTelaSecundaria.getVacina();
     * codRecebido.setText(String.valueOf(vacina.getCodigo()));
     * nameRecebido.setText(vacina.getNome());
     * descRecebido.setText(vacina.getDescricao());
     * } else {
     * Alert alert = new Alert(Alert.AlertType.INFORMATION);
     * alert.setContentText("A janela não contém dados válidos.");
     * alert.showAndWait();
     * }
     * }
     */

}
