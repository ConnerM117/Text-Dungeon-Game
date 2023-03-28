package rooms.story;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Coins;
import mobs.Player;
import rooms.Room;

public class DragonHoard extends Room {
	
	private int curseMod;
	private int minCoins;
	private int maxCoins;
	
	public DragonHoard(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Dragon Hoard";
		combatChance = NO_COMBAT_CHANCE;
		curseMod = 1;
		minCoins = 2;
		maxCoins = 4;
	}

	@Override
	public String getDescription() {
		return "Your eyes widen as your eyes take in the largest accumulation of wealth you have ever seen. Piles of gold, jewels, "
				+ "and other precious things entirely cover the floor of this cavern.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction takeTreasure = new RoomAction("Take Treasure") {
			@Override
			public String resolveAction(Player player) {
				player.modCurse(curseMod);
				player.addToInventory(new Coins(minCoins, maxCoins));
				return "You take a bit of treasure, but your pack feels heavier than it should for only a handful of coins. It's said "
						+ "that dragon's gold is cursed...";
			}
		};
		
		roomActions.add(takeTreasure);
		
		if (player.getInventory().containsKey(player.STOLEN_GOODS)) {
			RoomAction returnGoods = new RoomAction("Return Stolen Goods") {
				@Override
				public String resolveAction(Player player) {
					player.removeFromInventory(player.STOLEN_GOODS);
					GameScreen.isStolenLootReturned = true;
					return "As you return the stolen loot, you feel a sense of relief, as if a weight you didn't know you had was "
							+ "lifted from your shoulders.";
				}
			};
			
			roomActions.add(returnGoods);
		}
	}

}
