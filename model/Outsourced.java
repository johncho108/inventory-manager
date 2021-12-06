package model;

/**
 * This class extends the abstract Part class and implements the Outsourced Part object.
 * @author John
 *
 */
public class Outsourced extends Part {
	private String companyName;
	
	/**
	 * This is the constructor, which inherits from the Part constructor. 
	 * 
	 * @param id
	 * @param name
	 * @param price
	 * @param stock
	 * @param min
	 * @param max
	 * @param machineID
	 */
	public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
		super(id, name, price, stock, min, max);
		this.companyName = companyName;
	}
	
	/**
	 * This method sets the Company Name.
	 * @param machineID
	 */
	public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

	/**
	 * This method gets the Company Name.
	 * @return
	 */
    public String getCompanyName() {
        return companyName;
    }
}