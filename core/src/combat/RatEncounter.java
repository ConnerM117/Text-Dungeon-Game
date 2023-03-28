package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.GiantRat;
import mobs.Mob;
import mobs.RatSwarm;
import mobs.WereRat;

public class RatEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "You hear chittering up above, and as you enter the room, eyes peer hungrily from the "
				+ "darkness. The rats charge forward to attack!";
	}

	@Override
	public String getVictoryDescription() {
		return "After defeating the last of the rats, you move onward into the dark, ever vigilant. "
				+ "After seeing if they had anything valuable, that is!";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> rats = new ArrayList<>();
		int gr = 1, rs = 1, wr = 1;
		for (int i = 0; i < GameScreen.generateRandom(2, 4); i++) { //fills the list with 2-4 enemies
			int rand = GameScreen.generateRandom(1, 4);
			if (rand <= 2) {
				rats.add(new GiantRat(gr));
				gr++;
			} else if (rand == 3) {
				rats.add(new RatSwarm(rs));
				rs++;
			} else {
				rats.add(new WereRat(wr));
				wr++;
			}
		}
		return rats;
	}

}
