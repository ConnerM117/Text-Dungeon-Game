package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.bosses.DemonLord;

public class DemonBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "You get to the heart of the temple, where profane sigils coat the walls and dried "
				+ "blood betrays the fate of adventurers that came before you. Within the sactum stands "
				+ "a hulking figure with black horns and huge wings, who holds up a skull, looks at you, "
				+ "and says, \"Ah... another one to add to my collection!\"";
	}

	@Override
	public String getVictoryDescription() {
		return "The Demon Lord roars, shaking dust from the ceiling, and a planar gate opens "
				+ "behind him. He seems to disintegrate, turning to ash and being sucked through the "
				+ "gate as he bellows, \"I WILL RETURN!\"";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new DemonLord());
		return mobs;
	}

}
