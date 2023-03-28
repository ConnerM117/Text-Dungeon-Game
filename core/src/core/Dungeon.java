package core;

import java.util.*;

import floors.*;

public class Dungeon {
	
protected List<Floor> dungeonFloors;
	
	public Dungeon() {
		dungeonFloors = createDungeon();
	}
	
	private List<Floor> createDungeon() {
		List<Integer> randInts = new ArrayList<Integer>();
        for (int i=1; i<10; i++) randInts.add(i); //get 1-9 in a random order to use as seeds in getRandomFloor, to ensure there are no duplicate floors in a random order
        //Collections.shuffle(randInts);
        
        List<Floor> dungeonFloors = new ArrayList<>(); //create the dungeon floor
		for (int i = 0; i < 9; i++) {//fill the dungeon with 9 floors
			dungeonFloors.add(getRandomFloor(randInts.get(i)));
		}
		dungeonFloors.add(new AstralDominion(dungeonFloors.size()));
		
		return dungeonFloors;
	}

	private Floor getRandomFloor(Integer seed) {
		switch (seed) {
		case 1: return new RatWarrens(seed);
		case 2: return new GoblinTunnels(seed);
		case 3: return new ForgottenCatacombs(seed);
		case 4: return new TrollCave(seed);
		case 5: return new DeepForest(seed);
		case 6: return new DeepSwamp(seed);
		case 7: return new LichDomain(seed);
		case 8: return new DragonCavern(seed);
		case 9: return new DesecratedTemple(seed);
		}
		return null;
	}
	
	public List<Floor> getDungeonFloors() {
		return dungeonFloors;
	}

}
