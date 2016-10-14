package mukherjee.assign1;

/**
 * 
 * @author Ankita Mukherjee
 *
 */


public class USMoney {
	
	private int dollars;
	private int cents;
	
	// default constructor
	public USMoney() { 

		dollars = 0;
		cents = 0;
	}

	/**
	 * 
	 * @param dollars
	 * @param cents
	 */
	public USMoney(int dollars, int cents) {

		if (cents >= 0 && cents <= 99) { // check if cents is in between 0 and 99  
			this.dollars = dollars;
			this.cents = cents;
		} else if (cents >= 100) { // check if cents is not between 0 and 99 
			this.cents = cents % 100;// assign cents in between 0 and 99
			this.dollars = dollars + cents / 100;// transfer remaining cents to dollar
													
		}

	}
/** 
 * Method to set dollars
 * 
 * @param dollars specifies the current dollar value to be set
 * 
 */
	public void setDollars(int dollars) {
		this.dollars += dollars;
	}
/**
 * Method to set cents
 * 
 * @param cents specifies the current cents value to be set
 */
	public void setCents(int cents) {
		if (cents >= 100) { // check if cents is greater than or equal to 100
			this.cents = cents % 100; //keep cents in between 0 and 99
			this.dollars = this.dollars + cents / 100; // transfer remaining cents to dollar
														
		} else if (cents >= 0 && cents <= 99) {  // check if cents is in between 0 and 99 
			this.cents = cents;  //keep cents in between 0 and 99
		}
	}
	
/** 
 * Method to get dollars
 * 
 * @return dollars
 */
	public int getDollars() {
		return dollars;
	}

	/** 
	 * Method to get cents
	 * @return cents
	 */
	public int getCents() {
		return cents;
	}

	/** 
	 * Method to add parameter values to data member values
	 * 
	 * @param dollars parameter value should be added to the data members current dollars value
	 * @param cents   parameter value should be added to the data members current cents value
	 */
	public void addTo(int dollars, int cents) {

		int sumDollars = this.dollars + dollars;
		int sumCents = this.cents + cents;

		if (sumCents >= 0 && sumCents <= 99) // check if cents is in between 0 and 99 

		{
			this.dollars = sumDollars;
			this.cents = sumCents;

		}

		else if (sumCents >= 100) { // check if cents is not between 0 and 99 
			this.dollars = sumDollars +sumCents / 100; // transfer remaining cents to dollar
			this.cents = sumCents % 100;// keep cents in between 0 and 99

		} else {
			System.out.println("invalid");
		}
	}
/** 
 * Method creates and returns a new USMoney object representing the sum of the object whose
 * add() is invoked and the object's ( parameter ) data members values 
 * 
 * @param  money new object 
 * @return new object of type USMoney
 */
	public USMoney add(USMoney money) {

		int sumDollars = this.dollars + money.dollars; // sum of current dollar value of data members and object's dollar value  
		int sumCents = this.cents + money.cents; // sum of current cents value of data members and object's cents value

		if (sumCents >= 0 && sumCents<= 99) // check if cents is in between 0 and 99 

		{
			money.dollars = sumDollars;
			money.cents = sumCents;

		}

		else if (sumCents >= 100) {  // check if cents is not between 0 and 99 
			money.dollars = sumDollars + sumCents / 100;  // transfer remaining cents to dollar
			money.cents =sumCents % 100; // keep cents in between 0 and 99

		} else {
			System.out.println("invalid");
		}
		return money;

	}

	/** 
	 * Method to return String representation of object
	 * 
	 * @return String
	 */
	public String toString() {
		if (cents >= 0 && cents < 10 && dollars >= 0) {
			return "$"+dollars+"." +"0"+cents;
		} else if (cents >= 10 && dollars >= 0) {
			return "$"+dollars+"." + cents;
		} else {
			return " invalid ";
		}
	}

}