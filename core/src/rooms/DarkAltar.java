package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import mobs.Player;

public class DarkAltar extends Room {

	private int curseMod;
	private int healCurse;
	private int statBuff;
	private int cost;
	
	public DarkAltar(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Dark Altar";
		curseMod = 1;
		statBuff = 5;
		cost = 1;
		healCurse = 3;
	}

	@Override
	public String getDescription() {
		return "This dimly lit place has an altar at its center, with a statue of a robed figure shrouded in shadow. One hand is "
				+ "held to its breast, and the other is outstretched, as if expecting payment.";
	}

	@Override
	public String getCompletedDescription() {
		return getDescription();
	}

	@Override
	public void initRoomActions(Player player) {
		RoomAction placeCoin = new RoomAction("Place Coin") {
			@Override
			public String resolveAction(Player player) {
				if (player.getCoins() < 1)
					return "You don't have any coins to give!";
				
				setCompleted(true);
				player.modCurse(curseMod);
				switch (GameScreen.generateRandom(1, 5)) {
				case 1:
					player.modBaseAccuracy(statBuff);
					break;
				case 2:
					player.modBaseAgility(statBuff);
					break;
				case 3:
					player.modBaseMind(statBuff);
					break;
				case 4:
					player.modBaseStrength(statBuff);
					break;
				case 5: 
					player.modBaseToughness(statBuff);
					break;
				}
				return "You place a coin into the outstretched hand. For a moment nothing happens, but then it dissolves into shadow. "
						+ "You feel... stronger somehow, if a bit uneasy. Dark dealings rarely turn out in the dealer's favor..."
						+ "\n" + player.spendCoins(cost);
			}
		};
		
		roomActions.add(placeCoin);
		
		if (player.isHoly()) {
			RoomAction bless = new RoomAction("Bless Altar") {
				@Override
				public String resolveAction(Player player) {
					setCompleted(true);
					player.modCurse(healCurse);
					return "You raise your holy implement and utter a prayer to the gods. The shadows become shallow from the "
							+ "light that flares from you, and the room seems brighter. A weight is lifted from your shoulders.";
				}
			};
			
			roomActions.add(bless);
		}
	}

}
