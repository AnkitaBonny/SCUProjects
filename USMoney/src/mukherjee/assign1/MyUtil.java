package mukherjee.assign1;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class MyUtil {

	/**
	 * Method takes integer array as parameter and after creation of loop return
	 * string as a result
	 * 
	 * @param array integer array as parameter           
	 * 
	 * @return String surround by braces and separated by commas
	 */
	public static String displayArray(int[] array) {

		StringBuilder builder = new StringBuilder("{");
		for (int i = 0; i < array.length; i++) {
			builder.append((array[i])).append(",");
		}
		return builder.substring(0, builder.length() - 1).concat("}");

	}

	/**
	 * Method takes an positive integer ,reverse the value and display
	 * 
	 * @param aNumber
	 */

	public static void reverseANumber(int aNumber) {
		int reverse = 0;
		int remainder = 0;
		do {
			remainder = aNumber % 10;
			reverse = reverse * 10 + remainder;
			aNumber = aNumber / 10;
		} while (aNumber > 0);

		System.out.println(reverse);
	}

	public static void main(String[] args) {

		int[] myArray = { 2, 90, 14, 15 };
		String str = displayArray(myArray);
		System.out.println(str);

		reverseANumber(12345);
	}

}
