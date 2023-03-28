package rooms;

import floors.Floor;
import mobs.Player;

public class LavaRiver extends SplitRoom {

	private int lavaDamage;
	
	public LavaRiver(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Lava River";

		sideOneDescription = "A river of lava cuts through the cavern, illuminating it with an eerie orange-red glow. "
				+ "You might be able to jump the gap, but you shudder to "
				+ "think what might happen if you don't make it.";
		sideTwoDescription = sideOneDescription;
		lavaDamage = 5;
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {

		RoomAction jump = new RoomAction("Jump Across") {
			@Override
			public String resolveAction(Player player) {
				if (player.isStrong()) {
					switchSide();
					return "You ready yourself, get a running start, and then leap with all your might. Your feet connect with the "
							+ "ground on the other side and you breathe a sigh of relief.";
				} else {
					int damage = lavaDamage;
					if (player.isImmuneToBurning() || player.isImmuneBurningTemp())
						damage /= 2;
					return "\nYou ready yourself, get a running start, and then leap with all your might. Your foot slips. The heat "
							+ "is unbearably intense, even though you catch yourself before touching the molten rock. Breathing hard,"
							+ "you heave yourself up, a bit singed.\n" + player.takeDamage(damage);
				}
			}
		};
		
		roomActions.add(jump);
	}

}
