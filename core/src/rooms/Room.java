package rooms; 

import java.util.*;

import com.textdungeon.game.GameScreen;

import combat.CombatEncounter;
import core.Chest;
import floors.Floor;
import items.Item;
import items.consumables.Consumable;
import mobs.*;
import traps.Trap;

public abstract class Room {
	
	/*
	 * Clockwork Forge:
	 * 		Ancient Forge: bring a special material to reinforce a weapon/armor and grant it a special bonus (second rune slot?)
	 */
	
	protected final int LAIR_COMBAT_CHANCE = 25;
	protected final int AVG_COMBAT_CHANCE = 10;
	protected final int NO_COMBAT_CHANCE = 0;
	
	protected String name;
	protected int roomNumber;
	protected int combatChance;
	protected int secretSeed;
	protected boolean isBossRoom;
	protected boolean isScouted;
	protected boolean isExplored;
	protected boolean isCompleted;
	protected boolean isSearched;
	protected boolean hasSecret;
	protected boolean isChalked;
	protected boolean hasScriptedCombat;
	protected boolean hasChest;
	protected boolean hasTrap;
	protected boolean trapTriggered;
	protected boolean hasPortal;
	protected Chest chest;
	protected Trap trap;
	protected boolean visited; //used only for Floor methods
	protected List<Item> droppedItems;
	protected Set<Room> adjacentRooms;
	protected List<RoomAction> roomActions;
	protected Floor floor;
	
	/**
	 * marked true after a combat, and marked false after the next time the room is 
	 * displayed, to avoid having two consecutive combats with no break
	 */
	protected boolean combatCooldown;
	
	protected enum Type {
		BOSS, EMPTY, LAIR, SHOP, NORMAL, STORY, UNIQUE
	}
	protected Type type;
	
	 //constructor
	public Room (int roomNumber, Floor floor) {
		this.roomNumber = roomNumber;
		this.floor = floor;
		combatChance = AVG_COMBAT_CHANCE;
		secretSeed = GameScreen.generateRandom(1, 5);
		adjacentRooms = new HashSet<>();
		roomActions = new ArrayList<>();
		droppedItems = new ArrayList<>();
	}
	
	//public methods
	
	/**
	 * Allows the player to search the room. Each room might have different effects of searching, like
	 * adding a new roomAction, discovering loot to add to droppedItems, or discovering a Secret
	 * @return a string describing the events that occurred while searching
	 */
	public String searchRoom(Player player) {
		isSearched = true;
		if (hasSecret() && player.notice()) {
			GameScreen.setLogger(getSecretFound());
			floor.getSecretRoom().addAdjacentRoom(this);
			addAdjacentRoom(floor.getSecretRoom());
		}
		
		String str = "You take a look around the area...\n";
		int randItem = GameScreen.generateRandom(1, 10);
		if (randItem <= 2)
			str += player.addToInventory(Consumable.getRandConsumable());
		if (randItem == 3)
			str += player.addToInventory(Item.getWeightedArmorWeaponGear(floor.getFloorNumber()));
		
		return str;
	}
	
	public void enterRoom() { return; } //executes code that should only be run each time the player first enters the room
	
	public abstract String getDescription();
	
	public abstract String getCompletedDescription();
	
	public abstract void initRoomActions(Player player);
	
	public String getSecretHint() {
		switch (secretSeed) {
		case 1: return "You feel an unusual draft...";
		case 2: return "There's a bit of ambient light in here, but you can't tell where it comes from...";
		case 3: return "Scrapes along the floor lead directly into one of the walls...";
		case 4: return "Footprints lead directly to a wall before stopping abruptly...";
		case 5: return "Part of the stonework seems to be a different color...";
		default: return "Rubble is piled against the walls, except for one short section...";
		}
	}
	
	public String getSecretFound() {
		switch (secretSeed) {
		case 1: return "The breeze leads you to a section of the stone with a hairline crack. A secret door!";
		case 2: return "You're able to pinpoint the source of the light: a small crack with light on the other side. A secret door!";
		case 3: return "Surely the scrapes along the floor must mean a secret door. It's only a matter of time before you find the trigger and the door swings open.";
		case 4: return "Surely the footprints must lead somewhere. With a few minutes spent searching you find a secret lever, which opens the secret door!";
		case 5: return "Part of it must have been reworked or added later; a bit of investigation reveals a secret door!";
		default: return "Someone has gone through the trouble of keeping this clear... and the reason soon becomes equally clear. A secret door!";
		}
	}
	
