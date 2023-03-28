package items.runes;

import items.weapons.Weapon;
import mobs.Player;

public class VenomRune extends Rune {

	protected int poisonDamage;
	private int weaponPoisonedRounds;
	
	public VenomRune() {
		name = "Venom Rune";
		description = "The weapon is permanently laced with poison.";
		count = 1;
		cost = 10;
		type = Type.RUNE;
		
		poisonDamage = 1;
		weaponPoisonedRounds = 0;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.poisonWeapon(poisonDamage, weaponPoisonedRounds, true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.resetWeaponPoisoned();
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
