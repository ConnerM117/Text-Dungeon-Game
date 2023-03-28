package adventurers;

import mobs.*;

public class Priest extends Adventurer {
	
	public Priest() {
		name = "Priest";
		
		// TODO complete constructor
	}
	
	public void chooseArchetype(Player player) {
		// TODO complete method	
	}

	@Override
	public void initEquipment(Player player) {
		// TODO Auto-generated method stub
		
	}

}

/*
 * 5 Perks:
 * 	Bless (as above)
 * 	Blinding Light (as above)
 * 	Cleanse (remove all debuffs on self, including Burn and Poison)
 * 	Curse (remove all buffs on foes and set -10 dodge)
 * 	Heal (heal self, end Bleeding)
 * 
 * Cleric: (Channel Power: Automatically hit one foe, no dodge)
 * 	Armor of Faith: Bonus to Armor
 * 	Atone: Heal when you Channel Power
 * 	Divine Grace: Attacking heals 1 HP
 * 	Judgement: if there are multiple foes, Channel Power attacks all of them but deals half damage
 * 	Regenerate (1 heal per turn, but higher total than Heal)
 * 
 * Cultist: (Dark Bargain: doesn't take your action; the next action you take has buffs/debuffs doubled, option to spend Stamina or take a random debuff)
 * 	Hex of the Abyss: choose one stat to debuff
 * 	Hex of Despair: debuff Accuracy and Mind
 * 	Hex of Vulnerability: debuff Armor and Dodge
 * 	Hex of Weakness: debuff Toughness and Damage	
 * 	Sacrifice: when you defeat an enemy with an Attack, 50% chance to trigger Dark Bargain with neither Stamina nor Debuff
 * 
 * Prophet: (Portent: as above)
 * 	Augury: identify a connected room the first time you enter a new room
 *  Clairvoyance: identify the most direct route to the boss room
 * 	Foresight: your Attacks can't be dodged
 * 	
 * 
 * 
 */
