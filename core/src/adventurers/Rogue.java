package adventurers;

import java.util.ArrayList;
import adventurers.perks.rogue.Evasive;
import adventurers.perks.rogue.HidePerk;
import adventurers.perks.rogue.Phantom;
import adventurers.perks.rogue.PinpointPerk;
import adventurers.perks.rogue.ScoutPerk;
import mobs.*;
import items.armor.*;
import items.gear.*;
import items.weapons.*;

public class Rogue extends Adventurer {
	
	public Rogue() {
		name = "Rogue";
		agilityBuff = 20;
		toughnessBuff = 10;
		mindBuff = 15;
		strengthBuff = 15;
		accuracyBuff = 5;
		
		perkChoices = new ArrayList<>();
		perkChoices.add(new Evasive());
		perkChoices.add(new HidePerk());
		perkChoices.add(new Phantom());
		perkChoices.add(new PinpointPerk());
		perkChoices.add(new ScoutPerk());
	}
	
	@Override
	public void initEquipment(Player player) {
			Weapon oldSword = new OldSword();
			player.addToInventory(oldSword);
			player.equipHandWeapon(oldSword);

			Armor paddedArmor = new PaddedArmor();		
			player.addToInventory(paddedArmor);
			player.equipArmor(paddedArmor);

			Gear cloak = new Cloak();
			player.addToInventory(cloak);
			player.equipMisc(cloak);
	}

}
