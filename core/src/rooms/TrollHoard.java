package rooms;

import core.Chest;
import floors.Floor;
import items.Item;
import items.consumables.WeirdArtifact;
import items.runes.Rune;
import mobs.Player;

public class TrollHoard extends Room {

	public TrollHoard(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Troll Hoard";
		setChest(true);
		setChest(new Chest(floor.getFloorNumber(), Chest.Type.UNLOCKED, 2));
		droppedItems.add(new WeirdArtifact());
		droppedItems.add(Rune.getRandRuneWeighted(floor.getFloorNumber()));
		droppedItems.add(Item.getRandItemWeighted(floor.getFloorNumber()));
		droppedItems.add(Item.getTrollItem());
	}

	@Override
	public String getDescription() {
		return "It reeks of troll, but a quick glance around reveals why. A troll must lair here, for this is where it's kept "
				+ "all of its loot!";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		// no special actions
	}

}
