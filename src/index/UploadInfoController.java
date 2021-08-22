package index;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.UploadInfo;

public class UploadInfoController {
    @FXML
    private TextField appIdTv;
    @FXML
    private TextField appKeyTv;
    @FXML
    private TextField bundleIdTv;
    @FXML
    private TextField versionTv;
    @FXML
    private TextField platformTv;
    @FXML
    private TextField symbolTv;
    @FXML
    private TextField mappingTv;
    @FXML
    private TextField buglyJarPathTv;
    @FXML
    private Button upload;
    @FXML
    private Button symbolChooser;
    @FXML
    private Button mappingChooser;
    @FXML
    private Button buglyJarChooser;
    @FXML
    private Label jarCommand;

    private UploadInfo uploadInfo;
    private FileChooser fileChooser;
    private FileChooser jarChooser;
    private DirectoryChooser directoryChooser;

    public UploadInfoController() {
    }

    @FXML
    private void initialize() {
        appIdTv.textProperty()
               .addListener((observable, oldValue, newValue) -> {
                   uploadInfo.setAppId(newValue);
                   updateJarCommand();
               });
        appKeyTv.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    uploadInfo.setAppKey(newValue);
                    updateJarCommand();
                });
        bundleIdTv.textProperty()
                  .addListener((observable, oldValue, newValue) -> {
                      uploadInfo.setBundleId(newValue);
                      updateJarCommand();
                  });
        versionTv.textProperty()
                 .addListener((observable, oldValue, newValue) -> {
                     uploadInfo.setVersion(newValue);
                     updateJarCommand();
                 });
        platformTv.textProperty()
                  .addListener((observable, oldValue, newValue) -> {
                      uploadInfo.setPlatform(newValue);
                      updateJarCommand();
                  });
        symbolTv.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    uploadInfo.setInputSymbol(newValue);
                    updateJarCommand();
                });
        mappingTv.textProperty()
                 .addListener((observable, oldValue, newValue) -> {
                     uploadInfo.setInputMapping(newValue);
                     updateJarCommand();
                 });
        buglyJarPathTv.textProperty()
                      .addListener((observable, oldValue, newValue) -> {
                          uploadInfo.setBulgyJarPath(newValue);
                          updateJarCommand();
                      });
        upload.setOnAction(event -> {
            if (isEmpty(uploadInfo.getAppId())) {
                ToastTool.toast("请输入 app id");
                return;
            }
            if (isEmpty(uploadInfo.getAppKey())) {
                ToastTool.toast("请输入 app key");
                return;
            }
            if (isEmpty(uploadInfo.getBundleId())) {
                ToastTool.toast("请输入 bundle id");
                return;
            }
            if (isEmpty(uploadInfo.getVersion())) {
                ToastTool.toast("请输入 version");
                return;
            }
            if (isEmpty(uploadInfo.getPlatform())) {
                ToastTool.toast("请输入 platform");
                return;
            }
            if (isEmpty(uploadInfo.getInputMapping()) && uploadInfo.getPlatform()
                                                                   .equals("Android")) {
                ToastTool.toast("请选择 mapping.txt");
                return;
            }
            if (isEmpty(uploadInfo.getBulgyJarPath())) {
                ToastTool.toast("请选择 bugly jar包[buglyqq-upload-symbol.jar] 目录");
                return;
            }

            saveTemp();
            runJar();
        });
        symbolChooser.setOnAction(event -> {
            if (directoryChooser == null) {
                directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle(
                        "input symbol,如果是Android平台同时包含mapping和so，选择两个原始符号表存储的共同父目录");
            }
            if (!isEmpty(uploadInfo.getInputSymbol())) {
                directoryChooser.setInitialDirectory(new File(uploadInfo.getInputSymbol()).getParentFile());
            }
            File primary = directoryChooser.showDialog(main.stage);

            if (primary != null && primary.exists()) {
                symbolTv.setText(primary.getAbsolutePath());
            }
        });
        mappingChooser.setOnAction(event -> {
            if (fileChooser == null) {
                fileChooser = new FileChooser();
                fileChooser.setTitle("input mapping,mapping所在文件夹目录地址[Android平台特有，ios忽略]");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("mapping.txt",
                                                                                      "*.txt"));
            }
            if (!isEmpty(uploadInfo.getInputMapping())) {
                File file = new File(uploadInfo.getInputMapping());
                fileChooser.setInitialDirectory(file.getParentFile());
            }
            File mapping = fileChooser.showOpenDialog(main.stage);
            if (mapping != null && mapping.exists()) {
                mappingTv.setText(mapping.getAbsolutePath());
            }
        });
        buglyJarChooser.setOnAction(event -> {
            if (jarChooser == null) {
                jarChooser = new FileChooser();
                jarChooser.setTitle("请选择bugly jar包所在目录");
                jarChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                        "buglyqq-upload-symbol.jar",
                        "*.jar"));
            }
            if (!isEmpty(uploadInfo.getBulgyJarPath())) {
                File file = new File(uploadInfo.getBulgyJarPath());
                jarChooser.setInitialDirectory(file.getParentFile());
            }
            File jar = jarChooser.showOpenDialog(main.stage);
            if (jar != null && jar.exists()) {
                buglyJarPathTv.setText(jar.getAbsolutePath());
            }
        });
    }

    private void runJar() {
        try {
            String jarPath = uploadInfo.getBulgyJarPath();
            File jarFile = new File(jarPath);
            String command = "java -jar " + jarFile.getAbsolutePath() + getJarConfig() + "\n pause";
            File bat = new File("temp.bat");
            if (bat.exists()) {
                bat.delete();
            }
            bat.createNewFile();
            FileWriter writer = new FileWriter(bat);
            writer.write(command);
            writer.flush();
            writer.close();
            Desktop.getDesktop().open(bat);
        } catch (IOException e) {
            e.printStackTrace();
            ToastTool.toast("jar包执行异常：" + e.getMessage());
        }
    }

    private void updateJarCommand() {
        String jarPath = uploadInfo.getBulgyJarPath();
        String command = "java -jar " + new File(jarPath).getName() + getJarConfig().replace(" -",
                                                                                             "\n -");
        jarCommand.setText(command);
    }

    private String getJarConfig() {
        String config = " -appid " + uploadInfo.getAppId()
                + " -appkey " + uploadInfo.getAppKey()
                + " -bundleid " + uploadInfo.getBundleId()
                + " -version " + uploadInfo.getVersion()
                + " -platform " + uploadInfo.getPlatform();
        if (!isEmpty(uploadInfo.getInputMapping())) {
            config += " -inputMapping " + uploadInfo.getInputMapping();
        }
        if (!isEmpty(uploadInfo.getInputSymbol())) {
            config += " -inputSymbol " + uploadInfo.getInputSymbol();
        }
        return config;
    }

    private void loadTemp() {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(UploadInfo.class);
            Unmarshaller um = context.createUnmarshaller();
            File tempFile = main.getTempFile();
            if (tempFile == null || tempFile.length() == 0) return;
            System.out.println("temp file:" + tempFile.getAbsolutePath() + ",length:" + (tempFile.length()));
            Object obj = um.unmarshal(tempFile);
            if (obj != null) {
                uploadInfo = (UploadInfo) obj;
            }
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }
    }

    private void saveTemp() {
        try {
            File tempFile = main.getTempFile();
            tempFile.delete();
            JAXBContext context = JAXBContext
                    .newInstance(UploadInfo.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(uploadInfo, tempFile);
            System.out.println("save temp：" + tempFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Main main;

    public void setMain(Main main) {
        this.main = main;
        loadTemp();
        if (uploadInfo == null) {
            uploadInfo = new UploadInfo();
            saveTemp();
        } else {
            appIdTv.setText(uploadInfo.getAppId());
            appKeyTv.setText(uploadInfo.getAppKey());
            bundleIdTv.setText(uploadInfo.getBundleId());
            versionTv.setText(uploadInfo.getVersion());
            platformTv.setText(uploadInfo.getPlatform());
            symbolTv.setText(uploadInfo.getInputSymbol());
            mappingTv.setText(uploadInfo.getInputMapping());
            buglyJarPathTv.setText(uploadInfo.getBulgyJarPath());
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0;
    }
}
