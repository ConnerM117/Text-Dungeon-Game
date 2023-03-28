package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mimic;
import mobs.Mob;

public class MimicEncounter extends CombatEncounter {
	
	public MimicEncounter() {
		isScriptedEncounter = true;
	}
	
	@Override
	public String getDescription() {
		return "You take hold of the lid to open the chest, but as the lid rises, "
				+ "it reveals rows of sharp teeth. Goggling eyes open on the wood surface, "
				+ "a lashing tongue shoots toward you, and limbs grow from its sides. It's "
				+ "a mimic!";
	}

	@Override
	public String getVictoryDescription() {
		return "The thing lurches, groaning as it hits the ground and finally "
				+ "stops moving. Despite its true nature, looks like it had treasure in "
				+ "it after all!";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new Mimic());
		return mobs;
	}

}
