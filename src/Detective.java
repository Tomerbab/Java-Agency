public class Detective extends Agent {
	private boolean weaponLicense;

	public Detective (String name, int ID, int experience, boolean canDrive, boolean weaponLicense) { // detective constructor
		this.canDrive = canDrive;
		this.name = name;
		this.experience = experience;
		this.weaponLicense = weaponLicense;
		if(ID < 9999) { // ID condition
			throw new IDNotValidLengthException ("your ID is " + ID + " please enter an ID bigger than 9999"); // RunTime error
		}
		this.ID = ID;
		this.setIsAvailable();
	}
	
	public boolean getWeaponLicense() {
		return this.weaponLicense;
	}
	
	public String toString () {
		return "name: " +name+ ", ID: "+ID+", experience: "+experience+ ", salary: "+salary+", CanDrive: " +canDrive+", weaponLicense: " + weaponLicense + "\n";
	}
}