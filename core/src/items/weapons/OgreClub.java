package items.weapons;

public class OgreClub extends Weapon {
	
	public OgreClub() {
		name = "Ogre Club";
		description = "A huge heavy club used by an Ogre. Still smells like it.";
		count = 1;
		cost = 0;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 2;
		minDamage = 4;
		maxDamage = 12;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 15;
		critDamageMod = 30;
		dodgeMod = 0;
		accuracyMod = -10;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 28;
	}

}
