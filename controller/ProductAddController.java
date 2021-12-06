package controller;

import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * This class implements the controller for the Add Product scene.
 * 
 * There were initially some errors in which parts that were added to the associated parts table were added multiple times for one "add" action. 
 * These errors were corrected by removing erroneous, duplicate calls to the addAssociatedPart method.
 * 
 * A feature for future implementation is to prevent users from adding the same part multiple times to a product. The associated parts of a 
 * product should ideally reflect which parts are being used, not the number of times those parts are being used in a product, so a user
 * should ideally not be able to add the same part to an associated list multiple times.
 * 
 * @author John
 */
public class ProductAddController implements Initializable {
	private Product newProduct = new Product();
	private String name;
	private double price;
	private int inv;
	private int max;
	private int min;
	private int machineID;
	private String errorMsg;
	private boolean errorExists;
	
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
	
	public TextField nameField;
	public TextField priceField;
	public TextField invField;
	public TextField maxField;
	public TextField minField;
	public TextField machineIDField;
	public TextField partSearch;
	
	public Button addBtn;
	public Button saveBtn;
	public Button cancelBtn;
	public Button removeAssociatedBtn;
	
	public Label errorMsgDisplay;

	/**
	 * This method adds a part to the associated parts of the product and updates the associated parts table.
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onAdd(ActionEvent actionEvent) throws IOException {
		errorMsgDisplay.setText("");
		Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
		if(selectedPart == null){
			return;
		}
		newProduct.addAssociatedPart(selectedPart);
		productsTable.setItems(newProduct.getAllAssociatedParts());
	}
	
	/**
	 * This method creates the product, stores it in inventory, and saves the information based on user inputs. 
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onSave(ActionEvent actionEvent) throws IOException {
		errorMsgDisplay.setText("");
		errorMsg = "Error:\n";
		errorExists = false;

		name = nameField.getText();
		if (name.isEmpty() || name.trim().length() == 0) {
			errorMsg += "Name is blank.\n";
			errorMsgDisplay.setText(errorMsg);
			errorExists = true;
		}
		
		try {
			inv = Integer.parseInt(invField.getText());
		} catch (Exception e) {
			errorMsg += "Inventory must be an integer value.\n";
			errorMsgDisplay.setText(errorMsg);
			errorExists = true;
		}
		
		try {
			price = Double.parseDouble(priceField.getText());
		} catch (Exception e) {
			errorMsg += "Price must be a numeric value\n";
			errorMsgDisplay.setText(errorMsg);
			errorExists = true;
		}
		
		try {
			max = Integer.parseInt(maxField.getText());
		} catch (Exception e) {
			errorMsg += "Max must be an integer value.\n";
			errorMsgDisplay.setText(errorMsg);
			errorExists = true;
		}
		
		try {
			min = Integer.parseInt(minField.getText());
		} catch (Exception e) {
			errorMsg += "Min must be an integer value.\n";
			errorMsgDisplay.setText(errorMsg);
			errorExists = true;
		}
		
		if (inv > max || inv < min) {
			errorMsg += "Inventory must fall between min and max, inclusive.\n";
			errorMsgDisplay.setText(errorMsg);
			errorExists = true;
		}
		
		if (min > max) {
			errorMsg += "Max must be greater than min.\n";
			errorMsgDisplay.setText(errorMsg);
			errorExists = true;
		}
		
		if (errorExists == false) {
			newProduct.setId(Inventory.productID);
			newProduct.setName(name);
			newProduct.setStock(inv);
			newProduct.setPrice(price);
			newProduct.setMax(max);
			newProduct.setMin(min);
			Inventory.addProduct(newProduct);
			Inventory.productID += 1;
			this.onReturn(actionEvent);
		}
		return;
	}
	
	/**
	 * This method returns the user to the Main Inventory scene.
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onReturn(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 1200, 500, Color.WHITESMOKE);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/** 
	 * This method removes an associated part from the product and updates the associated parts table.
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onRemoveAssociated(ActionEvent actionEvent) throws IOException {
		errorMsgDisplay.setText("");
		Part selectedPart = (Part) productsTable.getSelectionModel().getSelectedItem();
		if(selectedPart == null){
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Remove");
		alert.setHeaderText("Remove");
		alert.setContentText("Are you sure you want to remove this part?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.isPresent() && result.get() == ButtonType.OK) {
			newProduct.deleteAssociatedPart(selectedPart);
			productsTable.setItems(newProduct.getAllAssociatedParts());
		} else {
			errorMsgDisplay.setText("No part was deleted.");
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
	 * This method initializes the Add Product scene.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errorMsgDisplay.setText("");
		
		partsTable.setItems(Inventory.getAllParts());
		
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
