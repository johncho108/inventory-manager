package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class implements the inventory of the application. It stores the user-added parts and products in memory during the execution
 * of the application. 
 * 
 * A feature for future implementation is to implement the inventory in a database so that the data persists once the application is
 * terminated. This feature would make this application a viable product for customers to use for their businesses.
 * 
 * @author John
 *
 */
public class Inventory {
	private static ObservableList<Part> allParts = FXCollections.observableArrayList();
	private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
	public static int partID = 1;
	public static int productID = 1;
	
	/**
	 * This method adds a part to inventory.
	 * 
	 * @param newPart
	 */
	public static void addPart(Part newPart) {
		allParts.add(newPart);
	}
	
	/**
	 * This method adds a product to inventory.
	 * 
	 * @param newPart
	 */
	public static void addProduct(Product newProduct) {
		allProducts.add(newProduct);
	}
	
	/**
	 * This method looks up a part by part ID.
	 * 
	 * @param newPart
	 */
	public static Part lookupPart(int partID) {
		for (Part p : allParts) {
			if(p.getId() == partID) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * This method looks up a product by product ID.
	 * 
	 * @param newPart
	 */
	public static Product lookupProduct(int productID) {
		for (Product p : allProducts) {
			if(p.getId() == productID) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * This method looks up a part by name.
	 * 
	 * @param newPart
	 */
	public static ObservableList<Part> lookupPart(String partName) {
		ObservableList<Part> results = FXCollections.observableArrayList();
		ObservableList<Part> allParts = Inventory.getAllParts();
		
		for (Part p : allParts) {
			if(p.getName().contains(partName)) {
				results.add(p);
			}
		}
		return results;
	}
	
	/**
	 * This method looks up a product by name.
	 * 
	 * @param newPart
	 */
	public static ObservableList<Product> lookupProduct(String productName) {
		ObservableList<Product> results = FXCollections.observableArrayList();
		ObservableList<Product> allProducts = Inventory.getAllProducts();
		
		for (Product p : allProducts) {
			if(p.getName().contains(productName)) {
				results.add(p);
			}
		}
		return results;
	}
	
	/**
	 * This method updates a part in inventory.
	 * 
	 * @param newPart
	 */
	public static void updatePart(int index, Part selectedPart) {
		ObservableList<Part> allParts = Inventory.getAllParts();
		allParts.set(index, selectedPart);
		return;
	}
	
	/**
	 * This method updates a product in inventory.
	 * 
	 * @param newPart
	 */
	public static void updateProduct(int index, Product newProduct) {
		ObservableList<Product> allProducts = Inventory.getAllProducts();
		allProducts.set(index, newProduct);
		return;
	}
	
	/**
	 * This method removes a part from inventory.
	 * 
	 * @param newPart
	 */
	public static boolean deletePart(Part selectedPart) {
		return allParts.remove(selectedPart);
	}
	
	/**
	 * This method removes a product from inventory.
	 * 
	 * @param newPart
	 */
	public static boolean deleteProduct(Product selectedProduct) {
		return allProducts.remove(selectedProduct);
	}
	
	/**
	 * This method gets the list of all parts. 
	 * 
	 * @param newPart
	 */
	public static ObservableList<Part> getAllParts() {
		return allParts;
	}
	
	/**
	 * This method gets the list of all products.
	 * 
	 * @param newPart
	 */
	public static ObservableList<Product> getAllProducts() {
		return allProducts;
	}
}