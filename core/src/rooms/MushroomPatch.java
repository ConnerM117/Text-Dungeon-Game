package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.consumables.Ration;
import items.consumables.SketchyMushroom;
import mobs.Player;

public class MushroomPatch extends Room {

	private int minMushrooms;
	private int maxMushrooms;
	
	public MushroomPatch(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Mushroom Patch";
		minMushrooms = 2;
		maxMushrooms = 3;
	}
	
	@Override
	public String getDescription() {
		return "This section of the dungeon is overgrown with fungus, lichen, and many varieties of large mushroom.";
	}

	@Override
	public String getCompletedDescription() {
		return "This section of the dungeon is overgrown with fungus, lichen, and many varieties of large mushroom. You've already "
				+ "harvested those that looked usable.";
	}

	@Override
	public void initRoomActions(Player player) {

		RoomAction harvest = new RoomAction("Harvest Mushrooms") {
			@Override
			public String resolveAction(Player player) {
				String str = "You set to work harvesting mushrooms.";
				int numMushrooms = GameScreen.generateRandom(minMushrooms, maxMushrooms);
				for (int i = 0; i < numMushrooms; i++) {
					int rand = GameScreen.generateRandom(1, 2);
					if (rand == 1) {
						str += "\n" + player.addToInventory(new SketchyMushroom());
					} else {
						str += "\n" + player.addToInventory(new Ration());
					}
				}
				setCompleted(true);
				return str;
			}
		};
		
		roomActions.add(harvest);
	}

}
