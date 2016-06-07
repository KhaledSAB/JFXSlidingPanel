package com.khaledsab.slidingpanelexample;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SlidingPanel {

	private Region slidingPanel;
	private TranslateTransition translateTrans;
	private Rectangle clipRect;
	private boolean isSlidingPaneShown = true;
	private NumberBinding invertedYTrans;
	private ChangeListener<Number> slidingPanelHeightListener;

	public SlidingPanel() {}

	public SlidingPanel(Region slidingPanel) {
		this.slidingPanel = slidingPanel;

		translateTrans = new TranslateTransition();
		translateTrans.setDuration(new Duration(500));
		translateTrans.setCycleCount(1);
		translateTrans.setInterpolator(Interpolator.EASE_BOTH);
		translateTrans.setNode(slidingPanel);

		clipRect = new Rectangle(0, 0, slidingPanel.getWidth(), slidingPanel.getHeight());

		clipRect.setLayoutX(0);
		clipRect.setLayoutY(0);
		clipRect.widthProperty().bind(slidingPanel.widthProperty());
		clipRect.heightProperty().bind(slidingPanel.heightProperty());

		invertedYTrans = slidingPanel.translateYProperty().negate();

		slidingPanel.setClip(clipRect);

		slidingPanelHeightListener = (v,ov,nv)->{
			slidingPanel.setTranslateY(-slidingPanel.getHeight());
		};
	}

	/**
	 * Hide the sliding panel
	 */
	public void hidePanel() {
		clipRect.layoutYProperty().bind(invertedYTrans);
		translateTrans.setToY(-slidingPanel.getHeight());
		translateTrans.setOnFinished((event)->{
			slidingPanel.setVisible(false);
			slidingPanel.heightProperty().addListener(slidingPanelHeightListener);
		});
		translateTrans.play();
		isSlidingPaneShown = false;
	}

	/**
	 * show the sliding panel
	 */
	public void showPanel() {
		slidingPanel.heightProperty().removeListener(slidingPanelHeightListener);
		slidingPanel.setVisible(true);
		translateTrans.setToY(0);
		translateTrans.setOnFinished(null);
		translateTrans.play();
		isSlidingPaneShown = true;
	}

	public boolean isSlidingPaneShown() {
		return isSlidingPaneShown;
	}
}
