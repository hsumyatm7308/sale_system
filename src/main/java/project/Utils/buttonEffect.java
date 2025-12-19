package project.Utils;

import javafx.animation.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

public class buttonEffect {

    private static buttonEffect instance;

    private static Parent parent;

    public static buttonEffect getInstance(){
        if (instance == null) instance = new buttonEffect();
        return instance;
    }

    public void setPasswordBtn(TextField showPassword, TextField hidePassword, Button showBtn, Button hideBtn){
        FontIcon hideIcon = new FontIcon("fas-eye-slash");
        hideIcon.setIconSize(10);
        hideBtn.setGraphic(hideIcon);

        FontIcon showIcon = new FontIcon("fas-eye");
        showIcon.setIconSize(10);
        showBtn.setGraphic(showIcon);

        showBtn.setOnMouseEntered(event -> {
            showIcon.setIconSize(12);
        });

        showBtn.setOnMouseExited(event -> {
            showIcon.setIconSize(10);
        });
        hideBtn.setOnMouseEntered(event -> {
            hideIcon.setIconSize(12);
        });
        hideBtn.setOnMouseExited(event -> {
            hideIcon.setIconSize(10);
        });

        showBtn.setOnAction(event -> {
            showPassword.setText(hidePassword.getText());
            showPassword.setVisible(true);
            hidePassword.setVisible(false);
            showBtn.setVisible(false);
            hideBtn.setVisible(true);
        });

        hideBtn.setOnAction(event -> {
            hidePassword.setText(showPassword.getText());
            hidePassword.setVisible(true);
            showPassword.setVisible(false);
            hideBtn.setVisible(false);
            showBtn.setVisible(true);
        });
    }

    public void setMenuButton(Parent parent, Button miniBtn, Button sizeBtn , Button exitBtn){
        buttonEffect.parent = parent;

        Tooltip exit = new Tooltip("close");

        FontIcon sizeIcon = new FontIcon("fas-window-restore");
        sizeIcon.setIconSize(12);
        sizeBtn.setGraphic(sizeIcon);

        FontIcon miniIcon = new FontIcon("fas-window-minimize");
        miniIcon.setIconSize(12);
        miniBtn.setGraphic(miniIcon);

        FontIcon exitIcon = new FontIcon("fas-times");
        exitIcon.setIconSize(12);
        exitBtn.setGraphic(exitIcon);
        Tooltip.install(exitBtn, exit);


        exitBtn.setOnAction(event -> {
            exitDelayTime();
        });

        miniBtn.setOnAction(event -> {
            minimizeEffect();
        });
//
//        sizeBtn.setOnAction(event -> {
//            sizeEffect();
//        });
    }

    private void exitDelayTime(){

        Region region = new Region();
        region.setPrefSize(parent.getBoundsInLocal().getWidth(), parent.getBoundsInLocal().getHeight());
        region.setManaged(false);
        region.setMouseTransparent(true);
        region.setOpacity(0);

        if(parent instanceof Pane pane) {
            pane.getChildren().add(region);
            region.toFront();
        }

        GaussianBlur blur = new GaussianBlur(1);
        parent.setEffect(blur);

        Timeline blurIn = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(blur.radiusProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(region.opacityProperty(), 0, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.millis(250),
                        new KeyValue(blur.radiusProperty(), 5, Interpolator.EASE_BOTH),
                        new KeyValue(region.opacityProperty(), 0.5, Interpolator.EASE_BOTH)
                )

        );

        blurIn.setOnFinished(event -> {
            System.exit(0);
        });

        blurIn.play();
    }

