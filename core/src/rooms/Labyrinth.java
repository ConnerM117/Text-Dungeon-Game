package rooms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.consumables.Chalk;
import mobs.Player;

public class Labyrinth extends Room {

	private boolean isConfused;
	private String chalk;
	
	public Labyrinth(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Labyrinth";
		isConfused = false;
		
		this.chalk = new Chalk().getName();
	}
	
	@Override
	public String getDescription() {
		return "The tunnels here twist and turn through multiple intersections; it would be easy to lose yourself down here...";
	}

	@Override
	public String getCompletedDescription() {
		return "The tunnels here twist and turn through multiple intersections, but you've marked the way with Chalk. Navigating "
				+ "the labyrinth should no longer pose an issue.";
	}

	@Override
	public void initRoomActions(Player player) {
		if (!isChalked())
			isConfused = true;
		
		if (!isCompleted()) {
			RoomAction navigate = new RoomAction("Navigate") {
				@Override
				public String resolveAction(Player player) {
					if (player.notice()) {
						isConfused = false;
						return "Through the power of will, masterful directional skill, or sheer luck, "
								+ "you're able to make sense of the labyrinth's twists and turns, but there's no guarantee you'll "
								+ "be able to do it again. ";
					} else {
						isConfused = true;
						return "The labyrinth confounds you, and you get turned around...";
					}
				}
			};
			
			roomActions.add(navigate);
		}
		
		if (player.getInventory().containsKey(chalk)) {
			RoomAction useChalk = new RoomAction("Use Chalk") {
				@Override
				public String resolveAction(Player player) {
					player.removeFromInventory(chalk);
					isConfused = false;
					setChalked(true);
					setCompleted(true);
					return "You enter the labyrinthian tunnels, using chalk to mark where you've been. You navigate it with ease, "
							+ "and it should pose no more problems in the future.";
				}
			};
			
			roomActions.add(useChalk);
		}
		
	}
	
	public Set<Room> getAdjacentRooms() {
		if (isConfused) { //return a single random adjacent room
			List<Room> tempList = new ArrayList<>(adjacentRooms);
			Set<Room> newSet = new HashSet<>();
			newSet.add(tempList.get(GameScreen.generateRandom(0, tempList.size()-1)));
			return newSet;
		} else {
			return adjacentRooms;
		}
	}

}
