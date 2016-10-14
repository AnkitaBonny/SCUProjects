package mukherjee.assign1;

/**
 * 
 * @author Ankita Mukherjee
 *
 */

public class USMoneyTester {
	public static void main(String[] args) {

		USMoney m1 = new USMoney(15, 80);
		System.out.println(m1);

		m1.addTo(25, 100);
		System.out.println(m1);

		USMoney m2 = m1.add(new USMoney(2, 90));

		System.out.println(m2);
		System.out.println(m1);

		USMoney amt1 = new USMoney();
		System.out.println(amt1);
		amt1.setCents(250);
		System.out.println(amt1);
		amt1.setDollars(10);
		System.out.println(amt1);

		System.out.println(amt1.getCents());

		USMoney amt2 = amt1.add(new USMoney(2, 90));
		System.out.println(amt1);
		System.out.println(amt2);
		amt2.addTo(amt1.getDollars(), amt1.getCents());
		System.out.println(amt2);

	}

}
