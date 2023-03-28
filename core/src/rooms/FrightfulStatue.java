package rooms;

import floors.Floor;
import mobs.*;

public class FrightfulStatue extends Room {
	
	private boolean hasBlood;
	private boolean isToppled;
	private int damageBuff;
	private int toughnessBuff;
	private int rounds;
	private int tempHP;
	
	public FrightfulStatue(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Frightful Statue";
		hasBlood = false;
		isToppled = false;
		damageBuff = 1;
		rounds = 10;
		tempHP = 2;
	}
	
	@Override
	public String getDescription() {
		if (hasBlood)
			return "In the middle of the room stands a tall stone gargoyle with terrible features and long "
					+ "horns. It clutches a large blood-filled bowl in its claws. You have the revolting urge to drink.";
		else
			return "In the middle of the room stands a tall stone gargoyle with terrible features and long "
				+ "horns. It clutches a large bloodstained bowl in its claws. Perhaps it expects a sacrifice?";
	}
	
	@Override
	public String getCompletedDescription() {
		if (isToppled)
			return "The stone statue of a tall gargoyle lies toppled on the floor, cracked and crumbling. Though "
					+ "it's inert and in disrepair, the sight of it is still chilling.";
		else			
			return "The tall stone gargoyle goggles at you with its terrible features and long horns. Though "
				+ "you've seen it before, the sight of it is still chilling.";
	}
	
	public void initRoomActions(Player player) {
		
		RoomAction drink = new RoomAction("Drink from Bowl") {
			@Override
			public String resolveAction(Player player) {
				String str = "The taste of iron fills your mouth as you drink. Power pounds through your veins, and you feel an "
						+ "insatiable bloodlust. You continue onward, ready for battle!";
				str += player.buffDamage(damageBuff, rounds);
				setCompleted(true);
				return str;
			}
		};
		
		RoomAction sacrifice = new RoomAction("Sacrifice Blood") {
			@Override
			public String resolveAction(Player player) {
				String str = "";
				if (hasBlood) {
					str += "The bowl is already filled with blood! No sense in sacrificing more.";
				} else {
					str += "You draw a knife and slide it across your palm, allowing a few precious drops of blood to drip into the "
							+ "stone bowl. You look down and see that it is full; far more full than it could possibly be from your own drops! "
							+ "You feel the sudden urge to drink.";
					str += player.takeDamage(2);
					hasBlood = true;
					roomActions.add(drink);
				}
				return str;
			}
		};
		
		RoomAction topple = new RoomAction("Topple Statue") {
			@Override
			public String resolveAction(Player player) {
				String str = "";
				if (player.isStrong()) {
					str += "You set yourself against the statue and push with all your might, utilizing as much leverage as you can manage. "
							+ "With a mighty heave the statue tips and cracks on the floor with a bang. You feel as though something approves "
							+ "of this development...";
					str += player.setTempHP(tempHP);
					str += player.buffToughness(toughnessBuff, rounds);
					isToppled = true;
					setCompleted(true);
				} else {
					str += "You set yourself against the statue and push with all your might, but it doesn't budge and shows no sign of "
							+ "doing so anytime soon. Wearied, you admit defeat.";
					str += player.spendStamina(1);
				}
				return str;
			}
		};
		
		RoomAction exorcize = new RoomAction("Exorsize") {
			@Override
			public String resolveAction(Player player) {
				String str = "You raise your implement and utter a prayer censuring the evil you feel in the room. "
						+ "You see no difference in the statue, but the evil feeling leaves, and you feel spiritual reassurance "
						+ "that something is watching you in approval.";
				str += player.setTempHP(tempHP);
				str += player.buffToughness(toughnessBuff, rounds);
				setCompleted(true);
				return str;
			}
		};
		
		RoomAction sacrificeRat = new RoomAction("Sacrifice Rat") {
			@Override
			public String resolveAction(Player player) {
				String str = "You extract the rat from its bag, and do what needs to be done in the bloodstained bowl. You "
						+ "look down and see that it is full; far more full than it could possibly be from only the rat! "
						+ "You feel the sudden urge to drink.";
				hasBlood = true;
				roomActions.add(drink);
				return str;
			}
		};
		
		if (hasBlood) {
			roomActions.add(drink);
		} else {
			roomActions.add(sacrifice);
			if (player.getInventory().containsKey("Rat in a Bag")) {
				roomActions.add(sacrificeRat);
			}
		}
		roomActions.add(topple);
		if (player.isHoly()) {
			roomActions.add(exorcize);
		}
		

	}

}
