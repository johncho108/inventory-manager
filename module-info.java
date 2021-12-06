module C482PA {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	exports main to javafx.graphics;
	opens controller to javafx.graphics, javafx.fxml;
	opens model to javafx.base;
}
