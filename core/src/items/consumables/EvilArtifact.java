package items.consumables;

import mobs.Player;

public class EvilArtifact extends Consumable {

	public EvilArtifact() {
		name = "Evil Artifact";
		description = "This talisman of black stone depicts a sneering ghoulish face, and emanates malice.";
		count = 1;
		cost = 10;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 0;
	}
	
	public String useItem(Player player) {
		return "You can't use this here.";
	}
}
