package core;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Coins;
import items.Item;
import items.consumables.Consumable;
import items.runes.Rune;
import mobs.Player;
import traps.Trap;

public class Chest {
	
	private List<Item> contents;
	private int numContents;
	private int floorNumber;
	private String keyString;
	private boolean isLocked;
	private boolean isSealed;
	private boolean trapIsArmed;
	private boolean isMimicAlive;
	private Trap chestTrap;
	
	private final String LOCKPICKS = "Lockpicks";
	
	public enum Type {
		LOCKED, LOCKEDTRAPPED, MIMIC, TRAPPED, UNLOCKED
	}
	Type type;
	
	private enum LockColor {
		BRASS, COPPER, GOLD, IRON, SILVER
	}
	LockColor lockColor;
	
	public Chest(int floorNumber) {
		this.floorNumber = floorNumber;
		setType();
		setLockColor();
		numContents = GameScreen.generateRandom(2, 3);
		setContents(new ArrayList<>());
		initContents();
		keyString = "Floor " + floorNumber + " " + getLockColorString() + " Key";
		if (type == Type.LOCKED || type == Type.LOCKEDTRAPPED) {
			isLocked = true;
		} else {
			isLocked = false;
		}
		isSealed = false;
		if (type == Type.TRAPPED || type == Type.LOCKEDTRAPPED) {
			trapIsArmed = true;
			chestTrap = Trap.getRandChestTrap();
		} else {
			trapIsArmed = false;
			chestTrap = null;
		}
		if (type == Type.MIMIC) {
			isMimicAlive = true;
		}
	}
	
	public Chest(int floorNumber, Type type, int numContents) {
		this.floorNumber = floorNumber;
		this.type = type;
		setLockColor();
		this.numContents = numContents;
		setContents(new ArrayList<>());
		initContents();
		keyString = "Floor " + floorNumber + " " + getLockColorString() + " Key";
		if (type == Type.LOCKED || type == Type.LOCKEDTRAPPED) {
			isLocked = true;
		} else {
			isLocked = false;
		}
		isSealed = false;
		if (type == Type.TRAPPED || type == Type.LOCKEDTRAPPED) {
			trapIsArmed = true;
			chestTrap = Trap.getRandChestTrap();
		} else {
			trapIsArmed = false;
			chestTrap = null;
		}
		if (type == Type.MIMIC) {
			isMimicAlive = true;
		}
	}
	
	private void setType() {
		int rand = GameScreen.generateRandom(1, 5);
		switch (rand) {
		case 1: type = Type.LOCKED; break;
		case 2: type = Type.LOCKEDTRAPPED; break;
		case 3: type = Type.MIMIC; break;
		case 4: type = Type.TRAPPED; break;
		case 5: type = Type.UNLOCKED; break;
		default: type = Type.UNLOCKED; break;
		}
	}
	
	private void setLockColor() {
		int rand = GameScreen.generateRandom(1, 5);
		switch (rand) {
		case 1: lockColor = LockColor.BRASS; break;
		case 2: lockColor = LockColor.COPPER; break;
		case 3: lockColor = LockColor.GOLD; break;
		case 4: lockColor = LockColor.IRON; break;
		case 5: lockColor = LockColor.SILVER; break;
		default: lockColor = LockColor.IRON; break;
		}
	}
	
	private void initContents() {
		for (int i = 0; i < numContents; i++) {
			int rand = GameScreen.generateRandom(1, 10);
			if (rand == 1)
				getContents().add(Item.getFloorSpecialItem(floorNumber));
			else if (rand <= 3)
				getContents().add(Consumable.getRandConsumable());
			else if (rand <= 5)
				getContents().add(Item.getWeightedArmorWeaponGear(floorNumber));
			else if (rand <= 7)
				getContents().add(Rune.getRandRuneWeighted(floorNumber));
			else 
				getContents().add(new Coins(2, 5));
		}
	}
	
