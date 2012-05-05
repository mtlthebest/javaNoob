package SimpleFactory;

public abstract class Ugp {

	SoldierFactory f;

	Ugp(SoldierFactory factory) {
		this.f = factory;
	}

	public abstract Soldier deploy(String surname, String name, String rank);

}
