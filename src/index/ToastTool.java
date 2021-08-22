package index;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * author 边凌
 * date 2021/8/22 18:39
 * 类描述：
 */

class ToastTool {
    private static final Stage stage = new Stage();
    private static final Label label = new Label();

    static {
        stage.initStyle(StageStyle.TRANSPARENT);
    }

    public static void toast(String msg) {
        toast(msg, 3000);
    }

    public static void toast(String msg, int time) {
        label.setText(msg);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(stage::close);
            }
        };
        init(msg);
        Timer timer = new Timer();
        timer.schedule(task, time);
        stage.show();
    }

    private static void init(String msg) {
        Label label = new Label(msg);//默认信息
        label.setStyle(
                "-fx-background: rgba(255,255,255,0.8)");//label透明,圆角
        label.setTextFill(Color.rgb(255, 0, 0));//消息字体颜色
        label.setPrefHeight(40);
        label.setPadding(new Insets(15));
        label.setAlignment(Pos.CENTER);//居中
        label.setFont(new Font(20));//字体大小
        Scene scene = new Scene(label);
        scene.setFill(null);//场景透明
        stage.setScene(scene);
    }
}
