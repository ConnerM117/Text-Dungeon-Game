package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.GoblinBrute;
import mobs.Mob;

public class GoblinGuardEncounter extends CombatEncounter {

	public GoblinGuardEncounter() {
		isScriptedEncounter = true;
	}
	
	@Override
	public String getDescription() {
		return "You advance into the room, moving to attack. The guards rise from their positions and draw their weapons!";
	}

	@Override
	public String getVictoryDescription() {
		return "With the guards defeated, you're free to explore the room.";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new GoblinBrute(1));
		mobs.add(new GoblinBrute(2));
		return mobs;
	}

}
