package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * This class implements the controller for the Main Inventory scene.
 * 
 * There were initially some errors with the search functionality. When users searched for parts/products that didn't exist,
 * the table(s) showed the full list of parts/products. This error was fixed by making sure to set the table items to null
 * when the search returned no results. 
 * 
 * A feature for future implementation is to prevent the deletion of a part that is associated with a product. Currently, the Main
 * Controller prevents the deletion of a product that has associated parts, but not the other way around. If a part is deleted, the
 * products are fundamentally changed because their associated parts are changed. 
 * 
 * @author John Cho
 */
public class MainController implements Initializable {
	public TableView partsTable;
	public TableColumn partsIDCol;
	public TableColumn partsNameCol;
	public TableColumn partsInventoryCol;
	public TableColumn partsUnitPriceCol;
	public TableView productsTable;
	public TableColumn productsIDCol;
	public TableColumn productsNameCol;
	public TableColumn productsInventoryCol;
	public TableColumn productsUnitPriceCol;
	
	public Button partsAdd;
	public Button partsModify;
	public Button partsDelete;
	public Button productsAdd;
	public Button productsModify;
	public Button productsDelete;
	public Button inventoryExit;
	public TextField partSearch;
	public TextField productSearch;
	public Label errorMsgDisplay;
	
	public static Part selectedPart;
	public static InHouse selectedInHousePart;
	public static Outsourced selectedOutsourcedPart;
	public static Product selectedProduct;
	
	/**
	 * This method directs the user to the Add Part scene. 
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onPartsAdd(ActionEvent actionEvent) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/view/PartAdd.fxml"));
		Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 600, Color.WHITESMOKE);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This method directs the user to the Modify Part scene.
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onPartsModify(ActionEvent actionEvent) throws IOException{
		selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
		
		if(selectedPart == null){
			return;
		}
		
		if(selectedPart instanceof InHouse) {
			selectedInHousePart = (InHouse) selectedPart;
		} else {
			selectedOutsourcedPart = (Outsourced) selectedPart;
		}
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/PartModify.fxml"));
		Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 600, 600, Color.WHITESMOKE);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This method deletes the selected part (upon user confirmation).
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onPartsDelete(ActionEvent actionEvent) throws IOException{
		errorMsgDisplay.setText("");
		Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
		if(selectedPart == null){
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete");
		alert.setContentText("Are you sure you want to delete this part?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.isPresent() && result.get() == ButtonType.OK) {
			Inventory.deletePart(selectedPart);
			Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
			Scene scene = new Scene(root, 1200, 500, Color.WHITESMOKE);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} else {
			errorMsgDisplay.setText("No part was deleted.");
		}

	}
	
	/**
	 * This method directs the user to the Add Product scene.
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onProductsAdd(ActionEvent actionEvent) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/view/ProductAdd.fxml"));
		Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 1200, 700, Color.WHITESMOKE);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This method directs the user to the Modify Product scene.
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onProductsModify(ActionEvent actionEvent) throws IOException{
		selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
		if(selectedProduct == null){
			return;
		}
		Parent root = FXMLLoader.load(getClass().getResource("/view/ProductModify.fxml"));
		Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 1200, 700, Color.WHITESMOKE);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This method deletes the selected product (upon user confirmation, if the product has no associated parts).
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onProductsDelete(ActionEvent actionEvent) throws IOException{
		errorMsgDisplay.setText("");
		Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
		if(selectedProduct == null){
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete");
		alert.setContentText("Are you sure you want to delete this product?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.isPresent() && result.get() == ButtonType.OK) {
			if(selectedProduct.getAllAssociatedParts().size() != 0) {
				errorMsgDisplay.setText("This product has at least one associated part. Please remove all associated parts.");
				return;
			} else {
				Inventory.deleteProduct(selectedProduct);
				Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
				Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
				Scene scene = new Scene(root, 1200, 500, Color.WHITESMOKE);
				stage.setResizable(false);
				stage.setScene(scene);
				stage.show();
			}
			
		} else {
			errorMsgDisplay.setText("No part was deleted.");
			return;
		}
	}
	
	/**
	 * This method allows users to search for parts by ID or name.
	 * 
	 * @param actionEvent
	 */
	public void onPartsSearch(ActionEvent actionEvent) {
		errorMsgDisplay.setText("");
		String q = partSearch.getText();
		if (q == "") {
			partsTable.setItems(Inventory.getAllParts());
			return;
		}
		if ("0123456789".contains(q)) {
			Part result = Inventory.lookupPart(Integer.parseInt(q));
			if (result != null) {
				ObservableList<Part> results = FXCollections.observableArrayList();
				results.add(result);
				partsTable.setItems(results);
				return;
			} 
		} else {
			ObservableList<Part> results = Inventory.lookupPart(q);
			if (results.size() != 0) {
				partsTable.setItems(results);
				return;
			}
		}
		partsTable.setItems(null);
		errorMsgDisplay.setText("No search results matched your query.");
	}
	
	/**
	 * This method allows users to search for products by ID or name.
	 * 
	 * @param actionEvent
	 */
	public void onProductsSearch(ActionEvent actionEvent) {
		errorMsgDisplay.setText("");
		String q = productSearch.getText();
		if (q == "") {
			productsTable.setItems(Inventory.getAllProducts());
			return;
		}
		if ("0123456789".contains(q)) {
			Product result = Inventory.lookupProduct(Integer.parseInt(q));
			if (result != null) {
				ObservableList<Product> results = FXCollections.observableArrayList();
				results.add(result);
				productsTable.setItems(results);
				return;
			} 
		} else {
			ObservableList<Product> results = Inventory.lookupProduct(q);
			if (results.size() != 0) {
				productsTable.setItems(results);
				return;
			}
		}
		productsTable.setItems(null);
		errorMsgDisplay.setText("No search results matched your query.");
		
	}
	
	/**
	 * This method terminates the program.
	 * 
	 * @param actionEvent
	 */
	public void onInventoryExit(ActionEvent actionEvent){
		Platform.exit();
	}
	
	/**
	 * This method initializes the Main Inventory scene. 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errorMsgDisplay.setText("");
	
		partsTable.setItems(Inventory.getAllParts());
		productsTable.setItems(Inventory.getAllProducts());
		
		partsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
		partsUnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		productsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		productsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
		productsUnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));	
		
		partsTable.getSortOrder().add(partsIDCol);
		partsTable.sort();
		
	}
	
}
