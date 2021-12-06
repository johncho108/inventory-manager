package model;

/**
 * This class extends the abstract Part class and implements the InHouse Part object.
 * @author John
 *
 */
public class InHouse extends Part {
	private int machineID;
	
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
	public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
		super(id, name, price, stock, min, max);
		this.machineID = machineID;
	}
	
	/**
	 * This method sets the Machine ID.
	 * @param machineID
	 */
	public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
	
	/**
	 * This method gets the Machine ID.
	 * @return
	 */
    public int getMachineID() {
        return machineID;
    }

}