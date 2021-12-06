package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class implements the Product object.
 * 
 * @author John
 */
public class Product {
	private ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;    
    
    /**
     * This constructor creates a null Product with no parameters.
     */
    public Product () {
    }
    
    /**
     * This constructor creates a Product object with the parameters below.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method gets the Product ID.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the Product ID.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * This method gets the Product Name.
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * This method sets the Product Name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method gets the Product Price.
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method sets the Product Price.
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method gets the Product Stock.
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method sets the Product Stock.
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method gets the Product Min.
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * This method sets the Product Min.
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method gets the Product Max.
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * This method sets the Product Max.
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * This method adds an associated part to the product.
     * @param part
     */
    public void addAssociatedPart(Part part) {
    	associatedParts.add(part);
    }
    
    /**
     * This method removes an associated part from a product.
     * @param selectedAssociatedPart
     * @return boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
    	return associatedParts.remove(selectedAssociatedPart);
    }
    
    /**
     * This method gets the list of all associated parts.
     * @return ObservableList<Part>
     */
    public ObservableList<Part> getAllAssociatedParts() {
    	return associatedParts;
    }
}