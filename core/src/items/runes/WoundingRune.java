package items.runes;

import items.weapons.Weapon;

public class WoundingRune extends Rune {

	protected int woundRateBuff;
	protected int woundDamageBuff;
	
	public WoundingRune() {
		name = "Wounding Rune";
		count = 1;
		cost = 10;
		type = Type.RUNE;
		
		woundRateBuff = 20;
		woundDamageBuff = 1;
		description = "The weapon has +" + woundRateBuff + " Wound Rate and +" + woundDamageBuff + " Wound Damage.";
	}
	
	@Override
	public void modifyWeapon(Weapon weapon) {
		weapon.modWoundRate(woundRateBuff);
		weapon.modWoundDamage(woundDamageBuff);
	}

	@Override
	public void revertChanges(Weapon weapon) {
		weapon.modWoundRate(-woundRateBuff);
		weapon.modWoundDamage(-woundDamageBuff);
	}

}
