package items.runes;

public class WoundingRuneGreater extends WoundingRune {

	public WoundingRuneGreater() {
		super();
		name = "Greater Wounding Rune";
		cost = 20;
		woundRateBuff = 30;
		woundDamageBuff = 2;
		description = "The weapon has +" + woundRateBuff + " Wound Rate and +" + woundDamageBuff + " Wound Damage.";
	}
}
