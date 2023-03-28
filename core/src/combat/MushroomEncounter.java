package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.GiantMushroom;
import mobs.Mob;
import mobs.VineMob;

public class MushroomEncounter extends CombatEncounter {

	public MushroomEncounter() {
		isScriptedEncounter = true;
	}
	
	@Override
	public String getDescription() {
		return "As you approach the rows of mushrooms, the enormous mushroom groans and roots erupt from the ground like vines, "
				+ "spraying the area with earth! The vines attack!";
	}

	@Override
	public String getVictoryDescription() {
		return "The vines wither and fall to the side as the mushroom groans once more, this time in agony, and then topples to the "
				+ "ground with a low boom. All is still.";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new GiantMushroom());
		mobs.add(new VineMob());
		return mobs;
	}

}
