package floors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import combat.CombatEncounter;
import core.Chest;
import items.Item;
import items.KeyItem;
import items.consumables.Consumable;
import rooms.*;
import traps.Trap;

/*
 * Floor Types:
 * 1-Rat Warrens: Giant Rat, Rat Swarm, Wererat; Boss: Rat King
 * 2-Goblin Tunnels: Goblin Scout, Goblin Archer, Goblin Shaman; Boss: Goblin King
 * 3-Forgotten Tomb: Skeleton Warrior, Skeleton Archer, Giant Skeleton; Boss: Bone Mass
 * 4-Troll Cave: Ogre, Hobgoblin, Troll; Boss: Cave Troll
 * 5-Deep Forest: wolf, bear, giant snake (coil move); Boss: Elder Druid
 * 6-Deep Swamp: lizardman warrior, lizardman shaman, lizardman champion; Boss: Hydra
 * 7-Lich's Domain: Necromancer, Zombie, Wight; Boss: Lich
 * 8-Dragon's Lair: Wyvern, Dragon Wyrmling, Dragonkin; Boss: Ancient Dragon
 * 9-Desecrated Temple: imp, horned demon, fire demon; Boss: Demon Lord
 * 10-Astral Dominion: eldritch horror, eldritch spawn, eldritch beast
 * Final Floor is always Astral Dominion; Boss: Heart of the Dungeon (made of the souls of all that the dungeon has consumed)
 * 
 * Secret Floor: Clockwork Forge: Clockwork Drone, Clockwork Sentinel, Clockwork Artillery; Boss: Clockwork Colossus
 * 
 * Stone Guardians become an optional fight with great treasure
 */

public abstract class Floor {
	
	public final static String RAT_WARRENS = "Rat Warrens";
	public final static String GOBLIN_TUNNELS = "Goblin Tunnels";
	public final static String FORGOTTEN_TOMB = "Forgotten Tomb";
	public final static String TROLL_CAVE = "Troll Cave";
	public final static String DEEP_FOREST = "Deep Forest";
	public final static String DEEP_SWAMP = "Deep Swamp";
	public final static String LICH_DOMAIN = "Lich's Domain";
	public final static String DRAGON_CAVERN = "Dragon's Cavern";
	public final static String DEMON_TEMPLE = "Desecrated Temple";
	public final static String ASTRAL_DOMINION = "Astral Dominion";
	public final static String CLOCKWORK_FORGE = "Clockwork Forge";
	
	private final int MAX_COMBAT_DIFFICULTY = 10;
	private final int LAIRS = 3;
	protected final int INVALID_ROOMS = 4; //subtracted from floorRooms.size() to avoid picking invalid rooms at the end of the list
	private final int NUM_CONSUMABLES = 5;
	private final int NUM_ITEMS = 2;
	private final int MIN_TRAPS = 1;
	private final int MAX_TRAPS = 3;
	
	protected String name;
	protected int floorNumber;
	protected List<Room> floorRooms;
	protected Room secretRoom;
	protected int combatXPMax;
	
