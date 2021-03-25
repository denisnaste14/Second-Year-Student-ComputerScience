import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import repository.database.CursaDbRepo;
import repository.database.InscriereDbRepo;
import repository.database.ParticipantDbRepo;
import repository.database.StaffDbRepo;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application {
    private Service service=null;

    public void init_service(){
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        ParticipantDbRepo pdr=new ParticipantDbRepo(props);
        CursaDbRepo cdr=new CursaDbRepo(props);
        InscriereDbRepo idr=new InscriereDbRepo(props,pdr,cdr);
        StaffDbRepo sdr=new StaffDbRepo(props);

        this.service = new Service(pdr,cdr,idr,sdr);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init_service();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Login.fxml"));
        Pane root;
        root = loader.load();

        LoginController lc=loader.getController();
        lc.initial(service);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
