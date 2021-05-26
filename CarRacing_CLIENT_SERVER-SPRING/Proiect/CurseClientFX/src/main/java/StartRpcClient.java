import controllers.CurseParticipantiController;
import controllers.LoginController;
import curse.services.ICurseServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartRpcClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        ICurseServices server=(ICurseServices) factory.getBean("curseService");
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("Views/Login.fxml"));
        Parent root=loader.load();
        LoginController lc=loader.getController();
        lc.initial(server);

        FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().getResource("Views/CurseParticipanti.fxml"));
        Parent root2=loader2.load();
        CurseParticipantiController cpc=loader2.getController();
        cpc.initial(server,new Stage(),new TextField(),new TextField());
        lc.setParent(root2);
        lc.setCurseParticipantiController(cpc);


        primaryStage.setTitle("Curse Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
