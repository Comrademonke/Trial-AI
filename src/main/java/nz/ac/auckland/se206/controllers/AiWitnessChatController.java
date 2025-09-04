package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.DraggableMaker;

/**
 * Controller class for the AI Witness chat view. Handles user interactions and communication with
 * the GPT model via the API proxy.
 */
public class AiWitnessChatController extends ChatControllerCentre {

  @FXML private TextArea txtaChat;
  @FXML private Slider slider;
  @FXML private VBox flashbackMessage;
  private final Map<ImageView, Label> speechBubbleLabels = new HashMap<>();

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
    setupSpeechBubbleTexts();
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

    // Hide all labels
    for (Label label : speechBubbleLabels.values()) {
      label.setVisible(false);
    }
  }

  private void addTextToSpeechBubble(ImageView bubble, String text) {
    // Create label
    Label label = new Label(text);
    label.setWrapText(true);
    label.setTextAlignment(TextAlignment.CENTER);
    label.setAlignment(Pos.CENTER);
    label.setPrefWidth(bubble.getFitWidth());
    label.setPrefHeight(bubble.getFitHeight());

    // Create StackPane to hold both bubble and text
    StackPane stack = new StackPane();
    stack.setLayoutX(bubble.getLayoutX());
    stack.setLayoutY(bubble.getLayoutY());

    // Remove bubble from its current parent and add to stack
    AnchorPane parent = (AnchorPane) bubble.getParent();
    parent.getChildren().remove(bubble);
    stack.getChildren().addAll(bubble, label);
    parent.getChildren().add(stack);

    // Reset the layout
    bubble.setLayoutX(0);
    bubble.setLayoutY(0);

    label.setVisible(false); // Always start hidden
    speechBubbleLabels.put(bubble, label);
  }

  private void setupSpeechBubbleTexts() {
    addTextToSpeechBubble(speechBubble1, "The latest project was...");
    addTextToSpeechBubble(speechBubble2, "I heard they were working on AI");
    addTextToSpeechBubble(speechBubble3, "Some kind of AI system");
    addTextToSpeechBubble(speechBubble4, "The project involved");
    addTextToSpeechBubble(speechBubble5, "Training on data");
    addTextToSpeechBubble(speechBubble6, "Musicians weren't happy");
    addTextToSpeechBubble(speechBubble7, "Something about Music...");
    addTextToSpeechBubble(speechBubble8, "Using AI to generate");
    addTextToSpeechBubble(speechBubble9, "Without permission...");
    addTextToSpeechBubble(speechBubble10, "Copying their style");
    addTextToSpeechBubble(speechBubble11, "It was unethical");
    addTextToSpeechBubble(speechBubble12, "The whole story is clear now...");
  }

  private void showSpeechBubble(int value) {
    hideAllSpeechBubbles();
    if (value >= 2) {
      showBubbleWithText(speechBubble2);
    }
    if (value >= 3) {
      showBubbleWithText(speechBubble1);
    }
    if (value >= 4) {
      showBubbleWithText(speechBubble7);
    }
    if (value >= 5) {
      showBubbleWithText(speechBubble4);
    }
    if (value >= 6) {
      showBubbleWithText(speechBubble8);
    }
    if (value >= 7) {
      showBubbleWithText(speechBubble5);
    }
    if (value >= 8) {
      showBubbleWithText(speechBubble3);
    }
    if (value >= 9) {
      showBubbleWithText(speechBubble9);
      showBubbleWithText(speechBubble10);
    }
    if (value >= 10) {
      showBubbleWithText(speechBubble6);
      showBubbleWithText(speechBubble11);
    }
    if (value >= 11) {
      showBubbleWithText(speechBubble12);
      clearNoiseBtn.setVisible(true);
      slider.setVisible(false);
    }
  }

  private void showBubbleWithText(ImageView bubble) {
    bubble.setVisible(true);
    Label label = speechBubbleLabels.get(bubble);
    if (label != null) {
      label.setVisible(true);
    }
  }

  @FXML
  private void onClearNoiseBtnClick() {
    // Make the StackPanes containing bubbles and text draggable
    for (ImageView bubble :
        new ImageView[] {
          speechBubble1, speechBubble2, speechBubble3, speechBubble4,
          speechBubble5, speechBubble6, speechBubble7, speechBubble8,
          speechBubble9, speechBubble10, speechBubble11, speechBubble12
        }) {
      if (bubble.getParent() instanceof StackPane) {
        DraggableMaker.makeDraggable((StackPane) bubble.getParent());
      }
    }
  }
}
