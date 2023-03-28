package rooms;

import floors.Floor;
import items.Item;
import mobs.Player;

public class Battleground extends Room {

	private int XPMod;
	
	public Battleground(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Battleground";
		XPMod = 2;
	}
	
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		
		str += "A metallic sheen catches your eye from beneath a pile of bodies. You can't tell exactly what it is- and you'll have to "
				+ "move the macabre obstacles atop it, which will make quite a bit of noise...";
		return str;
	}
	
	@Override
	public String getDescription() {
		return "This place is strewn with the corpses of the fallen. There was obviously a terrible battle here. "
				+ "You can't tell who or what was the cause, but there may be something here of use to you...";
	}

	@Override
	public String getCompletedDescription() {
		return "This place is strewn with the corpses of the fallen. There was obviously a terrible battle here. "
				+ "There is nothing here of use to you...";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (isSearched) {
			RoomAction takeLoot = new RoomAction("Investigate Corpses") {
				@Override
				public String resolveAction(Player player) {
					String str = "You grit your teeth and heave, trying not to inhale too deeply. Bodies crash to the "
							+ "ground; something will have heard that, but now you can get a closer look...\n";
					floor.modifyXPLimit(XPMod);
					str += player.addToInventory(Item.getWeightedArmorOrWeapon(floor.getFloorNumber()));
					setCompleted(true);
					return str;
				}
			};
			
			roomActions.add(takeLoot);
		}
		
	}

}
