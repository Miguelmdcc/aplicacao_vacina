package poov.programa14javafx;

import java.io.IOException;
import java.util.Locale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import poov.programa14javafx.controller.vacinecontroller;
import poov.programa14javafx.modelo.dao.ConexaoFactoryPostgreSQL;
import poov.programa14javafx.modelo.dao.core.ConnectionFactory;
import poov.programa14javafx.modelo.dao.core.DAOFactory;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ConnectionFactory conexaoFactory = new ConexaoFactoryPostgreSQL("localhost:5432/poov", "postgres", "12345");
        DAOFactory factory = new DAOFactory(conexaoFactory);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vacinetable.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Vacinas");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/java.png")));
            vacinecontroller primaria = loader.getController();
            primaria.setDaofac(factory);
            primaria.atualiza();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.of("pt", "BR"));
        launch();
    }

}