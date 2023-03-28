package items.runes;

import com.textdungeon.game.GameScreen;

import items.weapons.Weapon;

public class ParryRune extends Rune {

	protected int dodgeBuff;
	
	public ParryRune() {
		name = "Parry Rune";
		count = 1;
		cost = 10;
		type = Type.RUNE;
		
		dodgeBuff = 4 + GameScreen.generateRandom(1, 3);
		description = "Increase your Dodge chance by " + dodgeBuff;
	}
	
	@Override
	public void modifyWeapon(Weapon weapon) {
		weapon.modDodgeMod(dodgeBuff);
	}

	@Override
	public void revertChanges(Weapon weapon) {
		weapon.modDodgeMod(-dodgeBuff);
	}

}
