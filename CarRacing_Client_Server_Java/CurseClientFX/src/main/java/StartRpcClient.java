import controllers.CurseParticipantiController;
import controllers.LoginController;
import curse.services.ICurseServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rpcprotocol.CurseClientRpcProxy;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClient extends Application {
    private static int defaultCursePort = 55555;
    private static String defaultServer = "localhost";
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/curseclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find curseclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("curse.server.host", defaultServer);
        int serverPort = defaultCursePort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("curse.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultCursePort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        ICurseServices server= new CurseClientRpcProxy(serverIP,serverPort);
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

    public static void main(String[] args) {
        launch(args);
    }
}
