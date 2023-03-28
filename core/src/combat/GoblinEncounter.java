package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.GoblinArcher;
import mobs.GoblinBrute;
import mobs.GoblinScout;
import mobs.GoblinShaman;
import mobs.Mob;

public class GoblinEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "A stench assaults your nose as you enter and come face-to-face with goblins! "
				+ "They raise their crude weapons and attack!";
	}

	@Override
	public String getVictoryDescription() {
		return "Having defeated the goblins, you step over their corpses and head farther into the dark. "
				+ "But not before looting the bodies, of course!";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> goblins = new ArrayList<>();
		int gScout = 1, gArcher = 1, gShaman = 1, gBrute = 1;
		for (int i = 0; i < GameScreen.generateRandom(2, 4); i++) { //fills the list of goblins with 2-4 goblins
			int rand = GameScreen.generateRandom(1, 9);
			if (rand <= 4) {
				goblins.add(new GoblinScout(gScout));
				gScout++;
			} else if (rand <= 6) {
				goblins.add(new GoblinArcher(gArcher));
				gArcher++;
			} else if (rand <= 8) {
				goblins.add(new GoblinShaman(gShaman));
				gShaman++;
			} else {
				goblins.add(new GoblinBrute(gBrute));
				gBrute++;
			}
		}
		return goblins;
	}

}
