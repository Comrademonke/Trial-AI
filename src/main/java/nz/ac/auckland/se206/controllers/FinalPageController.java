package nz.ac.auckland.se206.controllers;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.TimerManager;

public class FinalPageController {

  @FXML private Label timer;
  @FXML private VBox overlay;
  @FXML private VBox overlaySuccess;
  @FXML private VBox overlayFailure;
  @FXML private Button yesButton;
  @FXML private Button noButton;
  @FXML private TextArea txtInput;
  @FXML private Button submitButton;
  @FXML private Label optionPickingMessage;
  @FXML private Label optionTextMessage;

  private Timeline timeline;
  private final int totalSeconds = 60;
  private int remainingSeconds = totalSeconds;
  private boolean isYesClicked = false;
  private boolean isNoClicked = false;

  /**
   * Initializes the final page.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    // Stop 2:00 timer and play flashback tts
    TimerManager.getInstance().stop();

    // Erase all text
    optionPickingMessage.setText("");
    optionTextMessage.setText("");

    String audioFile = "src/main/resources/sounds/oneMinuteLeft.mp3";

    Media sound = new Media(new File(audioFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

    mediaPlayer.play();

    submitButton.setDisable(false);

    timer.setText(String.format("01:00"));

    // Start 60s timer
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  remainingSeconds--;
                  timer.setText(String.format("00:%02d", remainingSeconds % 60));

                  if (remainingSeconds <= 0) {
                    timeline.stop();

                    // auto submit everything
                    // showOverlay();
                    String audioFile2 = "src/main/resources/sounds/gameOver.mp3";

                    Media sound2 = new Media(new File(audioFile2).toURI().toString());
                    MediaPlayer mediaPlayer2 = new MediaPlayer(sound2);

                    mediaPlayer2.play();
                  }
                }));
    timeline.setCycleCount(totalSeconds);
    timeline.play();
  }

  private void showOverlay() {
    Platform.runLater(
        () -> {
          overlay.setVisible(true);
        });
  }

  @FXML
  private void onYesClick() {
    yesButton.setScaleX(1.2);
    yesButton.setScaleY(1.2);

    isYesClicked = true;
    isNoClicked = false;
    setYesOrNoClick();

    // overlaySuccess.setVisible(true);
  }

  @FXML
  private void onNoClick() {
    noButton.setScaleX(1.2);
    noButton.setScaleY(1.2);

    isNoClicked = true;
    isYesClicked = false;
    setYesOrNoClick();

    // overlayFailure.setVisible(true);
  }

  private void setYesOrNoClick() {
    optionPickingMessage.setText("");
    // If yes and no are not clicked yet
    if (isNoClicked == false && isYesClicked == false) {
      return;
      // If no is clicked after yes
    } else if (isNoClicked) {
      yesButton.setScaleX(1);
      yesButton.setScaleY(1);
      // If yes is clicked after no
    } else if (isYesClicked) {
      noButton.setScaleX(1);
      noButton.setScaleY(1);
    }
  }

  @FXML
  private void onSendClick() {
    String message = txtInput.getText().trim();

    // Checks for empty string
    if (message.isEmpty()) {
      txtInput.getStyleClass().removeAll("text-area-normal", "text-area-error");
      txtInput.getStyleClass().add("text-area-error");
      optionTextMessage.setText("Please provide an answer");
      return;
    }

    // Check if at least yes or no is clicked
    if (isNoClicked == false && isYesClicked == false) {
      optionPickingMessage.setText("Please Choose Yes or No");
      return;
    }

    txtInput.clear();

    // Disable add buttons and stop timer
    submitButton.setDisable(true);
    timeline.stop();
    yesButton.setDisable(true);
    noButton.setDisable(true);
    txtInput.setDisable(true);

    // Task<Void> task =
    //     new Task<>() {
    //       @Override
    //       protected Void call() {
    //         try {
    //           runGpt(message);
    //         } catch (ApiProxyException e) {
    //           e.printStackTrace();
    //         }
    //         return null;
    //       }
    //     };

    // new Thread(task).start();
  }

  @FXML
  public void sendMessage(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ENTER)) {
      // Send the message
      onSendClick();
    }
  }

  @FXML
  private void onInputStartUp() {
    txtInput.getStyleClass().removeAll("text-area-normal", "text-area-error");
    txtInput.getStyleClass().add("text-area-normal");
    optionTextMessage.setText("");
  }
}
