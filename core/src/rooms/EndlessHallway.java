package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import mobs.Player;

public class EndlessHallway extends SplitRoom {

	private final int DISTANCE_MULT = 20;
	
	private enum Direction {
		FORWARD, BACKWARD
	}
	
	private Direction direction;
	private int distance;
	
	public EndlessHallway(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Endless Hallway";
		distance = 0;
		direction = Direction.FORWARD;
	}
	
	@Override
	public String getDescription() {
		super.getDescription();
		
		if (distance > 0) {
			return "The hall seems to stretch on endlessly both in front and behind.";
		}
		return "The hall seems to stretch on eternally in front of you.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction walkForward = new RoomAction("Forward") {
			@Override
			public String resolveAction(Player player) {
				switch (roomSide) {
				case SIDE_ONE:
					roomSide = RoomSide.MIDDLE;
					break;
				case SIDE_TWO:
					roomSide = RoomSide.MIDDLE;
					break;
				default:
					break;
				}
				
				if (GameScreen.generateRandom(1, 100) <= distance * DISTANCE_MULT || (direction == Direction.BACKWARD && distance == 0)) {
					resetDistance();
					reachHallEnd();
					return "You reach the end of the hall abruptly, as if you had blinked and found yourself there. You can't tell which "
							+ "side of the hall you ended up on- presuming there is another side at all.";
				} else {
					incrementDistance();
					player.takeFatigue(1);
					return "You press onward, walking through the hall. It looks as endless as ever.";
				}
			}
		};
		
		roomActions.add(walkForward);
		
		if (distance > 0) {
			RoomAction turnAround = new RoomAction("Turn Around") {
				@Override
				public String resolveAction(Player player) {
					reverseDirection();
					return "You turn around and start heading the other way.";
				}
			};
		
			roomActions.add(turnAround);
		}

	}
	
	private void reachHallEnd() {
		if (distance == 0) {
			roomSide = initialSide;
		} else  {
			roomSide = oppositeSide;
		}
	}
	
	private void reverseDirection() {
		switch (direction) {
		case BACKWARD:
			direction = Direction.FORWARD;
			break;
		case FORWARD:
			direction = Direction.BACKWARD;
			break;
		default: //not possible
			break;
		}
	}

	private void incrementDistance() {
		switch (direction) {
		case BACKWARD:
			distance--;
			break;
		case FORWARD:
			distance++;
			break;
		default: //not possible
			break;
		}
	}
	
	private void resetDistance() {
		distance = 0;
	}
	
}