    private void minimizeEffect(){
//        Stage stage = (Stage) parent.getScene().getWindow();
//        FadeTransition fade = new FadeTransition(Duration.millis(180), parent);
//        fade.setFromValue(1);
//        fade.setToValue(0);
//
//        ScaleTransition scale = new ScaleTransition(Duration.millis(180), parent);
//        scale.setFromX(1);
//        scale.setFromY(1);
//        scale.setToX(0.85);
//        scale.setToY(0.85);
//
//        ParallelTransition animation = new ParallelTransition(fade, scale);
//
//        animation.setOnFinished(e -> {
//            stage.setIconified(true);
//            parent.setOpacity(1);
//            parent.setScaleX(1);
//            parent.setScaleY(1);
//        });
//
//        animation.play();

        Stage stage = (Stage) parent.getScene().getWindow();

        DropShadow shadow = new DropShadow();
        shadow.setRadius(25);
        shadow.setSpread(0.15);
        shadow.setColor(Color.rgb(0, 0, 0, 0.35));
        parent.setEffect(shadow);


        Duration duration = Duration.millis(380);

        // Fade
        FadeTransition fade = new FadeTransition(duration, parent);
        fade.setFromValue(1.0);
        fade.setToValue(0.02);

        // Scale (uniform)
        ScaleTransition scale = new ScaleTransition(duration, parent);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(0.92);
        scale.setToY(0.92);

        // Move down slightly
        TranslateTransition move = new TranslateTransition(duration, parent);
        move.setFromY(0);
        move.setToY(12);

        // Shadow
        Timeline shadowAnim = new Timeline(
          new KeyFrame(duration,
                  new KeyValue(shadow.radiusProperty(),45, Interpolator.EASE_OUT),
                  new KeyValue(shadow.colorProperty(),Color.rgb(0,0,0,0.15),Interpolator.EASE_OUT)
                  )
        );

        ParallelTransition animation = new ParallelTransition(fade, scale,move,shadowAnim);

        animation.setInterpolator(Interpolator.EASE_OUT);

        animation.setOnFinished(e -> {
            stage.setIconified(true);

        });
        animation.play();



        parent.setVisible(true);
        parent.setOpacity(0);
        parent.setScaleX(0.90);
        parent.setScaleY(0.90);
        parent.setTranslateY(12);

        FadeTransition fadeIn = new FadeTransition(duration, parent);
        fadeIn.setToValue(1.0);

        ScaleTransition scaleIn = new ScaleTransition(duration, parent);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        TranslateTransition moveUp = new TranslateTransition(duration, parent);
        moveUp.setToY(0);

        Timeline shadowBack = new Timeline(
                new KeyFrame(
                        duration,
                        new KeyValue(
                                shadow.colorProperty(),
                                Color.rgb(0, 0, 0, 0.35),
                                Interpolator.EASE_IN
                        )
                )
        );


        ParallelTransition restore = new ParallelTransition(
                fadeIn, scaleIn, moveUp, shadowBack
        );

        restore.setInterpolator(Interpolator.EASE_IN);
        restore.play();



    }

    private void sizeEffect(){
        Stage stage = (Stage) parent.getScene().getWindow();
        double minWidth = 600;
        double minHeight = 500;

        Region region = new Region();
        region.setPrefSize(parent.getBoundsInLocal().getWidth(), parent.getBoundsInLocal().getHeight());
        region.setManaged(false);
        region.setMouseTransparent(true);
        region.setOpacity(0);

        if(parent instanceof Pane pane) {
            pane.getChildren().add(region);
            region.toFront();
        }

        GaussianBlur blur = new GaussianBlur(0);
        parent.setEffect(blur);

        Timeline blurIn = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(blur.radiusProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(region.opacityProperty(), 0, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(blur.radiusProperty(), 2, Interpolator.EASE_BOTH),
                        new KeyValue(region.opacityProperty(), 0.25, Interpolator.EASE_BOTH)
                )

        );
        blurIn.setOnFinished(event -> {
            Timeline blurOut = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(blur.radiusProperty(), 2, Interpolator.EASE_BOTH),
                            new KeyValue(region.opacityProperty(), 0.25, Interpolator.EASE_BOTH)
                    ),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(blur.radiusProperty(), 0, Interpolator.EASE_BOTH),
                            new KeyValue(region.opacityProperty(), 0, Interpolator.EASE_BOTH)
                    )
            );
            blurOut.setOnFinished(e -> {
                if(stage.isMaximized()){
                    stage.setWidth(minWidth);
                    stage.setHeight(minHeight);
                    stage.setMaximized(false);
                }else{
                    stage.setMaximized(true);
                }
                parent.setEffect(null);
                if (parent instanceof Pane p) {
                    p.getChildren().remove(region);
                }
            });
            blurOut.play();
        });
        blurIn.play();

    }

}
