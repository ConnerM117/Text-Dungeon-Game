package adventurers;

import mobs.*;

import java.util.ArrayList;
import adventurers.perks.warrior.*;
import items.armor.Armor;
import items.armor.PaddedArmor;
import items.gear.Gear;
import items.gear.LightHelm;
import items.weapons.OldSword;
import items.weapons.Weapon;

public class Warrior extends Adventurer {
	
	public Warrior() {
		name = "Warrior";
		agilityBuff = 15;
		toughnessBuff = 15;
		mindBuff = 10;
		strengthBuff = 20;
		accuracyBuff = 5;
		
		perkChoices = new ArrayList<>();
		perkChoices.add(new BattleCryPerk());
		perkChoices.add(new IntimidatePerk());
		perkChoices.add(new PowerAttackPerk());
		perkChoices.add(new SweepAttackPerk());
		perkChoices.add(new ToughAsNailsPerk());
	}

	@Override
	public void initEquipment(Player player) {
			Weapon oldSword = new OldSword();
			player.addToInventory(oldSword);
			player.equipHandWeapon(oldSword);

			Armor paddedArmor = new PaddedArmor();		
			player.addToInventory(paddedArmor);
			player.equipArmor(paddedArmor);

			Gear lightHelm = new LightHelm();
			player.addToInventory(lightHelm);
			player.equipHead(lightHelm);
	}

}
