package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.Mob;
import mobs.Wraith;

public class WraithEncounter extends CombatEncounter {

	public WraithEncounter() {
		isScriptedEncounter = true;
	}
	
	@Override
	public String getDescription() {
		return "From the blackness, a humanoid form takes shape, shadows of blackness curling off of its cloak of darkness "
				+ "like smoke. With a ghastly wail it attacks!";
	}

	@Override
	public String getVictoryDescription() {
		GameScreen.wraithIsSlain = true;
		return "The thing unleashes a final screech as it begins to fade, its voice dying until it becomes only a breath like "
				+ "the wind, and then it is gone.";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new Wraith());
		return mobs;
	}

}
