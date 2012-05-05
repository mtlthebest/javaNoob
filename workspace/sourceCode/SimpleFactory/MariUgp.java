package SimpleFactory;

public class MariUgp extends Ugp {

	MariUgp(SoldierFactory factory) {
		super(factory);
	}

	public Soldier deploy(String surname, String name, String rank) {
		Soldier soldier = this.f.createSoldier(surname, name, rank);
		System.out.println("MARIUGP: deploying SEAMAN \"" + surname + "\"...");
		return soldier;
	}
}
