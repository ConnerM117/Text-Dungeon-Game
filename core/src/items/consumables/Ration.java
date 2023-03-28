package items.consumables;

import mobs.Player;

public class Ration extends Consumable {

	private int fatigueReduction;
	private int healValue;
	private int staminaRegain;
	
	public Ration() {
		name = "Ration";
		fatigueReduction = -5;
		healValue = 4;
		staminaRegain = 1;
		description = "Consume to reduce Fatigue by " + fatigueReduction + ", heal 1/" + healValue + " max hit points, and regain " 
				+ staminaRegain + " Stamina. Making Camp at a Campsite automatically consumes 1 Ration but "
				+ "doubles its effectiveness.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 19;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " consumes a " + name + ".";
		str += "\n" + player.takeFatigue(fatigueReduction);
		str += "\n" + player.healDamage(player.getMaxHitPoints()/healValue);
		str += "\n" + player.healStamina(staminaRegain);
		player.removeFromInventory(this);
		return str;
	}
	
	public int getFatigueReduction() {
		return fatigueReduction;
	}
	
	public int getHealValue() {
		return healValue;
	}
	
	public int getStaminaRegain() {
		return staminaRegain;
	}

}
