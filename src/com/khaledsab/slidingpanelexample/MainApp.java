package com.khaledsab.slidingpanelexample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application implements Initializable {

	@FXML private VBox container, fixedPanel, slidingPanel;
	@FXML StackPane panelsContainer;
	@FXML Button slideBtn;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		StackPane.setAlignment(slidingPanel, Pos.TOP_CENTER);

		// clearing the content of the fixed panel and adding an image to it
		fixedPanel.getChildren().clear();
		ImageView javaImg = new ImageView("images/javaLogo.png");
		javaImg.setFitWidth(100);
		javaImg.setPreserveRatio(true);
		fixedPanel.getChildren().add(javaImg);

		// adding background insets ensures the shadows to appear, and also adding a background color
		// instead of the transparent background of the sliding panel
		slidingPanel.setStyle("-fx-background-insets: 0 10 10 10; -fx-background-color: #FAFAFA");
		slidingPanel.setPadding(new Insets(15, 15, 15, 15));

		// setting the size of the sliding panel to be smaller than its container's size to allow shadows to appear
		slidingPanel.maxHeightProperty().bind(panelsContainer.heightProperty().multiply(0.9));
		slidingPanel.maxWidthProperty().bind(panelsContainer.widthProperty().multiply(0.9));

		// Clear all of the content of the sliding panel and add an image to it
		slidingPanel.getChildren().clear();
		ImageView imgView = new ImageView("images/eclipseLogo.png");
		imgView.setFitWidth(100);
		imgView.setPreserveRatio(true);
		slidingPanel.getChildren().add(imgView);
		slidingPanel.setAlignment(Pos.BOTTOM_RIGHT);

		// Adding a shadow to the sliding panel
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(10.0);
		dropShadow.setOffsetX(1.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		slidingPanel.setEffect(dropShadow);

		// This will allow the slidingPanel to become slidable
		SlidingPanel sp = new SlidingPanel(slidingPanel);

		// Adding action to the slide controller button
		slideBtn.setOnAction((event)->{
			if(sp.isSlidingPaneShown()) {
				sp.hidePanel();
				slideBtn.setText("Slide down and show");
			} else {
				sp.showPanel();
				slideBtn.setText("Slide up and hide");
			}
		});
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("SlidingPanelExample.fxml"));
		loader.setController(this);
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