	protected CombatEncounter encounter;
	protected CombatEncounter bossEncounter;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		secretRoom = new SecretRoom(-1, this);
		floorRooms = createFloor();
		combatXPMax = 3;
	}
	
	public abstract String getDescription();
	
	protected abstract Room getLairRoom(int roomNumber);
	
	protected abstract Room getBossRoom(int roomNumber);
	
	protected abstract Room getEmptyRoom(int roomNumber);
	
	protected abstract Room getStoryRoomOne(int roomNumber);
	
	protected abstract Room getStoryRoomTwo(int roomNumber);
	
	protected abstract Room getUniqueRoomOne(int roomNumber);
	
	protected abstract Room getUniqueRoomTwo(int roomNumber);
	
	public List<Room> createFloor() {
		List<Room> dungeonFloor = initFloorRooms();
		
		connectRooms(dungeonFloor);
		initFloorLoot(dungeonFloor);
		initFloorSecrets(dungeonFloor);
		initFloorTraps(dungeonFloor);
		
		return dungeonFloor;
	}

	/*
	 * returns a list of rooms: 3 Lairs, 2 Unique Rooms, 2 Flavor Rooms, 1 Shop, 1 Campsite, 
	 * 1-3 Empties, and 5-Empties random filler rooms, plus one Boss Room. 15 Total.
	 */
	private List<Room> initFloorRooms() {
		int roomCounter = 1;
		List<Room> dungeonFloor = new ArrayList<>(); //create the dungeon floor
		
		int numEmptyRooms = GameScreen.generateRandom(1, 3); //decide how many empties, 1-3
		
		for (int i = 0; i < numEmptyRooms; i++) { // get Empty Rooms equal to numEmptyRooms
			dungeonFloor.add(getEmptyRoom(roomCounter));
			roomCounter++;
		}
		
		List<Integer> randInts = new ArrayList<Integer>();
		for (int i=1; i<=11; i++) randInts.add(i); //get 1-11 in a random order to use as seeds in getRandomFillerRoom, to ensure there are no duplicate rooms
        Collections.shuffle(randInts);
       
		for (int i = 0; i < (5-numEmptyRooms); i++) { //fill the floor with (5-numEmptyRooms) filler rooms
			dungeonFloor.add(getRandomFillerRoom(randInts.get(i), roomCounter));
			roomCounter++;
		}
		
		for (int i = 0; i < LAIRS; i++) { //add LAIRS number of lairs
			dungeonFloor.add(getLairRoom(roomCounter));
			roomCounter++;
		}
		
		dungeonFloor.add(new Campsite(roomCounter, this)); //add one campsite
		roomCounter++;
		dungeonFloor.add(new Shop(roomCounter, this)); // add one shop
		roomCounter++;
		dungeonFloor.add(getUniqueRoomOne(roomCounter)); //add first unique room
		roomCounter++;
		dungeonFloor.add(getUniqueRoomTwo(roomCounter)); //add second unique room
		roomCounter++;
		dungeonFloor.add(getStoryRoomOne(roomCounter)); //add first sidequest room
		roomCounter++;
		dungeonFloor.add(getStoryRoomTwo(roomCounter)); //add second sidequest room
		roomCounter++;
		dungeonFloor.add(getBossRoom(roomCounter)); //add the boss room
		return dungeonFloor;
	}
	
	/*
	 * ensures each room on the floor is connected to at least one other room and there is a path from entrance to exit
	 */
	private void connectRooms(List<Room> floor) {
		do {
			for (Room r : floor) {
				r.clearAdjacentRooms(); //ensure adjacentRooms is empty before filling it
				r.setVisited(false);
			}
			
			for (Room r : floor) {
				int roomIndex = -1; 
				do {
					//pick another random room to be adjacent to, excluding the boss room or the story rooms.
					//this ensures boss rooms and story rooms will each have only one connection and thus be dead ends.
					roomIndex = GameScreen.generateRandom(0, floor.size()-INVALID_ROOMS); 
				} while (!isValidRoomChoice(r, roomIndex, floor));
				
				floor.get(roomIndex).addAdjacentRoom(r); //add this room to that room's list
				r.addAdjacentRoom(floor.get(roomIndex)); //add that room to this room's list
			}
		} while (!checkExitDistance(floor));
	}
	
	//returns true if there are at least 3 rooms before the boss room and each room is connected
	private boolean checkExitDistance(List<Room> floor) {
		int distance[] = new int[floor.size()]; //stores distance from each room to the beginning room
		
		LinkedList<Room> queue = new LinkedList<>();
		for (int i = 0; i < floor.size(); i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		
		floor.get(0).setVisited(true); //begin with room at floors.get(0)
		distance[0] = 0;
		queue.add(floor.get(0));
		
		while (!queue.isEmpty()) {
			Room room = queue.poll();
			List<Room> adjacentRooms = new ArrayList<>(room.getAdjacentRooms());
			for (int i = 0; i < adjacentRooms.size(); i++) {
				if (!adjacentRooms.get(i).isVisited()) {
					adjacentRooms.get(i).setVisited(true);
					distance[adjacentRooms.get(i).getRoomNumber()-1] = distance[room.getRoomNumber()-1] + 1;
					queue.add(adjacentRooms.get(i));
				}
			}
		}
		
		return (distance[floor.size()-1] > 2 && allRoomsConnected(floor));
	}
	
	//returns true if all rooms are accessible
	private boolean allRoomsConnected(List<Room> floor) {
		for (int i = 0; i < floor.size(); i++) {
			if (floor.get(i).isVisited() == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines if the index is linked to a valid room choice. To be a valid choice, the room must not have
	 * picked itself, and the potential room must not already have 4 connections. Boss rooms are the last room 
	 * in the list, and thus won't be picked by the random number generator.
	 * @param room the room finding a room choice
	 * @param roomIndex the index of the proposed adjacent room in Floor
	 * @param floor the list of all rooms
	 * @return true if the index is to a valid room choice, false otherwise
	 */
	private boolean isValidRoomChoice(Room room, int roomIndex, List<Room> floor) {
		return roomIndex != room.getRoomNumber()-1 && floor.get(roomIndex).getAdjacentRooms().size() < 4;
	}
	
	public Room getRandomFillerRoom(int seed, int roomNumber) {
		switch (seed) {
		case 1: return new AdventurerTomb(roomNumber, this);
		case 2: return new Armory(roomNumber, this);
		case 3: return new Battleground(roomNumber, this);
		case 4: return new BlessedFountain(roomNumber, this);
		case 5: return new FrightfulStatue(roomNumber, this);
		case 6: return new Labyrinth(roomNumber, this);
		case 7: return new MushroomPatch(roomNumber, this);
		case 8: return new Obelisk(roomNumber, this);
		case 9: return new Ravine(roomNumber, this);
		case 10: return new StoneGolemSanctum(roomNumber, this);
		case 11: return new DarkAltar(roomNumber, this);
		default: return new AdventurerTomb(roomNumber, this);
		}
	}
	
	protected void initFloorLoot(List<Room> floorRooms) {
		List<Item> floorLoot = new ArrayList<>();
		for (int i = 0; i < NUM_CONSUMABLES; i++) {
			floorLoot.add(Consumable.getRandConsumable());
		}
		for (int i = 0; i < NUM_ITEMS; i++) {
			floorLoot.add(Item.getWeightedArmorWeaponGear(floorNumber));
		}
		
		for (Item item : floorLoot) {
			int randRoom = GameScreen.generateRandom(0, floorRooms.size()-INVALID_ROOMS);
			floorRooms.get(randRoom).addDroppedItem(item);
		}
		
		int numChests = GameScreen.generateRandom(1, 2);
		//create chests equal to numChests, and if locked, generate a key
		for (int i = 0; i < numChests; i++) {
			Chest chest = new Chest(floorNumber);
			int randRoom = GameScreen.generateRandom(0, floorRooms.size()-INVALID_ROOMS);
			//pick a room that doesn't already have a chest
			while (floorRooms.get(randRoom).hasChest() || floorRooms.get(randRoom) instanceof StoneGolemSanctum) {
				randRoom = GameScreen.generateRandom(0, floorRooms.size()-INVALID_ROOMS);
			}
			floorRooms.get(randRoom).setChest(true);
			floorRooms.get(randRoom).setChest(chest);
			//if the chest is locked, spawn a linked key somewhere else on the floor
			if (chest.getType() == Chest.Type.LOCKED || chest.getType() == Chest.Type.LOCKEDTRAPPED) {
				int randRoom2 = GameScreen.generateRandom(0, floorRooms.size()-INVALID_ROOMS);
				while (randRoom == randRoom2) { //ensures the key won't spawn in the same room as the chest
					randRoom2 = GameScreen.generateRandom(0, floorRooms.size()-INVALID_ROOMS);
				}				
				floorRooms.get(randRoom2).addDroppedItem(new KeyItem(chest.getKeyString()));
			}
		}
		
	}
	
	protected void initFloorSecrets(List<Room> floorRooms) {
		int randRoom = GameScreen.generateRandom(1, floorRooms.size()-INVALID_ROOMS);
		floorRooms.get(randRoom).setSecret(true);
	}
	
	protected void initFloorTraps(List<Room> floorRooms) {
		int numTraps = GameScreen.generateRandom(MIN_TRAPS, MAX_TRAPS);
		for (int i = 0; i < numTraps; i++) {
			Trap trap = Trap.getRandRoomTrap();
			int randRoom = GameScreen.generateRandom(1, 7); //pick a random empty/filler/lair room, one of the first 8 indexes, except for the first room
			//pick a room that doesn't already have a trap
			while (floorRooms.get(randRoom).hasTrap()) {
				randRoom = GameScreen.generateRandom(1, 7);
			}
			floorRooms.get(randRoom).setTrap(true);
			floorRooms.get(randRoom).setTrap(trap);
		}
	}
	
	public String getMap() {
		StringBuilder str = new StringBuilder();
		for (Room r : floorRooms) {
			str.append(r.displayAdjacentRooms());
		}
		return str.toString();
	}
	
	/**
	 * Modifies this floor's combatXPMax by int mod. If lower than 1, it is set to 1.
	 * If higher than MAX_COMBAT_DIFFICULTY, it is set to that value.
	 * 
	 * XP Limit is the maximum upper limit for encounter difficulty.
	 * @param mod how much to modify the floor's combatXPMax
	 */
	public void modifyXPLimit(int mod) {
		combatXPMax += mod;
		if (combatXPMax < 1)
			combatXPMax = 1; //XPLimit can't go lower than 1
		if (combatXPMax > MAX_COMBAT_DIFFICULTY)
			combatXPMax = MAX_COMBAT_DIFFICULTY; //XPLimit can't go higher than the max
	}
	
	//getters and setters

	public List<Room> getFloorRooms() {
		return floorRooms;
	}

	public String getName() {
		return name;
	}
	
	public int getCombatXPMax() {
		return combatXPMax;
	}
	
	public CombatEncounter getCombatEncounter() {
		return encounter;
	}
	
	public CombatEncounter getBossEncounter() {
		return bossEncounter;
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}
	
	public Room getSecretRoom() {
		return secretRoom;
	}

}