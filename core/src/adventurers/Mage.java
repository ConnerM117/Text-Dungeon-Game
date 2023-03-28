package adventurers;

import java.util.ArrayList;

import adventurers.perks.mage.EmpowerPerk;
import adventurers.perks.mage.MageArmorPerk;
import adventurers.perks.mage.MagicMissilePerk;
import adventurers.perks.mage.ShieldPerk;
import adventurers.perks.mage.WardPerk;
import items.armor.Armor;
import items.armor.PaddedArmor;
import items.gear.Gear;
import items.gear.LesserStaff;
import items.weapons.OldSword;
import items.weapons.Weapon;
import mobs.Player;

public class Mage extends Adventurer {
		
	public Mage() {
		name = "Mage";
		agilityBuff = 15;
		toughnessBuff = 15;
		mindBuff = 20;
		strengthBuff = 10;
		accuracyBuff = 5;
		
		perkChoices = new ArrayList<>();
		perkChoices.add(new EmpowerPerk());
		perkChoices.add(new MageArmorPerk());
		perkChoices.add(new MagicMissilePerk());
		perkChoices.add(new ShieldPerk());
		perkChoices.add(new WardPerk());
	}
	
	@Override
	public void initEquipment(Player player) {
			Weapon oldSword = new OldSword();
			player.addToInventory(oldSword);
			player.equipHandWeapon(oldSword);

			Armor paddedArmor = new PaddedArmor();		
			player.addToInventory(paddedArmor);
			player.equipArmor(paddedArmor);

			Gear staff = new LesserStaff();
			player.addToInventory(staff);
			player.equipHandGear(staff);
	}

}

/*
 * 5 Perks
 * 	Empower: +1 Damage, +10 Crit Rate
 * 	Mage Armor: temporary bonus to Armor
 * 	Magic Missile: deals -1 damage but always hits and can't be dodged
 * 	Shield: temporary bonus to Dodge
 * 	Ward: gain 3 Temp HP
 * 
 * Illusionist (Phantasm: create an illusion of your choice that is so real the target can die from system shock- special attack that auto-hits and can't be dodged, but is resisted with Mind)
 *  Dominate: become Protected by a non-boss enemy
 * 	Fear: all baddies take debuffs
 * 	Invisibility: become Hidden
 * 	Mirror Image; 2-4 version of you, 50% chance attacks are redirected to an image, destroying it
 * 	Ultimate Phantasm: Phantasm affects all enemies
 * 
 * Pyromancer (Fireball: attack all enemies)
 * 	Cauterize: Heal self and purge poison
 * 	Feed the Flames: gain Temp HP when you defeat an enemy
 *  Flame Cloak: attacking enemies resist with Dodge, Burning on failure
 * 	Flame Ward: Immune to Burning
 * 	Incinerate: Fireball can inflict Burning
 *
 * Wizard (Familiar: choose a familiar, each with different effects)
 * 	Chain Lightning: as above
 * 	Overchannel: take damage to massively increase damage output
 * 	Poison Cloud: poison all enemies
 * 	Spellbook: utility spells; spend Stamina to auto-unlock something or fly over traps/obstacles
 * 	True Strike: as above
 * 
 */