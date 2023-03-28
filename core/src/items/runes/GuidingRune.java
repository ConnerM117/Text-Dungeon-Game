package items.runes;

import com.textdungeon.game.GameScreen;

import items.weapons.Weapon;

public class GuidingRune extends Rune {

	protected int critDamageBuff;
	protected int critRateBuff;
	
	public GuidingRune() {
		name = "Guiding Rune";
		count = 1;
		cost = 10;
		type = Type.RUNE;
		
		critDamageBuff = 9 + GameScreen.generateRandom(1, 11);
		critRateBuff = 9 + GameScreen.generateRandom(1, 5);
		description = "The weapon guides your hand to your foe's vulnerabilities, granting +" + critRateBuff + " to Crit Rate "
				+ "and +" + critDamageBuff + " to Crit Damage";
	}
	
	@Override
	public void modifyWeapon(Weapon weapon) {
		weapon.modCritDamage(critDamageBuff);
		weapon.modCritRate(critRateBuff);
	}

	@Override
	public void revertChanges(Weapon weapon) {
		weapon.modCritDamage(-critDamageBuff);
		weapon.modCritRate(-critRateBuff);
	}

}
