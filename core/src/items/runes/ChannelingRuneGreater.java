package items.runes;

public class ChannelingRuneGreater extends ChannelingRune {

	public ChannelingRuneGreater() {
		name = "Greater Channeling Rune";
		count = 1;
		cost = 20;
		type = Type.RUNE;
		
		magicDamageBuff = 3;
		description = "Increase your Magic Damage by " + magicDamageBuff;;
	}
	
}
