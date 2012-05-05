package SimpleFactory;

public class SoldierFactory {

	public Soldier createSoldier(String surname, String name, String rank)
	{
		return new Soldier(surname, name, rank);
	}
	
}
