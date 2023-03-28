package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;

public abstract class CombatEncounter {
	
	protected boolean isScriptedEncounter;
	
	public abstract String getDescription();
	
	public abstract String getVictoryDescription();
	
	protected abstract List<Mob> getRandMobs();
	
	public List<Mob> getCombat(int XPMax) {
		if (isScriptedEncounter)
			return getRandMobs();
		
		int XPMin = XPMax/2;
		List<Mob> mobs = new ArrayList<>();
		do {
			mobs = getRandMobs();
		} while (!mobsAreValid(mobs, XPMax, XPMin));
		return mobs;
	}
	
	protected boolean mobsAreValid(List<Mob> mobs, int XPMax, int XPMin) {
		int XPTotal = 0;
		for (Mob m : mobs) {
			XPTotal += m.getXP();
		}

		if (XPTotal <= XPMax && XPTotal >= XPMin)
			return true;
		return false;
	}

}
