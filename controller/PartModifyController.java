package controller;

import java.io.IOException;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * This class implements the controller for the Modify Part scene.
 * 
 * There were initially some errors surrounding the display of the error messages. Previous error messages were being displayed
 * even though they had already been addressed. This error was fixed by setting the errorMsgDisplay label to the empty string 
 * at the beginning of all method call to reset the error display. 
 * 
 * A feature for future implementation is to further restrict user inputs to allow only names/company names that include at least one letter; price/cost
 * to be in a currency (rather than just double) format; and to restrict machine IDs to a certain format, perhaps with dashes like phone numbers.
 * 
 * @author John
 */
public class PartModifyController implements Initializable {
	
	private int id;
	private String name;
	private double price;
	private int inv;
	private int max;
	private int min;
	private int machineID;
	private String companyName;
	private String errorMsg = "";
	private boolean errorExists = false;

	public Label machineOrCompany;
	public RadioButton inHouseBtn;
	public RadioButton outsourcedBtn;
	
	public TextField idField;
	public TextField nameField;
	public TextField priceField;
	public TextField invField;
	public TextField maxField;
	public TextField minField;
	public TextField machineIDField;
	public Label errorMsgDisplay;
	
	/**
	 * This method clears the error message display and sets the label for the last field to "Company Name" when 
	 * the Outsourced radio button is pushed. 
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onInHouse(ActionEvent actionEvent) throws IOException {
		errorMsgDisplay.setText("");
		machineOrCompany.setText("Machine ID");
	}
	
	/**
	 * This method clears the error message display and sets the label for the last field to "Machine ID" when 
	 * the In-House radio button is pushed. 
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onOutsourced(ActionEvent actionEvent) throws IOException {
		errorMsgDisplay.setText("");
		machineOrCompany.setText("Company Name");
	}
	
	/**
	 * This method updates the part, recasting the type of the part to InHouse or Outsourced if necessary, and
	 * saves the information based on user inputs. 
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void onSave(ActionEvent actionEvent) throws IOException {
		errorMsgDisplay.setText("");
		errorMsg = "Error:\n";
		errorExists = false;
		Part selectedPart = MainController.selectedPart;
		
		id = selectedPart.getId();
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
		
		if (MainController.selectedPart instanceof InHouse) {
			if (inHouseBtn.isSelected()) {
				InHouse selectedInHousePart = (InHouse) MainController.selectedPart;
				try {
					machineID = Integer.parseInt(machineIDField.getText());
					if(errorExists == false) {
						selectedInHousePart.setName(name);
						selectedInHousePart.setPrice(price);
						selectedInHousePart.setStock(inv);
						selectedInHousePart.setMin(min);
						selectedInHousePart.setMax(max);
						selectedInHousePart.setMachineID(machineID);
						this.onReturn(actionEvent);
					} else {
						return;
					}
				} catch (Exception e) {
					errorMsg += "Machine ID must be an integer.\n";
					errorMsgDisplay.setText(errorMsg);
					errorExists = true;
					return;
				}
			}
		} else if (MainController.selectedPart instanceof Outsourced){
			if (outsourcedBtn.isSelected()) {
				Outsourced selectedOutsourcedPart = (Outsourced) MainController.selectedPart;
				companyName = machineIDField.getText();
				if (companyName.isEmpty() || companyName.trim().length() == 0) {
					errorMsg += "Company Name is blank.\n";
					errorMsgDisplay.setText(errorMsg);
					errorExists = true;
				}
				if(errorExists == false) {
					selectedOutsourcedPart.setName(name);
					selectedOutsourcedPart.setPrice(price);
					selectedOutsourcedPart.setStock(inv);
					selectedOutsourcedPart.setMin(min);
					selectedOutsourcedPart.setMax(max);
					selectedOutsourcedPart.setCompanyName(companyName);
					this.onReturn(actionEvent);
					return;
				} else {
					return;
				}	
			}
		}
		if (outsourcedBtn.isSelected()) {
			companyName = machineIDField.getText();
			if (companyName.isEmpty() || companyName.trim().length() == 0) {
				errorMsg += "Company Name is blank.\n";
				errorMsgDisplay.setText(errorMsg);
				errorExists = true;
				return;
			}
			if(errorExists == false) {
				Inventory.deletePart(selectedPart);
				Inventory.addPart(new Outsourced(id, name, price, inv, min, max, companyName));
				this.onReturn(actionEvent);
			} else {
				return;
			}
		} else {
			try {
				machineID = Integer.parseInt(machineIDField.getText());
				if(errorExists == false) {
					Inventory.deletePart(selectedPart);
					Inventory.addPart(new InHouse(id, name, price, inv, min, max, machineID));
					this.onReturn(actionEvent);
				} else {
					return;
				}
			} catch (Exception e) {
				errorMsg += "Machine ID must be an integer.\n";
				errorMsgDisplay.setText(errorMsg);
				errorExists = true;
				return;
			}
			
		}
	}
	
	/**
	 * This method returns the user to the Main Inventory scene.
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
	 * This method initializes the Modify Part scene, pre-filling prior information about the part.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errorMsgDisplay.setText("");
		
		if (MainController.selectedPart instanceof InHouse) {
			InHouse selectedInHousePart = (InHouse) MainController.selectedPart;
			machineIDField.setText(Integer.toString(selectedInHousePart.getMachineID()));
			machineOrCompany.setText("Machine ID");
			inHouseBtn.setSelected(true);
		} else {
			Outsourced selectedOutsourcedPart = (Outsourced) MainController.selectedPart;
			machineIDField.setText(selectedOutsourcedPart.getCompanyName());
			machineOrCompany.setText("Company Name");
			outsourcedBtn.setSelected(true);
		}
		Part selectedPart = MainController.selectedPart;
		idField.setPromptText(Integer.toString(selectedPart.getId()));
		nameField.setText(selectedPart.getName());
		priceField.setText(Double.toString(selectedPart.getPrice()));
		invField.setText(Integer.toString(selectedPart.getStock()));
		maxField.setText(Integer.toString(selectedPart.getMax()));
		minField.setText(Integer.toString(selectedPart.getMin()));
	}
}
