package items.runes;

import items.weapons.Weapon;

public class PiercingRune extends Rune {

	protected int piercingDamageBuff;
	
	public PiercingRune() {
		name = "Piercing Rune";
		count = 1;
		cost = 12;
		type = Type.RUNE;
		
		piercingDamageBuff = 2;
		description = piercingDamageBuff + " of the weapon's Damage is converted to Piercing Damage (ignores Armor).";
	}
	
	@Override
	public void modifyWeapon(Weapon weapon) {
		weapon.modMinDamage(-piercingDamageBuff);
		weapon.modMaxDamage(-piercingDamageBuff);
		weapon.modPiercingDamage(piercingDamageBuff);
	}

	@Override
	public void revertChanges(Weapon weapon) {
		weapon.modMinDamage(piercingDamageBuff);
		weapon.modMaxDamage(piercingDamageBuff);
		weapon.modPiercingDamage(-piercingDamageBuff);
	}

}
