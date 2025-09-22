package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.DraggableMaker;

/**
 * Controller class for the chat view. Handles user interactions and communication with the GPT
 * model via the API proxy.
 */
public class DefendantChatController extends ChatControllerCentre {

  @FXML private TextArea txtaChat;
  @FXML private Label timer;
  @FXML private AnchorPane disc1;
  @FXML private AnchorPane disc2;
  @FXML private AnchorPane disc3;
  @FXML private AnchorPane disc4;
  @FXML private AnchorPane disc5;
  @FXML private ImageView basket;
  @FXML private Button gameButton;

  @FXML private VBox flashbackMessage;
  private MediaPlayer mediaPlayer;
  private List<AnchorPane> discs;
  private AnimationTimer gameLoop;
  private int discIndex = 0;

  @Override
  @FXML
  public void initialize() {
    try {
      super.initialize();
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
    flashbackMessage.setVisible(true);
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
    DraggableMaker.makeDraggable(basket);
    discs = Arrays.asList(disc1, disc2, disc3, disc4, disc5);
  }

  private void dropDisc(AnchorPane disc) {
    double minX = 350;
    double maxX = 650;
    double x = minX + Math.random() * (maxX - minX);

    disc.setLayoutY(10);
    disc.setLayoutX(x);
    disc.setVisible(true);
  }

  private void nextDisc() {
    discIndex++;
    if (discIndex < discs.size()) {
      dropDisc(discs.get(discIndex));
    } else {
      basket.setVisible(false);
      System.out.println("FINISHED");
      gameLoop.stop();
      return;
    }
  }

  private void startGame() {
    gameLoop =
        new AnimationTimer() {
          @Override
          public void handle(long now) {
            AnchorPane disc = discs.get(discIndex);

            if (disc.isVisible()) {
              disc.setLayoutY(disc.getLayoutY() + 2);

              if (disc.getBoundsInParent().intersects(basket.getBoundsInParent())) {
                // Disc caught
                disc.setVisible(false);
                nextDisc();
              }

              if (disc.getLayoutY() > 600) {
                // Disc missed
                disc.setVisible(false);
                nextDisc();
              }
            }
          }
        };
    gameLoop.start();
  }

  @FXML
  private void onGameStart(ActionEvent event) {
    basket.setVisible(true);
    gameButton.setVisible(false);

    startGame();
    discIndex = 0;
    dropDisc(discs.get(discIndex));
  }

  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {
    if (this.mediaPlayer != null) {
      this.mediaPlayer.stop();
    }
    App.setRoot("room");
  }
}
