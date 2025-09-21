package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.DraggableMaker;

/**
 * Controller class for the chat view. Handles user interactions and communication with the GPT
 * model via the API proxy.
 */
public class HumanWitnessMemoryController extends ChatControllerCentre {

  private int currentSongIndex = 0;
  private String[] songs = {
    "Song 1", "Song 2", "Song 3", "Song 4", "Song 5", "Song 6", "Song 7", "Song 8"
  };
  Image image1 = new Image(getClass().getResourceAsStream("/images/carRadio.jpg"));
  Image image2 = new Image(getClass().getResourceAsStream("/images/cityLights.jpg"));
  Image image3 = new Image(getClass().getResourceAsStream("/images/catSleeping.jpg"));
  Image image4 = new Image(getClass().getResourceAsStream("/images/coffeeMug.jpg"));
  Image image5 = new Image(getClass().getResourceAsStream("/images/concert.jpg"));
  Image image6 = new Image(getClass().getResourceAsStream("/images/deers.jpg"));
  Image image7 = new Image(getClass().getResourceAsStream("/images/ocean.jpg"));
  Image image8 = new Image(getClass().getResourceAsStream("/images/eggs.jpg"));

  @FXML private TextArea txtaChat;
  @FXML private Label timer;
  @FXML private ImageView cassetteTape;
  @FXML private VBox flashbackMessage;
  @FXML private Label songLabel;
  @FXML private ImageView musicCover;

  @Override
  @FXML
  public void initialize() {
    try {
      super.initialize();
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }

    // Disable and turn visibility off the cassette tape
    cassetteTape.setVisible(false);
    cassetteTape.setDisable(true);

    // Set initial song
    songLabel.setText(songs[currentSongIndex]);

    // Display flashback message at the beginning
    flashbackMessage.setVisible(true);
    Platform.runLater(
        () -> {
          PauseTransition pause = new PauseTransition(Duration.seconds(1));
          pause.setOnFinished(e -> flashbackMessage.setVisible(false));
          pause.play();
        });

    DraggableMaker.makeDraggable(cassetteTape);
  }

  @FXML
  private void turnOnCassetteTape() {
    // Enable and turn visibility on the cassette tape
    cassetteTape.setVisible(true);
    cassetteTape.setDisable(false);
  }

  @FXML
  private void goToNextSong() {
    // Fade transitions
    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), songLabel);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);

    FadeTransition fadeIn = new FadeTransition(Duration.millis(300), songLabel);
    fadeIn.setFromValue(0.0);
    fadeIn.setToValue(1.0);

    // Play animation
    fadeOut.setOnFinished(
        e -> {
          // Update label
          currentSongIndex = (currentSongIndex + 1) % songs.length;
          setImageCover(currentSongIndex);
          songLabel.setText(songs[currentSongIndex]);
          fadeIn.play();
        });

    fadeOut.play();
  }

  @FXML
  private void goToPreviousSong() {
    // Fade transitions
    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), songLabel);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);

    FadeTransition fadeIn = new FadeTransition(Duration.millis(300), songLabel);
    fadeIn.setFromValue(0.0);
    fadeIn.setToValue(1.0);

    // Play animation
    fadeOut.setOnFinished(
        e -> {
          // Update label
          currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length;
          setImageCover(currentSongIndex);
          songLabel.setText(songs[currentSongIndex]);
          fadeIn.play();
        });

    fadeOut.play();
  }

  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {
    App.setRoot("room");
  }

  private void setImageCover(int index) {
    // Switches image cover to match song name
    if (index == 0) {
      musicCover.setImage(image1);
    } else if (index == 1) {
      musicCover.setImage(image2);
    } else if (index == 2) {
      musicCover.setImage(image3);
    } else if (index == 3) {
      musicCover.setImage(image4);
    } else if (index == 4) {
      musicCover.setImage(image5);
    } else if (index == 5) {
      musicCover.setImage(image6);
    } else if (index == 6) {
      musicCover.setImage(image7);
    } else if (index == 7) {
      musicCover.setImage(image8);
    }
  }
}
