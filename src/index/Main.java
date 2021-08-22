package index;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("BuglyUploadTool");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
        UploadInfoController controller = loader.getController();
        controller.setMain(this);
        this.stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public File getTempFile() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath == null) {
            try {
                File temp = new File("temp.xml");
                if (!temp.exists()) {
                    temp.createNewFile();
                }
                prefs.put("filePath", temp.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return getTempFile();
        } else {
            return new File(filePath);
        }
    }

}
