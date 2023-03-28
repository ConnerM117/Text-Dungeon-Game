package items.runes;

import com.textdungeon.game.GameScreen;

import items.weapons.Weapon;

public class WeightRune extends Rune {

	protected int damageBuff;
	protected int accuracyDebuff;
	
	public WeightRune() {
		name = "Weight Rune";
		count = 1;
		cost = 10;
		type = Type.RUNE;
		
		damageBuff = 2;
		accuracyDebuff = 9 + GameScreen.generateRandom(1, 4);
		description = "The weapon's weight is increased, reducing Accuracy by " + accuracyDebuff + ", but increasing "
				+ "damage by " + damageBuff;
	}
	
	@Override
	public void modifyWeapon(Weapon weapon) {
		weapon.modAccuracyMod(-accuracyDebuff);
		weapon.modMinDamage(damageBuff);
		weapon.modMaxDamage(damageBuff);
	}

	@Override
	public void revertChanges(Weapon weapon) {
		weapon.modAccuracyMod(accuracyDebuff);
		weapon.modMinDamage(-damageBuff);
		weapon.modMaxDamage(-damageBuff);
	}

}
