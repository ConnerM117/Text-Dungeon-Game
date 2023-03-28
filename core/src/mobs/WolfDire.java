package mobs;

import com.textdungeon.game.GameScreen;

public class WolfDire extends Wolf {
	
	private Mob parentDruid;

	public WolfDire(Mob parentDruid) {
		super(0);
		name = "Dire Wolf";
		this.parentDruid = parentDruid;
		maxHitPoints = 8;
		currentHitPoints = maxHitPoints;
		minDamage = 2;
	}
	
	public String takeDamage(int damage) {
		String str = super.takeDamage(damage);
		if (getCurrentHitPoints() <= 0) { //if dire bear dies
			GameScreen.mobsToAdd.add(parentDruid); //add the druid back into the mobs list
		}
		return str;
	}

}
