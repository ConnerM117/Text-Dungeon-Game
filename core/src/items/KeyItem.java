package items;

public class KeyItem extends Item {

	public KeyItem(String name) {
		this.name = name;
		type = Type.KEY;
	}
	
	@Override
	public String getStatistics() {
		return name;
	}

}
