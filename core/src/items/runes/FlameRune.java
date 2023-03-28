package items.runes;

import items.weapons.Weapon;
import mobs.Player;

public class FlameRune extends Rune {

	protected int burnDamage;
	private int weaponBurningRounds;
	
	public FlameRune() {
		name = "Flame Rune";
		description = "The weapon is permanently engulfed in flames.";
		count = 1;
		cost = 10;
		type = Type.RUNE;
		
		burnDamage = 1;
		weaponBurningRounds = 0;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.burnWeapon(burnDamage, weaponBurningRounds, true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.resetWeaponBurning();
	}
	
	@Override
	public void modifyWeapon(Weapon weapon) {
		return;
	}

	@Override
	public void revertChanges(Weapon weapon) {
		return;
	}

}
