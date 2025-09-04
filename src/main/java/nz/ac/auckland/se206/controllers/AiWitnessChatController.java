package nz.ac.auckland.se206.controllers;

import java.io.File;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;

/**
 * Controller class for the AI Witness chat view. Handles user interactions and communication with
 * the GPT model via the API proxy.
 */
public class AiWitnessChatController extends ChatControllerCentre {

  @FXML private TextArea txtaChat;
  @FXML private Slider slider;
  @FXML private VBox flashbackMessage;
  @FXML private ImageView speechBubble1;
  @FXML private ImageView speechBubble2;
  @FXML private ImageView speechBubble3;
  @FXML private ImageView speechBubble4;
  @FXML private ImageView speechBubble5;
  @FXML private ImageView speechBubble6;
  @FXML private ImageView speechBubble7;
  @FXML private ImageView speechBubble8;
  @FXML private ImageView speechBubble9;
  @FXML private ImageView speechBubble10;
  @FXML private ImageView speechBubble11;
  @FXML private ImageView speechBubble12;
  @FXML private Button clearNoiseBtn;

  @Override
  @FXML
  public void initialize() {
    try {
      super.initialize();
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
    flashbackMessage.setVisible(true);
    hideAllSpeechBubbles();
    clearNoiseBtn.setVisible(false);

    slider
        .valueProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              showSpeechBubble(newVal.intValue());
            });

    Platform.runLater(
        () -> {
          PauseTransition pause = new PauseTransition(Duration.seconds(1));
          pause.setOnFinished(e -> flashbackMessage.setVisible(false));
          String audioFile = "src/main/resources/sounds/flashback.mp3";

          Media sound = new Media(new File(audioFile).toURI().toString());
          MediaPlayer mediaPlayer = new MediaPlayer(sound);

          mediaPlayer.play();
          pause.play();
        });
  }

  private void hideAllSpeechBubbles() {
    speechBubble1.setVisible(false);
    speechBubble2.setVisible(false);
    speechBubble3.setVisible(false);
    speechBubble4.setVisible(false);
    speechBubble5.setVisible(false);
    speechBubble6.setVisible(false);
    speechBubble7.setVisible(false);
    speechBubble8.setVisible(false);
    speechBubble9.setVisible(false);
    speechBubble10.setVisible(false);
    speechBubble11.setVisible(false);
    speechBubble12.setVisible(false);
  }

  private void showSpeechBubble(int value) {
    hideAllSpeechBubbles();
    if (value >= 2) {
      speechBubble2.setVisible(true);
    }
    if (value >= 3) {
      speechBubble1.setVisible(true);
    }
    if (value >= 4) {
      speechBubble7.setVisible(true);
    }
    if (value >= 5) {
      speechBubble4.setVisible(true);
    }
    if (value >= 6) {
      speechBubble8.setVisible(true);
    }
    if (value >= 7) {
      speechBubble5.setVisible(true);
    }
    if (value >= 8) {
      speechBubble3.setVisible(true);
    }
    if (value >= 9) {
      speechBubble9.setVisible(true);
      speechBubble10.setVisible(true);
    }
    if (value >= 10) {
      speechBubble6.setVisible(true);
      speechBubble11.setVisible(true);
    }
    if (value >= 11) {
      speechBubble12.setVisible(true);
      clearNoiseBtn.setVisible(true);
      slider.setVisible(false);
    }
  }
}
