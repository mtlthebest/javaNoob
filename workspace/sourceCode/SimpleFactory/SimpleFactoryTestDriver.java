package SimpleFactory;

import java.util.ArrayList;

public class SimpleFactoryTestDriver {

	public static void main(String[] args) {

		// 1. - Create a crew made of five seamen, using the simple factory

		ArrayList<Soldier> crew = new ArrayList<Soldier>();

		Ugp mariugp = new MariUgp(new SoldierFactory());

		crew.add(mariugp.deploy("Laffranchi", "Matteo", "S.T.V."));
		crew.add(mariugp.deploy("Oliver", "Atton", "1° M.llo"));
		crew.add(mariugp.deploy("Bulgaro", "Mino", "C° 1^ cl."));
		crew.add(mariugp.deploy("Biscotto", "al Sesamo", "S.T.V."));
		crew.add(mariugp.deploy("Pasta", "alla Norma", "T.V."));

		// 2. - Once the "Seaman" objects have been created, show their data

		for (Soldier man : crew) {
			System.out.println(man.rank + " " + man.surname + " " + man.name);
		}

	}
}
