package rooms.story;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.consumables.EvilArtifact;
import items.runes.Rune;
import mobs.Player;
import rooms.Room;

public class ArcaneStudy extends Room {

	private boolean hasSpoken;
	private String evilArtifact;
	
	public ArcaneStudy(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Arcane Study";
		combatChance = NO_COMBAT_CHANCE;
		hasSpoken = false;
		evilArtifact = new EvilArtifact().getName();
	}

	@Override
	public String getDescription() {
		if (!hasSpoken)
			return "You stop at the entrance to what looks like a study, after hearing someone turn a page within. \"Come in,\" says "
					+ "a voice. When you enter, a woman looks up from a desk surrounded by bookshelves, her features gaunt. A staff leans "
					+ "against the chair. \"I believe we can come to an agreement that is mutually beneficial,\" she says.";
		return "\"If you want that lich gone for good- I know I do- then bring me that evil artifact. I will ensure you are rewarded.\"";
	}

	@Override
	public String getCompletedDescription() {
		return "\"I unfortunately cannot help you fight the lich directly; suffice it to say he has bound my magic against him. But "
				+ "I wish you luck. I trust in your ability to succeed.\"";
	}

	@Override
	public void initRoomActions(Player player) {
		if (!hasSpoken) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					GameScreen.setLogger("\"I'm glad you are willing. You see, I am an apprentice to the lich that calls this place his domain. But "
							+ "he has grown weak in his age. To move onward, you will need to defeat him anyway- as I trust that is "
							+ "your motive.");
					hasSpoken = true;
					return "However, his soul is bound to an artifact that he keeps in the Guarded Sanctum. He will resurrect unless "
							+ "that artifact is destroyed, and I need him gone permanently. Bring me the artifact so that I can destroy "
							+ "it, and I will reward you well.";
				}
			};
			
			roomActions.add(speak);
		}
		
		if (player.getInventory().containsKey(evilArtifact)) {
			RoomAction giveArtifact = new RoomAction("Give Artifact") {
				@Override
				public String resolveAction(Player player) {
					player.removeFromInventory(evilArtifact);
					setCompleted(true);
					return "She slips the artifact into her robes with a sly grin. \"Thank you for your assistance,\" she says. "
							+ "\"Your payment, as promised.\"\n" + player.addToInventory(Rune.getRandGreaterRune());
				}
			};
			
			roomActions.add(giveArtifact);
		}
	}

}
