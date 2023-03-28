package rooms.story;

import floors.Floor;
import items.consumables.BlueMushroom;
import items.consumables.Mutagen;
import mobs.Player;
import rooms.Room;

public class AlchemyWorkshop extends Room {

	private boolean hasSpoken;
	private String blueMushroom;
	
	public AlchemyWorkshop(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Alchemy Workshop";
		combatChance = NO_COMBAT_CHANCE;
		type = Type.STORY;
		hasSpoken = false;
		blueMushroom = new BlueMushroom().getName();
	}
	
	@Override
	public String getDescription() {
		if (!hasSpoken)
			return "Against the far wall, past rows of shelves full of alchemical ingredients, works a tall figure at a table "
				+ "piled with all sort of alchemical equipment. He turns as you enter. \"Oh good,\" he says, \"You look like "
				+ "just the sort of person that can help me!\"";
		return "\"Remember,\" the wererat alchemist says, \"I just need that Blue Mushroom. I'll make it worth your while!\"";
	}

	@Override
	public String getCompletedDescription() {
		return "The alchemist toils away at the table. \"Thank you for your help, but I'm busy!\" he calls.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (!hasSpoken) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					hasSpoken = true;
					return "\"Oh good, I'm glad you're happy to help!\" He steps closer, and you realize that the man is actually a "
							+ "wererat! You raise your weapons, but he interrupts, \"No, wait! I'm trying to find a cure, and I'm so "
							+ "close! I just need one more ingredient: a Blue Mushroom from my Mushroom Garden close by. Unfortunately "
							+ "another of my creations took over the place. If you can defeat it and bring me the Blue Mushroom, I "
							+ "will make it worth your while!";
				}
			};
			
			roomActions.add(speak);
		}
		
		if (player.getInventory().containsKey(blueMushroom)) {
			RoomAction giveMushroom = new RoomAction("Give Mushroom") {
				@Override
				public String resolveAction(Player player) {
					player.removeFromInventory(blueMushroom);
					setCompleted(true);
					return "\"Thank you!\" the wererat exclaims. \"I may finally rid myself of this curse! But of course I did "
							+ "not forget about your reward! This is a mutagen that was a result of one of my failed experiments. I "
							+ "trust you will be able to put it to good use.\n" + player.addToInventory(new Mutagen());
				}
			};
			
			roomActions.add(giveMushroom);
		}
		
	}

}
