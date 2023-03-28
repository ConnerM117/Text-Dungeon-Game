package items.gear;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.mage.Cauterize;
import adventurers.choices.mage.Dominate;
import adventurers.choices.mage.Doppelgangers;
import adventurers.choices.mage.Fear;
import adventurers.choices.mage.Fireball;
import adventurers.choices.mage.FlameCloak;
import adventurers.choices.mage.Invisibility;
import adventurers.choices.mage.MagicMissile;
import adventurers.choices.mage.ShieldSpell;
import adventurers.choices.mage.Ward;
import mobs.Player;

public class StaffMagic extends Gear {

	private Choice choice;
	
	public StaffMagic() {
		choice = getStaffChoice();
		name = "Staff of " + choice.getName();
		description = "A magical implement that allows you to cast a spell from it.";
		count = 1;
		cost = 10 + GameScreen.generateRandom(2, 10);
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 0;
		magicDamageMod = 0;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 0;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}

	@Override
	public void equipEffects(Player player) {
		player.addCombatChoice(choice);
	}

	@Override
	public void unequipEffects(Player player) {
		player.removeCombatChoice(choice);
	}
	
	private Choice getStaffChoice() {
		switch (GameScreen.generateRandom(1, 10)) {
		case 1: return new Cauterize();
		case 2: return new Dominate();
		case 3: return new Doppelgangers();
		case 4: return new Fear();
		case 5: return new Fireball();
		case 6: return new FlameCloak();
		case 7: return new Invisibility();
		case 8: return new MagicMissile();
		case 9: return new ShieldSpell();
		case 10: return new Ward();
		default: return new Fireball();
		}
	}
}
