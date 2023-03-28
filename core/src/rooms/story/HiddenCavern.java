package rooms.story;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.gear.StaffMagic;
import mobs.Player;
import rooms.Room;

public class HiddenCavern extends Room {

	public HiddenCavern(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Hidden Cavern";
		combatChance = NO_COMBAT_CHANCE;
	}

	@Override
	public String getDescription() {
		return "The entrance to this cavern is small and off to the side, easy to miss; especially for troll eyes! A man stands as you "
				+ "enter. \"Greetings,\" he says, tipping his wide-brimmed pointy hat and adjusting his long robes. \"You haven't seen "
				+ "my apprentice have you?\"";
	}

	@Override
	public String getCompletedDescription() {
		return "The entrance to this cavern is small and off to the side, easy to miss; especially for troll eyes!";
	}

	@Override
	public void initRoomActions(Player player) {

		if (!isCompleted()) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					if (GameScreen.isEscortingWizard) {
						GameScreen.isEscortingWizard = false;
						player.addToInventory(new StaffMagic()); //implement staves
						setCompleted(true);
						return "\"Brilliant!\" says the wizard as the apprentice sheepishly walks toward him. \"That should teach you to "
								+ "go off by yourself. But as for you. A reward.\" He holds out a short staff and hands it to you. "
								+ "\"Now then, we should be going.\" With a sudden brilliant flash of light, both wizards are gone.";
					} else if (GameScreen.isWizardDead) {
						setCompleted(true);
						return "\"Ah, is that so? He went down against a... I see. Well, that is horrible business. I suppose I should "
								+ "go looking for him. That way, you said? Thank you for your assistance.\" With that, the wizard "
								+ "quickly makes his way out of the cavern, and out of sight.";
					} else {
						return "\"No? Ah, well. He's gone off again and I can't seem to find him. Keep an eye out for him, would you?\"";
					}
				}
			};
			
			roomActions.add(speak);
		}
		
	}

}
