package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import mobs.*;

public class BlessedFountain extends Room {
	
	private int healValue;
	private int healStamina;
	private int fatigueHeal;
	private int damageBuff;
	private int damageRounds;
	private int minCoins;
	private int maxCoins;
	private int curseMod;
	private final String COMPLETED = "\nThe water stops flowing and loses its shimmer, its power spent.";
	
	public BlessedFountain(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Blessed Fountain";
		healValue = 4;
		healStamina = 2;
		fatigueHeal = -2;
		damageBuff = 1;
		damageRounds = 10;
		minCoins = 2;
		maxCoins = 5;
		curseMod = 1;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		
		str += "A glint catches your eye from the fountain's lowest basin. Previous adventurers must have left offerings or "
				+ "made wishes in the fountain.";
		return str;
	}
	
	public String getDescription() {
		return "In the center of the room stands a beautiful and ornate stone fountain, with water "
				+ "trickling from the top two layers into the bottom. The water looks pure and refreshing.";
	}
	
	public String getCompletedDescription() {
		return "In the center of the room stands a beautiful and ornate stone fountain. Its water "
				+ "has stopped flowing.";
	}
	
	public void initRoomActions(Player player) {
		
		RoomAction drink = new RoomAction("Drink") {
			@Override
			public String resolveAction(Player player) {
				String str = "You take a long draught from the cool, refreshing water.\n";
				str += player.healDamage(healValue);
				str += "\n" + player.takeFatigue(fatigueHeal);
				str += COMPLETED;
				setCompleted(true);
				return str;
			}
		};
		
		RoomAction bathe = new RoomAction("Bathe") {
			@Override
			public String resolveAction(Player player) {
				String str = "You take some time to wash in the clear water. The dirt and grime of the dungeon falls away, "
						+ "leaving you feeling clean and refreshed.\n";
				str += player.healStamina(healStamina);
				str += "\n" + player.takeFatigue(fatigueHeal);
				str += COMPLETED;
				setCompleted(true);
				return str;
			}
		};
		
		RoomAction cleanWeapon = new RoomAction("Clean Weapon") {
			@Override
			public String resolveAction(Player player) {
				String str = "You approach the fountain, drawing your weapon. It could use some maintenance, and the water "
						+ "looks purer than anything else you'll find down here. You set to work cleaning the weapon with "
						+ "water from the fountain.\n";
				str += player.buffDamage(damageBuff, damageRounds);
				str += COMPLETED;
				setCompleted(true);
				return str;
			}
		};
		
		RoomAction makeWish = new RoomAction("Make a Wish (1 Coin)") {
			@Override
			public String resolveAction(Player player) {
				String str = "";
				if (player.getCoins() > 0) {
					str += "You approach the fountain and toss in a coin, not really sure what to wish for. You find yourself "
							+ "breathing easier, however, like a heavy burden has been lifted from your shoulders.";
					str += COMPLETED;
					player.spendCoins(1);
					player.setCurse(0);
					setCompleted(true);
				} else {
					str += "You don't have any coins to offer!";
				}

				return str;
			}
		};
		
		roomActions.add(drink);
		roomActions.add(bathe);
		roomActions.add(cleanWeapon);
		roomActions.add(makeWish);
		
		if (isSearched()) {
			RoomAction takeCoins = new RoomAction("Take the Coins") {
				@Override
				public String resolveAction(Player player) {
					String str = "You take the coins, your hands dripping with pure water as you transfer them "
							+ "to your coin pouch. A dark feeling settles on your shoulders as the water in the "
							+ "fountain stops flowing.\n";
					str += player.addCoins(GameScreen.generateRandom(minCoins, maxCoins));
					player.modCurse(curseMod);
					setCompleted(true);
					return str;
				}
			};
			
			roomActions.add(takeCoins);
		}
	}
}
