package items.runes;

import com.textdungeon.game.GameScreen;

import items.weapons.Weapon;

public class ChannelingRune extends Rune {

	protected int magicDamageBuff;
	
	public ChannelingRune() {
		name = "Channeling Rune";
		count = 1;
		cost = 10;
		type = Type.RUNE;
		
		magicDamageBuff = GameScreen.generateRandom(1, 2);
		description = "Increase your Magic Damage by " + magicDamageBuff;;
	}
	
	@Override
	public void modifyWeapon(Weapon weapon) {
		weapon.modMagicDamage(magicDamageBuff);
	}

	@Override
	public void revertChanges(Weapon weapon) {
		weapon.modMagicDamage(-magicDamageBuff);
	}

}
