package items.runes;

public class PiercingRuneGreater extends PiercingRune {

	public PiercingRuneGreater() {
		name = "Greater Piercing Rune";
		count = 1;
		cost = 20;
		type = Type.RUNE;
		
		piercingDamageBuff = 3;
		description = piercingDamageBuff + " of the weapon's Damage is converted to Piercing Damage (ignores Armor).";
	}
}
