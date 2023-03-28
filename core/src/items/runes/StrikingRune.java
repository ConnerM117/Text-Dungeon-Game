package items.runes;

import com.textdungeon.game.GameScreen;

import items.weapons.Weapon;

public class StrikingRune extends Rune {

	protected int maxDamageBuff;
	
	public StrikingRune() {
		name = "Striking Rune";
		count = 1;
		cost = 10;
		type = Type.RUNE;
		
		maxDamageBuff = GameScreen.generateRandom(1, 2);
		description = "The weapon's maximum damage is increased by " + maxDamageBuff;
	}
	
	@Override
	public void modifyWeapon(Weapon weapon) {
		weapon.modMaxDamage(maxDamageBuff);
	}

	@Override
	public void revertChanges(Weapon weapon) {
		weapon.modMaxDamage(-maxDamageBuff);
	}

}
