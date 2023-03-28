package mobs;

import com.textdungeon.game.GameScreen;

public class BearDire extends Bear {
	
	private Mob parentDruid;
	
	public BearDire(Mob parentDruid) {
		super(0);
		name = "Dire Bear";
		this.parentDruid = parentDruid;
	}

	public String takeDamage(int damage) {
		String str = super.takeDamage(damage);
		if (getCurrentHitPoints() <= 0) { //if dire bear dies
			GameScreen.mobsToAdd.add(parentDruid); //add the druid back into the mobs list
		}
		return str;
	}
}
