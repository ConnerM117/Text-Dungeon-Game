package items.runes;

import com.textdungeon.game.GameScreen;

import items.weapons.Weapon;

public class KeenRune extends Rune {

	protected int minDamageBuff;
	private int originalMinDamage;

	public KeenRune() {
		name = "Keen Rune";
		count = 1;
		cost = 10;
		type = Type.RUNE;

		minDamageBuff = GameScreen.generateRandom(1, 3);
		description = "Raise the weapon's minimum damage by " + minDamageBuff;
	}

	@Override
	public void modifyWeapon(Weapon weapon) {
		originalMinDamage = weapon.getMinDamage();
		weapon.modMinDamage(minDamageBuff);
		if (weapon.getMinDamage() > weapon.getMaxDamage())
			weapon.modMinDamage(-(weapon.getMinDamage() - weapon.getMaxDamage()));
	}

	@Override
	public void revertChanges(Weapon weapon) {
		weapon.setMinDamage(originalMinDamage);
	}

}