	public String displayAdjacentRooms() {
		StringBuilder str = new StringBuilder();
		if (isExplored() || isScouted()) {
			str.append("\n" + getRoomNumberString() + ": " + name);
			if (isBossRoom())
				str.append(" <B>");
			if (hasSecret())
				str.append(" <S>");
			if (hasChest())
				str.append(" <C>");
			
			str.append(", Connected to: ");
			
			for (Room r : adjacentRooms) {
				if (r.isExplored() || isScouted()) {
					str.append("\n     " + r.getRoomNumberString() + ": " + r.getName());
					if (r.isBossRoom())
						str.append(" <B>");
					if (r.hasSecret())
						str.append(" <S>");
					if (r.hasChest())
						str.append(" <C>");
				} else if (r.isBossRoom) {
					str.append("\n     Boss Room");
				} else {
					str.append("\n      ???");
				}
			}
		} else {
			str.append("\n" + getRoomNumberString() + ": ???, Connected to: ");
			
			for (int i = 0; i < getAdjacentRooms().size(); i++) {
				str.append("\n      ???");
			}
		}
		
		return str.toString();
	}
	
	public CombatEncounter getCombatEncounter() {
		return floor.getCombatEncounter();
	}
	
	//getters and setters
	
	public String getName() {
		return name;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {	
		this.roomNumber = roomNumber;
	}
	
	public int getCombatChance() {
		return combatChance;
	}
	
	public String getRoomNumberString() {
		return "Room " + roomNumber;
	}
	
	public void addAdjacentRoom(Room room) {
		adjacentRooms.add(room);
	}
	
	public void clearAdjacentRooms() {
		adjacentRooms.clear();
	}
	
	public Set<Room> getAdjacentRooms() {
		return adjacentRooms;
	}
	
	public List<RoomAction> getActions() {
		return roomActions;
	}
	
	public List<Item> getItems() {
		return droppedItems;
	}
	
	public void addDroppedItem(Item item) {
		droppedItems.add(item);
	}
	
	public void removeDroppedItem(Item item) {
		droppedItems.remove(item);
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public boolean isBossRoom() {
		return isBossRoom;
	}
	
	public boolean isScouted() {
		return isScouted;
	}
	
	public void setScouted(boolean isScouted) {
		this.isScouted = isScouted;
	}
	
	public boolean isExplored() {
		return isExplored;
	}
	
	public void setExplored(boolean isExplored) {
		this.isExplored = isExplored;
	}
	
	public boolean isCompleted() {
		return isCompleted;
	}
	
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public boolean isSearched() {
		return isSearched;
	}
	
	public boolean hasSecret() {
		return hasSecret;
	}
	
	public void setSecret(boolean hasSecret) {
		this.hasSecret = hasSecret;
	}
	
	public boolean isChalked() {
		return isChalked;
	}
	
	public void setChalked(boolean isChalked) {
		this.isChalked = isChalked;
	}
	
	public boolean hasScriptedCombat() {
		return hasScriptedCombat;
	}
	
	public void setScriptedCombat(boolean hasScriptedCombat) {
		this.hasScriptedCombat = hasScriptedCombat;
	}
	
	public boolean hasChest() {
		return hasChest;
	}
	
	public void setChest(boolean hasChest) {
		this.hasChest = hasChest;
	}
	
	public Chest getChest() {
		return chest;
	}
	
	public void setChest(Chest chest) {
		this.chest = chest;
	}
	
	public boolean hasTrap() {
		return hasTrap;
	}
	
	public void setTrap(boolean hasTrap) {
		this.hasTrap = hasTrap;
	}
	
	public Trap getTrap() {
		return trap;
	}
	
	public void setTrap(Trap trap) {
		this.trap = trap;
	}
	
	public boolean hasPortal() {
		return hasPortal;
	}
	
	public void setPortal(boolean hasPortal) {
		this.hasPortal = hasPortal;
	}
	
	public boolean trapTriggered() {
		return trapTriggered;
	}
	
	public void setTrapTriggered(boolean trapTriggered) {
		this.trapTriggered = trapTriggered;
	}
	
	/**
	 * rolls a random 1-100. If it's lower than combatChance and the room isn't on 
	 * combatCooldown, then a combat should occur.
	 * @return true if combat should occur, false otherwise
	 */
	public boolean hasCombat() {
		if (GameScreen.generateRandom(1, 100) <= combatChance && !combatCooldown) {
			combatCooldown = true;
			return true;
		}
		combatCooldown = false;
		return false;
	}

	public class RoomAction {

		private String name;
		
		public RoomAction(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public String resolveAction(Player player) { return ""; };

	}
}
