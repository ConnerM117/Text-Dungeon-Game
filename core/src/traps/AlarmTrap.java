package traps;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.Mob;
import mobs.Player;

public class AlarmTrap extends Trap {
	
	List<Mob> mobs;
	int XPMod;
	
	public AlarmTrap() {
		mobs = new ArrayList<>();
		XPMod = 2;
		description = "alert nearby enemies";
	}

	@Override
	public String triggerTrap(Player player) {
		GameScreen.currentFloor.modifyXPLimit(XPMod);
		isArmed = false;
		return ("You hear a loud sound in a nearby area. Whatever set the trap knows you're here!");
	}

}