	private boolean chestIsLocked(Player player) {
		String str = ("The chest is locked with a " + getLockColorString() + " lock.");
		if (player.getKeyRing().contains(keyString)) {
			GameScreen.log(str + "\nBut you have a matching " + getLockColorString() + " key!");
			isLocked = false;
			return false;
		} else if (player.hasSkeletonKey()) {
			GameScreen.log(str + "\nBut you have the thievery skills required to get it open!");
			isLocked = false;
			return false;
		} else if (player.getInventory().containsKey(LOCKPICKS)) {
			GameScreen.log(str + "\nYou attempt to open the chest with your lockpicks...");
			GameScreen.log("...");
			if (GameScreen.generateRandom(1, 100) < player.getCurrentAgility() + GameScreen.LOCKPICK_BONUS) { //they succeed the lockpick check
				GameScreen.log("The lock clicks and the chest pops open!");
				isLocked = false;
				return false;
			} else {
				GameScreen.log("You struggle with the lock and can't seem to get it open.");
			}
			player.removeFromInventory(LOCKPICKS);
		} else {
			GameScreen.log(str + "\nYou have no way to get it open.");
		}

		return true;
	}
	
	private void runChestTrap(Player player) {
		if (player.notice() || player.isTrapExpert()) {
			
			if (player.hasSkeletonKey()) {
				GameScreen.log("You take hold of the lid to open the chest, but something catches"
						+ "\nyour eye. The chest is trapped! Luckily, you have the thievery skills to"
						+ "\ndeal with it.");
				chestTrap.disarmTrap();
				return;
			}
			
			if (player.getInventory().containsKey(LOCKPICKS)) {
				GameScreen.log("You set to work with your lockpicks, trying to disarm the trap...");
				if (GameScreen.generateRandom(1, 100) < player.getCurrentAgility() + GameScreen.LOCKPICK_BONUS) { //they succeed the lockpick check
					GameScreen.log("...success! It is of no further threat.");
					chestTrap.disarmTrap();
				} else {
					GameScreen.log("You struggle with the trap and trigger it in the process!");
					GameScreen.log(chestTrap.triggerTrap(player));
					chestTrap.disarmTrap();
				}
				player.removeFromInventory(LOCKPICKS);
			}
			
		} else {
			GameScreen.log("You take hold of the lid to open the chest, and you feel something"
					+ "\ncatch with a click. Your heart drops as you realize it was trapped!");
			GameScreen.log(chestTrap.triggerTrap(player));
			chestTrap.disarmTrap();
		}
	}

	public String getLockColorString() {
		switch (lockColor) {
		case BRASS: return "Brass";
		case COPPER: return "Copper";
		case GOLD: return "Gold";
		case IRON: return "Iron";
		case SILVER: return "Silver";
		default: return "Iron";
		}
	}

	public boolean openChest(Player player) {
		
		if (isSealed) {
			GameScreen.log("The chest is sealed shut!");
			return false;
		}
		
		switch (type) {
		case LOCKED: 
			if (isLocked && chestIsLocked(player)) 
				return false;
			break;
		case LOCKEDTRAPPED: 
			if (isLocked && chestIsLocked(player))
				return false;
			if (trapIsArmed) 
				runChestTrap(player);
			break;
		case TRAPPED: 
			if (trapIsArmed) 
				runChestTrap(player); 
			break;
		default: //mimic and unlocked
			break;
		}
		
		return true;
	}
	
	public boolean isEmpty() {
		return getContents().isEmpty();
	}
	
	public Type getType() {
		return type;
	}
	
	public String getKeyString() {
		return keyString;
	}

	public List<Item> getContents() {
		return contents;
	}

	public void setContents(List<Item> contents) {
		this.contents = contents;
	}

	public boolean isMimicAlive() {
		return isMimicAlive;
	}

	public void setMimicAlive(boolean isMimicAlive) {
		this.isMimicAlive = isMimicAlive;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
}
