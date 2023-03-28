package com.textdungeon.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.textdungeon.game.TextDungeon.ScreenKey;

import adventurers.Adventurer;
import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import adventurers.perks.general.Perk;
import adventurers.perks.kin.JackOfAllTrades;
import combat.CombatEncounter;
import combat.MimicEncounter;
import core.Chest;
import core.Dungeon;
import floors.Floor;
import items.Item;
import items.runes.Rune;
import items.weapons.Weapon;
import mobs.Mob;
import mobs.Player;
import rooms.Room;
import rooms.Room.RoomAction;
import traps.Trap;

/*
 * TODO List of known bugs: (# indicates that the bug has been addressed but the solution isn't 100% tested)
 * 		#Jack of All Trades perk brings up BuiltOx, Survivor, and Swindler twice (shouldn't show at all)
 * 		#Poison/Burning attacks (from a trap in this case) can proc poison/burning even if a Doppelganger takes the hit
 * 		#Damage from poison/burning can be taken by Doppelgangers
 * 		
 * 		Combat has a couple of "leftover clicks" at the end of each round. Probably empty events that need to be cycled, but they
 * 			shouldn't exist in the first place. Leftover events are always equal to number of enemies remaining
 * 
 * 		Trap Disarm button goes to previous room
 * 
 * 		Refactor: All specialAction and staminaAction need to return a String. All Log calls in combat should be happening in this class.
 * 
 * TODO Future Implementation:
 * 		Boss Keys: key is required to enter the boss room
 * 
 * 		Knight: replace Knight's Duel with Bulwark (armor+)
 * 
 * 		Armor Glyphs: (function identically to weapon runes, but on Armor)
 * 			Air (+Agility), Awareness (+Mind), Defense (+minArmor), Earth (+Toughness), 
 * 			Fire (grants FlameCloak), Juggernaut (+min/maxArmor), Vigor (+Stamina), Water (fire immunity)
 * 
 * 		Unlockables:
 * 			Kin: Goblin, Lizardman
 * 				Goblin: Iron Stomach, increases fatigue healing from Rations
 * 				Lizardman: poison immunity, regeneration
 * 
 * 		Sound Effects:
 * 			Level Up
 * 			Combat Start/End
 * 			Pick up item
 * 			Button click
 */

public class GameScreen implements Screen {
	
	private static float BUTTON_PAD = 5f;
	private static float BUTTON_WIDTH = 150f;
	
	final TextDungeon game;
	private final Player player;
	public static GameEventHandler eventHandler;
	
	Drawable blackBackground = new TextureRegionDrawable(new TextureRegion(new Texture(1, 1, Format.RGB565)));

	private static Dungeon dungeon;
	public static int floorCounter;
	public static Floor currentFloor;
	public static Room currentRoom;
	public static Room previousRoom;
	
	public static List<Mob> mobsToAdd = new ArrayList<>();
	public static boolean combatIsRunning;
	private static int roundCounter;
	private boolean playerIsFleeing;
	private static Mob playerTarget;

	private static final String DUNGEON_WELCOME = "You descend into the dungeon, where few have gone and fewer have returned, "
			+ "determined to etch your name into history as not only a survivor, but a legendary adventurer who conquered its "
			+ "depths. For you, retreat is not an option...";
	private static final String FLOOR = "Floor";
	
	public static final String FLEEING = "Run, before they cut off your escape!";
	public static final String CANT_FLEE = " can't escape!";
	public static final String NEXT_FLOOR = "Proceed to next Floor";
	public static final String MOVE_ON = "Onward";
	public static final String STAY = "Stay";
	public static final String SEARCH = "Search";
	public static final String GET_ITEMS = "Loot the Room";
	public static final String FIND_ITEMS = "You find the following items...";
	public static final String NO_ITEMS = "There's nothing here to loot!";
	public static final String WHERE_NOW = "Which way do you go?";
	public static final String UNKNOWN = "???";
	public static final String RETURN = "You return to ";
	public static final String TOUCH_TO_CONTINUE = "\nTouch anywhere to continue.";
	public static final String NEVERMIND = "Nevermind";
	public static final String KEY_RING = "Key Ring";
	public static final String NO_KEYS = "You don't have any keys!";
	public static final String GAME_OVER = "Game Over";
	
	public static final int roomFatigue = 1; //the fatigue taken when moving through a room
	public static final int LOCKPICK_BONUS = 25;
	
	public static boolean isBlacksmithFreed;
	public static boolean wraithIsSlain;
	public static boolean isEscortingWizard;
	public static boolean isWizardDead;
	public static int escortCombats = 0;
	public static final int MAX_ESCORT_COMBATS = 3;
	public static boolean isStolenLootReturned;
	public static boolean isForbiddenTomeRead;
	public static boolean isCursedPrisonStudied;
	
	Adventurer.StatChoice statChoice;
	Perk perkChoice;
	
	InputAdapter touchToBegin;
	
	SpriteBatch batch;
	Image screenFader;
	Image textFader;
	Label screenLabel;
	Label floorNumberLabel;
	Label floorNameLabel;
	AlphaAction fadeInAction;
	AlphaAction fadeOutAction;

	Stage stage;
	OrthographicCamera camera;
	Viewport viewport;
	Table uiTable;
	Table characterStatsTable;
	Table rightSideTable;
	public static Label logger;
	static Table optionsTable;

	Label lblName;
	Label lblAdventurer;
	Label lblStats;
	Table charDetailsTable;
	Label charDetailsLabel;
	
	public static Table combatChoiceTable; //each combat choice with extra choices will implement this on their own
	
	public static Table shopTable;
	public static Table playerSellTable; //implemented by Shop
	public static Table shopInventoryTable; //implemented by Shop
	public static Table shopDescriptionTable; //implemented by Shop
	public static Label shopDescription;
	public static Table shopTopRowButtons;
	Stack shopScreenStack;
	
	public static Table miscRoomTable; //implemented by Smithy
	
	Table gameOverTable;
	
	Table topRowTable;
	TextButton inventoryButton;
	TextButton mapButton;
	TextButton menuButton; // brings up save/quit/settings menu
	TextButton choicesButton; //brings up a list of the player's combat choices and their descriptions
	Label floorLabel;
	Label roomLabel;

	Table inventory;
	Table map;
	Table menu;
	Table choicesMenu;
	Label choicesLabel;
	Table levelUpTable;
	Label levelUpLabel;
	
	Table perkChoiceTable;
	Label perkChoiceDescription;

	Item selectedItem;
	Table leftInventory;
	Label inventoryDescription;
	Label equippedItemsLabel;
	TextButton useButton;
	Table runeChoiceTable;

	ScrollPane mapScroller;
	Label mapLabel;

	public GameScreen(final TextDungeon game, final Player player) {
		this.game = game;
		this.player = player;
		eventHandler = new GameEventHandler(this);
		
		dungeon = new Dungeon();
		floorCounter = 1;
		currentFloor = dungeon.getDungeonFloors().get(0);
		currentRoom = null;
		previousRoom = null;
		combatIsRunning = false;
		roundCounter = 0;
		playerIsFleeing = false;
		playerTarget = null;
		statChoice = null;

		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 480);
		stage = new Stage(viewport);
		batch = new SpriteBatch();
		
		touchToBegin = new InputAdapter() {
			@Override
			public boolean touchUp(int x, int y, int pointer, int button) {
				Gdx.input.setInputProcessor(null);
				textFader.addAction(fadeOutAction);
				
				Gdx.input.setInputProcessor(new InputAdapter() {
					@Override
					public boolean touchUp(int x, int y, int pointer, int button) {
						Gdx.input.setInputProcessor(null);
						textFader.addAction(fadeInAction);
						
						RunnableAction setInvisible = new RunnableAction();
						setInvisible.setRunnable(new Runnable() {
							@Override
							public void run() {
								screenLabel.clear();
								textFader.setVisible(false);
								screenFader.setVisible(false);
								screenLabel.setVisible(false);
								floorNumberLabel.setVisible(false);
								floorNameLabel.setVisible(false);
							}
						});
						
						textFader.addAction(new SequenceAction(fadeInAction, fadeOutAction, setInvisible));
						screenFader.addAction(new SequenceAction(fadeInAction, fadeOutAction, setInvisible));
						
						Gdx.input.setInputProcessor(stage);
						
						return true;
					}
				});
				
				return true;
			}
		};
		
		uiTable = new Table();
		uiTable.setFillParent(true);
		characterStatsTable = new Table();
		rightSideTable = new Table();

		topRowTable = new Table();
		initTopRow();
		characterStatsTable = new Table();
		initCharacterStatsTable();
		rightSideTable = new Table();
		initRightSideTable();

		initCharDetailsTable();
		
		initLevelUpTable();
		combatChoiceTable = new Table();
		combatChoiceTable.setFillParent(true);
		padTable(combatChoiceTable, 3);
		
		initShopTable();
		initGameOverTable();
		
		miscRoomTable = new Table();

		stage.addActor(uiTable);
		uiTable.add(topRowTable).colspan(2).center();
		uiTable.row();
		uiTable.add(characterStatsTable).width(stage.getWidth() / 3).center();
		uiTable.add(rightSideTable).expand().pad(20f);
		uiTable.pad(20f);

		stage.addActor(inventory);
		stage.addActor(runeChoiceTable);
		stage.addActor(map);
		stage.addActor(menu);
		stage.addActor(choicesMenu);
		stage.addActor(levelUpTable);
		stage.addActor(combatChoiceTable);
		stage.addActor(shopTable);
		stage.addActor(miscRoomTable);
		stage.addActor(gameOverTable);
		stage.addActor(charDetailsTable);
		inventory.setVisible(false);
		runeChoiceTable.setVisible(false);
		map.setVisible(false);
		menu.setVisible(false);
		choicesMenu.setVisible(false);
		levelUpTable.setVisible(false);
		combatChoiceTable.setVisible(false);
		shopTable.setVisible(false);
		miscRoomTable.setVisible(false);
		gameOverTable.setVisible(false);
		charDetailsTable.setVisible(false);

		initFaders();
		
		displayRoom(currentFloor.getFloorRooms().get(0));
	}

	@Override
	public void show() {
		updateCharacterStatsTable();		
		screenLabel.clear();
		screenLabel.setText(DUNGEON_WELCOME);

		Gdx.input.setInputProcessor(touchToBegin);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);
		
		if (!eventHandler.isEventQueueEmpty() && Gdx.input.getInputProcessor().equals(stage) && !levelUpTable.isVisible()) {
			eventHandler.setInputToEventHandler();
		}

		camera.update();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}
	
	private void displayWin() {
		//TODO implement Win screen
	}
	
	void gameOver() {
		logQueue(player.getName() + " has been defeated!");
		eventHandler.clearEvents();
		gameOverTable.setVisible(true);
	}

	private void initFaders() {
		screenFader = new Image(new TextureRegion(new Texture(1, 1, Format.RGB565)));
		screenFader.setSize(stage.getWidth(), stage.getHeight());
		screenFader.setOrigin(stage.getWidth()/2, stage.getHeight()/2);
		screenFader.setColor(Color.BLACK);

		textFader = new Image(new TextureRegion(new Texture(1, 1, Format.RGB565)));
		textFader.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		textFader.setOrigin(stage.getWidth()/2, stage.getHeight()/2);
		textFader.setColor(Color.BLACK);

		fadeInAction = new AlphaAction();
		fadeInAction.setAlpha(1.0f);
		fadeInAction.setDuration(3.0f);

		fadeOutAction = new AlphaAction();
		fadeOutAction.setAlpha(0.0f);
		fadeOutAction.setDuration(3.0f);
		
		screenLabel = new Label("", TextDungeon.skin);
		screenLabel.setWrap(true);
		screenLabel.setWidth(Gdx.graphics.getWidth()/2);
		screenLabel.setHeight(Gdx.graphics.getHeight()/2);
		screenLabel.setOrigin(stage.getWidth()/2, stage.getHeight()/2);
		
		floorNumberLabel = new Label("", TextDungeon.skin);
		floorNumberLabel.setWidth(Gdx.graphics.getWidth()/2);
		floorNumberLabel.setHeight(Gdx.graphics.getHeight()/2);
		floorNumberLabel.setOrigin(stage.getWidth()/2, stage.getHeight() * 2/3);
		
		floorNameLabel = new Label("", TextDungeon.skin);
		floorNameLabel.setWidth(Gdx.graphics.getWidth()/2);
		floorNameLabel.setHeight(Gdx.graphics.getHeight()/2);
		floorNameLabel.setOrigin(stage.getWidth()/2, stage.getHeight()/3);

		stage.addActor(screenFader);
		stage.addActor(screenLabel);
		stage.addActor(floorNumberLabel);
		stage.addActor(floorNameLabel);
		stage.addActor(textFader);
	}

	private void addMobs(List<Mob> mobs) {
		for (Mob m : mobsToAdd) {
			mobs.add(m);
		}
		mobsToAdd.clear();
	}
	
	private String displayMobs(List<Mob> mobs) {
		String str = "There are " + mobs.size() + " enemies remaining!";
		for (Mob m : mobs) {
			if (m.getCurrentHitPoints() > 0)
				str += "\n" + m.getStatistics();
		}
		return str;
	}
	
	private void removeDeadMobs(List<Mob> mobs) {
		int XP = 0;
		for (Iterator<Mob> itr = mobs.iterator(); itr.hasNext();) {
			Mob m = itr.next();
			if (m.getCurrentHitPoints() <= 0) { //if the mob has 0 hit points or less, remove it from combat
				
				if (player.getLevel() <= currentFloor.getFloorNumber() + 2)
					XP += m.getXP();
				itr.remove();
			}
		}
		
		if (XP > 0)
			log(player.receiveXP(XP));
		
		if (player.getXP() >= player.REQUIRED_LEVEL_XP) {
			eventHandler.addEvent(new GameEvent() {
				@Override
				public void runEvent() {
					logger.setText(player.levelUp());
					updatePerkChoiceTable();
					showLevelUpTable();
				}
			});
		}
	}
	
	public void chooseTarget(List<Mob> mobs, ChoiceTargetsMob choice, CombatEncounter encounter, Room room) {
		optionsTable.clear();
		logger.setText("Choose your target:");
		int counter = 0;
		for (Mob m : mobs) {
			if (m.getCurrentHitPoints() > 0) {
				TextButton button = new TextButton(m.getName(), TextDungeon.skin);
				button.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						playerTarget = m;
						logQueue(choice.targetMob(player, m));
						
						checkPlayerStatus();
						
						removeDeadMobs(mobs);
						
						mobsTakeCombatTurns(mobs);
						
						endCombatRound(encounter, mobs, room);
					}
				});
				
				optionsTable.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD);
				counter++;
				if (counter % 3 == 0)
					optionsTable.row();
			}
		}
	}
	
	private void playerChooseCombatOption(CombatEncounter encounter, List<Mob> mobs, Room room) {
		optionsTable.clear();
		roundCounter++;
		int counter = 0;
		
		for (Choice choice : player.getCombatChoices()) {
			
			TextButton button = new TextButton(choice.getName(), TextDungeon.skin);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (!choice.runChoice(player, mobs)) {//if the choice was invalid, get another choice
						playerChooseCombatOption(encounter, mobs, room);
					}
					else {
						if (choice.requiresTarget()) {
							chooseTarget(mobs, (ChoiceTargetsMob)choice, encounter, room);
							return;
						}
						
						checkPlayerStatus();
						
						removeDeadMobs(mobs);
						
						mobsTakeCombatTurns(mobs);
						
						endCombatRound(encounter, mobs, room);
					}
				}
			});
			
			optionsTable.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
		
		TextButton fleeButton = new TextButton("Flee", TextDungeon.skin);
		fleeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (player.getCurrentStamina() < 1) {
					log(player.getName() + " doesn't have the Stamina to escape!");
				} else {
					//spend 1 stamina, unless the player is Nimble (elf perk) and also succeeds an Agility test
					if (!player.isNimble() || !player.isAgile())
						player.spendStamina(1);
					
					playerIsFleeing = true;
					player.clearCombatLengthBuffs();
					combatIsRunning = false;
					displayLocationChoices(room);
					return;
				}
			}
		});
		
		optionsTable.add(fleeButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
	}
	
	protected void endCombatRound(CombatEncounter encounter, List<Mob> mobs, Room room) {
		if (mobs.isEmpty()) { //all mobs are dead
			player.clearCombatLengthBuffs();
			combatIsRunning = false;
			currentFloor.modifyXPLimit(1);
			if (room.isBossRoom()) { //they beat the boss
				room.setCompleted(true);
				if (currentFloor.getFloorNumber() == 10) { //they beat the final boss
					displayWin();
					return;
				}
			} else if (room.hasScriptedCombat()) {
				room.setCompleted(true);
			} else if (encounter instanceof MimicEncounter) {
				room.getChest().setMimicAlive(false);
			}
			log(encounter.getVictoryDescription());
			eventHandler.addEvent(new GameEvent() {
				
				 @Override public void runEvent() { 
					 displayRoom(room);
				 } 
				 
			});
			
		} else { //there are mobs alive
			log(displayMobs(mobs));
			
			playerChooseCombatOption(encounter, mobs, room);			
		}
		updateCharacterStatsTable();
	}

	private void mobsTakeCombatTurns(List<Mob> mobs) {
		for (Mob m : mobs) {
			if (isEscortingWizard && generateRandom(1, 3) == 3) {
				String str = "The wizard cries out as " + m.getName() + " attacks!";
				if (m.isAccurate()) {
					str += (m.getName() + " lands a solid blow on the wizard, who falls to the ground, dead.");
					isEscortingWizard = false;
					isWizardDead = true;
				} else {
					str += ("But the attack misses!");
				}
				log(str);
			} else {
				eventHandler.addEvent(new GameEvent() {
					@Override
					public void runEvent() {
						logger.setText(m.getCombatChoice(player, mobs));
					}
				});
				
				eventHandler.addEvent(new GameEvent() {
					@Override
					public void runEvent() {
						logger.setText(m.checkRoundCounters());
						updateCharacterStatsTable();
					}
				});
			}
			if (player.getCurrentHitPoints() <= 0) {
				eventHandler.addEvent(new GameEvent() {
					@Override
					public void runEvent() {
						gameOver();
					}
				});
				return;
			}
		}
		removeDeadMobs(mobs);
		addMobs(mobs);
	}

	public void runCombat(CombatEncounter encounter, List<Mob> mobs, Room room) {
		combatIsRunning = true;
		roundCounter = 0;
		if (isEscortingWizard)
			escortCombats++;
		
		eventHandler.addEvent(new GameEvent() {
			@Override
			public void runEvent() {
				logger.setText(encounter.getDescription() + "\n" + displayMobs(mobs));
				playerChooseCombatOption(encounter, mobs, room);
			}
		});
	}
	
	private void resolveTrap(Trap trap, Room room) {
		if (trap.isDiscovered() || player.notice() || player.isTrapExpert()) {
			log("You notice something; there's an armed trap in this room!\n" + trap.getDescription());
			trap.setDiscovered(true);
			
			optionsTable.clear();
			
			TextButton avoidButton = new TextButton("Avoid the Trigger", TextDungeon.skin);
			avoidButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (player.isAgile()) {
						log("You manage to avoid the trap's trigger. For now...");
					} else {
						log("You inadvertently trigger the trap!\n" + trap.triggerTrap(player));
					}
					displayRoom(room);
				}
			});
			TextButton retreatButton = new TextButton("Previous Room", TextDungeon.skin);
			retreatButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					displayRoom(previousRoom);
				}
			});
			
			optionsTable.add(avoidButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			optionsTable.add(retreatButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			
			if (player.getInventory().containsKey("Lockpicks")) {
				TextButton disarmButton = new TextButton("Disarm", TextDungeon.skin);
				disarmButton.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						log("You set to work with your lockpicks, trying to disarm the trap...");
						if (generateRandom(1, 100) < player.getCurrentAgility() + GameScreen.LOCKPICK_BONUS) { //they succeed the lockpick check
							log("...success! It is of no further threat.");
							trap.disarmTrap();
						} else {
							log("You struggle with the trap and trigger it in the process!\n" + trap.triggerTrap(player));
							trap.disarmTrap();
						}
						player.removeFromInventory("Lockpicks");
						displayRoom(room);
					}
				});
				
				optionsTable.add(disarmButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			}
			
		} else {
			log("As you enter the room, you set off a trap!");
			log(trap.triggerTrap(player));
			trap.setDiscovered(true);
			room.setTrapTriggered(true);
			displayRoom(room);
		}
		updateCharacterStatsTable();
	}
	
	private void displayLocationChoices(Room room) {
		if (playerIsFleeing)
			log(FLEEING + "\n\nThere are " + room.getAdjacentRooms().size() + " ways out of this location...");
		else
			log(WHERE_NOW + "\n\nThere are " + room.getAdjacentRooms().size() + " ways out of this location...");
		
		optionsTable.clear();
		int counter = 0;
		for (Room r : room.getAdjacentRooms()) {
			String name = "";
			if (r.isExplored() || r.isScouted())
				name = r.getName();
			else
				name = UNKNOWN;
			
			TextButton button = new TextButton(name, TextDungeon.skin);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					checkPlayerStatus();
					
					if (!room.isChalked())
						player.takeFatigue(roomFatigue);
					
					room.setTrapTriggered(false);
					displayRoom(r);
				}
			});
			
			optionsTable.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
		
		if (room.hasPortal()) {
			TextButton portalButton = new TextButton("Enter Portal", TextDungeon.skin);
			portalButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					log("You step into the whirling portal...");
					room.setTrapTriggered(false);
					displayRoom(currentFloor.getFloorRooms().get(generateRandom(0, currentFloor.getFloorRooms().size()-1)));
				}
			});
			
			optionsTable.add(portalButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
		
		if (room.isBossRoom() && !playerIsFleeing) {
			TextButton nextFloorButton = new TextButton(NEXT_FLOOR, TextDungeon.skin);
			nextFloorButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					checkPlayerStatus();
					
					room.setTrapTriggered(false);
					updateCharacterStatsTable();

					Gdx.input.setInputProcessor(stage);
					//TODO implement Fade to show floor number and name between floors
					floorCounter++;
					currentFloor = dungeon.getDungeonFloors().get(floorCounter-1);
					displayRoom(currentFloor.getFloorRooms().get(0));
				}
			});
			
			optionsTable.add(nextFloorButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
		
		if (!playerIsFleeing) {
			TextButton stayButton = new TextButton(STAY, TextDungeon.skin);
			stayButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					room.setTrapTriggered(false);
					displayRoom(room);
				}
			});
			
			optionsTable.add(stayButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		} else {
			playerIsFleeing = false;
		}
		
	}
	
	private void displayRoom(Room room) {
		if (currentRoom != room) {
			room.enterRoom();
		}
		
		previousRoom = currentRoom;
		currentRoom = room;
		
		floorLabel.setText(FLOOR + " " + floorCounter);
		roomLabel.setText(room.getName());
		
		optionsTable.clear();
		
		if (room.hasCombat()) {
			CombatEncounter encounter = currentRoom.getCombatEncounter();
			List<Mob> mobs = encounter.getCombat(currentFloor.getCombatXPMax() + player.getCurse());
			
			runCombat(encounter, mobs, room);
			
		} else if (room.isBossRoom() && !room.isCompleted()) {
			CombatEncounter encounter = currentFloor.getBossEncounter();
			List<Mob> mobs = encounter.getCombat(10); //10 to always ensure the cap is high enough for Bosses and any minions
			
			runCombat(encounter, mobs, room);
			//if the room has a trap, the trap is armed, and you're entering the room for the first time now
			//this prevents the trap from triggering every time the room is displayed until it's disarmed
		} else if (room.hasTrap() && room.getTrap().isArmed() && !room.trapTriggered()) {
			resolveTrap(room.getTrap(), room);
			room.setTrapTriggered(true);
		} else {
			room.getActions().clear(); //clear the list of room actions
			room.initRoomActions(player); //re-initialize the list of room actions in case something has changed

			if (!room.isExplored()) { //this is your first time in the room
				if (room.hasSecret())
					log(room.getSecretHint());
				
				log(room.getDescription());
				if (player.isScout()) {
					List<Room> tempList = new ArrayList<>(room.getAdjacentRooms());
					int rand = GameScreen.generateRandom(0, tempList.size()-1);
					tempList.get(rand).setScouted(true);
				}
			} else if (!room.isCompleted()) {
				log(RETURN + room.getName() + "\n" + room.getDescription());
			} else { //the room is completed
				log(RETURN + room.getName() + "\n" + room.getCompletedDescription());
			}
			
			int counter = 0;
			if (!room.isCompleted()) { //if the room isn't completed, even if you've been here before
				counter = 0;
				for (RoomAction ra : room.getActions()) { //then get the list of actions and resolve them
					
					TextButton button = new TextButton(ra.getName(), TextDungeon.skin);
					button.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							log(ra.resolveAction(player));
							if (player.getCurrentHitPoints() <= 0) { //if they die, game over and exit the game
								eventHandler.addEvent(new GameEvent() {
									@Override
									public void runEvent() {
										gameOver();
									}
								});
								return;
							}
							Gdx.input.setInputProcessor(new InputAdapter() {
								@Override
								public boolean touchUp(int x, int y, int pointer, int button) {
									Gdx.input.setInputProcessor(stage);
									if (room.isCompleted()) {
										displayLocationChoices(room);
									} else {
										displayRoom(room);
									}
									return true;
								}
							});
						}
					});
					
					optionsTable.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD);
					counter++;
					if (counter % 3 == 0)
						optionsTable.row();
				}
			} 
			
			addDefaultRoomActions(room, counter);
			
			updateCharacterStatsTable();
			
			room.setExplored(true);
		}
	}

	private void displayChestContents(Room room, Chest chest) {
		GameScreen.optionsTable.clear();
		int counter = 0;
		
		TextButton nevermindButton = new TextButton(NEVERMIND, TextDungeon.skin);
		nevermindButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				displayRoom(room);
			}
		});
		
		optionsTable.add(nevermindButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		counter++;
		
		TextButton takeAllButton = new TextButton("Take All", TextDungeon.skin);
		takeAllButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String str = "";
				for (Item i : chest.getContents()) {
					str += "\n" + player.addToInventory(i);
				}
				chest.getContents().clear();
				log(str);
				displayRoom(room);
			}
		});
		
		optionsTable.add(takeAllButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		counter++;
		
		for (Item item : chest.getContents()) {
			TextButton button = new TextButton(item.getName(), TextDungeon.skin);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					log(player.addToInventory(item));
					chest.getContents().remove(item);
					Gdx.input.setInputProcessor(new InputAdapter() {
						public boolean touchUp(int x, int y, int pointer, int button) {
							Gdx.input.setInputProcessor(stage);
							displayRoomItems(room);
							return true;
						}
					});
				}
			});
			
			optionsTable.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
	}
	
	private void displayRoomItems(Room room) {
		if (room.getItems().size() == 0)
			log(NO_ITEMS);
		else
			log(FIND_ITEMS);
		
		optionsTable.clear();
		int counter = 0;
		
		TextButton nevermindButton = new TextButton(NEVERMIND, TextDungeon.skin);
		nevermindButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				displayRoom(room);
			}
		});
		
		optionsTable.add(nevermindButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		counter++;
		
		for (Item item : room.getItems()) {
			TextButton button = new TextButton(item.getName(), TextDungeon.skin);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					log(player.addToInventory(item));
					room.removeDroppedItem(item);
					Gdx.input.setInputProcessor(new InputAdapter() {
						public boolean touchUp(int x, int y, int pointer, int button) {
							Gdx.input.setInputProcessor(stage);
							displayRoomItems(room);
							return true;
						}
					});
				}
			});
			
			optionsTable.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
		
		if (room.hasChest()) {
			TextButton chestButton = new TextButton("Chest", TextDungeon.skin);
			chestButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (room.getChest().getType() == Chest.Type.MIMIC && room.getChest().isMimicAlive()) {
						CombatEncounter encounter = new MimicEncounter();
						List<Mob> mobs = encounter.getCombat(3); //3 to always ensure the cap is high enough to allow the Mimic
						
						runCombat(encounter, mobs, room);
					}
					
					//if the chest opens and they survived the mimic combat and the mimic is dead
					if (room.getChest().openChest(player) && player.getCurrentHitPoints() > 0 && !room.getChest().isMimicAlive()) {
						displayChestContents(room, room.getChest());
					}
				}
			});
			
			optionsTable.add(chestButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
	}
	
	private void addDefaultRoomActions(Room room, int counter) {
		
		if (room.getAdjacentRooms().size() > 0) { //this should only be false for very specific rooms
			TextButton moveOnButton = new TextButton(MOVE_ON, TextDungeon.skin);
			moveOnButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					displayLocationChoices(room);
				}
			});
			
			optionsTable.add(moveOnButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
		
		if (!room.isSearched()) { //only add a search button if the room hasn't already been searched
			TextButton searchButton = new TextButton(SEARCH, TextDungeon.skin);
			searchButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					eventHandler.addEvent(new GameEvent() {
						@Override
						public void runEvent() {
							logger.setText(room.searchRoom(player));
							checkPlayerStatus();
							player.takeFatigue(roomFatigue); //searching incurs fatigue
						}
					});
					
					eventHandler.addEvent(new GameEvent() {
						@Override
						public void runEvent() {
							displayRoom(room);
						}
					});
				}
			});
			
			optionsTable.add(searchButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 3 == 0)
				optionsTable.row();
		}
		
		if (room.getItems().size() > 0 || room.hasChest()) {
			TextButton itemsButton = new TextButton(GET_ITEMS +  "(" + room.getItems().size() + ")", TextDungeon.skin);
			itemsButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					displayRoomItems(room);
				}
			});
			
			optionsTable.add(itemsButton).width(BUTTON_WIDTH).pad(BUTTON_PAD); //Don't make a new empty row if it's the last one
		}
	}
	
	private void initRightSideTable() {
		logger = new Label("", TextDungeon.skin);
		logger.setWrap(true);
		optionsTable = new Table();
		
		rightSideTable.add(logger).width(stage.getWidth() * 2 / 3).height(200f).top().left();
		rightSideTable.row();
		rightSideTable.add(optionsTable).expandY().bottom();
	}

	private void updateCharacterStatsTable() {
		if (player.getArchetype() != null) { // only do this if the player has been initialized
			lblName.clear();
			lblName.setText(player.getName());
			lblAdventurer.clear();
			lblAdventurer.setText(player.getKin() + " " + player.getAdventurerType());
			lblStats.clear();
			lblStats.setText(player.displayStatistics());
		}
	}
	
	private void initCharDetailsTable() {
		charDetailsTable = new Table();
		charDetailsLabel = new Label("", TextDungeon.skin);
		charDetailsLabel.setWrap(true);
		ScrollPane charScroller = new ScrollPane(charDetailsLabel);
		
		TextButton exitDetailsButton = new TextButton("Close", TextDungeon.skin);
		exitDetailsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				charDetailsTable.setVisible(false);
			}
		});
		
		charDetailsTable.add(charScroller).width(stage.getWidth() * 2 / 3);
		charDetailsTable.row();
		charDetailsTable.add(exitDetailsButton).center();
		charDetailsTable.setFillParent(true);
		charDetailsTable.center();
		padTable(charDetailsTable, 6);
		charDetailsTable.setBackground(blackBackground);
	}

	private void initCharacterStatsTable() {
		lblName = new Label(player.getName(), TextDungeon.skin);
		lblAdventurer = new Label(player.getKin() + " " + player.getAdventurerType(), TextDungeon.skin);
		lblStats = new Label("", TextDungeon.skin);
		
		TextButton detailsButton = new TextButton("Details", TextDungeon.skin);
		detailsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				charDetailsLabel.setText(player.displayStatisticsDetails());
				charDetailsTable.setVisible(true);
			}
		});

		characterStatsTable.add(lblName).top().left();
		characterStatsTable.row();
		characterStatsTable.add(lblAdventurer).top().left();
		characterStatsTable.row();
		characterStatsTable.add(lblStats).top().left();
		characterStatsTable.row();
		characterStatsTable.add(detailsButton).left();
	}

	private void initInventoryItems() {
		leftInventory.clear();
		leftInventory.add(new Label("Inventory", TextDungeon.skin)).top().left();
		leftInventory.row();
		leftInventory.add(new Label("Coins: " + player.getCoins(), TextDungeon.skin)).top().left();
				
		TextButton keyButton = new TextButton(KEY_RING, TextDungeon.skin);
		keyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				inventoryDescription.clear();
				if (player.getKeyRing().size() == 0)
					inventoryDescription.setText(NO_KEYS);
				else {
					StringBuilder str = new StringBuilder();
					for (String s : player.getKeyRing()) {
						str.append("\n" + s);
					}
					inventoryDescription.setText(str.toString());
				}
			}
		});
		leftInventory.row();
		leftInventory.add(keyButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		
		for (String itemName : player.getInventory().keySet()) {
			TextButton itemButton = new TextButton(itemName, TextDungeon.skin);
			itemButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					selectedItem = player.getInventory().get(itemName);
					inventoryDescription.clear();
					inventoryDescription.setText(selectedItem.getName() + " x" + selectedItem.getCount() + "\n\n"
							+ selectedItem.getStatistics());

					if (selectedItem.isEquippable()) {
						if (selectedItem.isEquipped()) { // it's equipped, so the action is Unequip
							useButton.setText("Unequip");
						} else { // it's not equipped, so the action is Equip
							useButton.setText("Equip");
						}
					} else { // it's not equippable, so it's a consumable
						useButton.setText("Use");
					}

				}
			});
			leftInventory.row();
			leftInventory.add(itemButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		}
		
		updateEquippedItemsLabel();
	}

	private void updateEquippedItemsLabel() {
		equippedItemsLabel.setText(player.displayEquippedItems());
	}

	private void initInventoryMenu() {
		inventory = new Table();
		leftInventory = new Table();
		
		ScrollPane inventoryScroller = new ScrollPane(leftInventory);

		inventoryDescription = new Label("", TextDungeon.skin);
		inventoryDescription.setWrap(true);
		
		equippedItemsLabel = new Label("", TextDungeon.skin);
		equippedItemsLabel.setWrap(true);
		
		runeChoiceTable = new Table();
		runeChoiceTable.setBackground(blackBackground);
		runeChoiceTable.setFillParent(true);
		runeChoiceTable.center();
		padTable(runeChoiceTable, 6);

		Table inventoryButtons = new Table(); // drop, use/equip, exit
		TextButton dropButton = new TextButton("Drop", TextDungeon.skin);
		dropButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (selectedItem != null) {
					if (selectedItem.isEquipped() && selectedItem.getCount() == 1) { // if it's equipped, don't drop it
						inventoryDescription.clear();
						inventoryDescription.setText("You can't drop an equipped item!");
					} else {
						inventoryDescription.clear();
						inventoryDescription.setText(selectedItem.getName() + " was dropped.");
						player.removeFromInventory(selectedItem);
						currentRoom.addDroppedItem(selectedItem);
						initInventoryItems();
					}
				} else {
					inventoryDescription.clear();
					inventoryDescription.setText("No item selected!");
				}
			}
		});
		useButton = new TextButton("Use", TextDungeon.skin);
		useButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (selectedItem != null) {
					if (selectedItem.isEquippable()) {
						if (selectedItem.isEquipped()) { // if it's already equipped
							inventoryDescription.setText(player.unequipItem(selectedItem));
							useButton.setText("Equip");
							updateEquippedItemsLabel();
						} else { // it needs to be equipped
							inventoryDescription.setText(player.equipItem(selectedItem));
							if (selectedItem.isEquipped())
								useButton.setText("Unequip");
							updateEquippedItemsLabel();
						}
					} else if (selectedItem.getType() == Item.Type.RUNE) {
						if (player.inventoryContains(Item.Type.WEAPON)) {
							initRuneTable();
							stage.setKeyboardFocus(runeChoiceTable);
							stage.setScrollFocus(runeChoiceTable);
							runeChoiceTable.setVisible(true);
						} else {
							inventoryDescription.setText("You don't have any weapons to apply the rune to!");
						}
					} else { // item is a consumable
						inventoryDescription.clear();
						inventoryDescription.setText(selectedItem.useItem(player));
					}
					initInventoryItems();
				} else {
					inventoryDescription.clear();
					inventoryDescription.setText("No item selected!");
				}
			}
		});
		TextButton exitButton = new TextButton("Exit", TextDungeon.skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				updateCharacterStatsTable();
				inventory.setVisible(false);
				stage.unfocusAll();
				inventoryDescription.clear();
			}
		});

		inventoryButtons.add(dropButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		inventoryButtons.add(useButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		inventoryButtons.add(exitButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);

		inventory.add(inventoryScroller).expand().top().left();
		inventory.add(inventoryDescription).expand().prefWidth(stage.getWidth() /3).top().left();
		inventory.add(equippedItemsLabel).expand().prefWidth(stage.getWidth() /3).top().left();
		inventory.row();
		inventory.add(inventoryButtons).colspan(3).center();
		inventory.setFillParent(true);
		inventory.center();
		inventory.pad(20f);

		inventory.setBackground(blackBackground);
	}
	
	private void initShopTable() {
		shopTable = new Table();
		shopTopRowButtons = new Table();
		playerSellTable = new Table();
		shopInventoryTable = new Table();
		shopDescriptionTable = new Table();
		shopScreenStack = new Stack();
		shopScreenStack.add(shopInventoryTable);
		shopScreenStack.add(playerSellTable);
		
		playerSellTable.setVisible(false);
		
		shopTable.add(shopTopRowButtons).colspan(2);
		shopTable.row();
		shopTable.add(shopScreenStack);
		shopTable.add(shopDescriptionTable);
		shopTable.setFillParent(true);
		shopTable.center();
		shopTable.setBackground(blackBackground);
	}
	
	private void initRuneTable() {
		runeChoiceTable.clear();
		
		Label runeChoiceLabel = new Label("Select a Weapon to apply " + selectedItem.getName() + "to.", TextDungeon.skin);
		TextButton exitButton = new TextButton("Nevermind", TextDungeon.skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				runeChoiceTable.setVisible(false);
				runeChoiceTable.clear();
				stage.unfocus(runeChoiceTable);
			}
		});
		
		runeChoiceTable.add(runeChoiceLabel).colspan(3);
		runeChoiceTable.row();
		runeChoiceTable.add(exitButton).colspan(3);
		runeChoiceTable.row();
		
		int counter = 0;
		for (Item item : player.getInventory().values()) {
			//if the object is a weapon and it doesn't already have a rune
			if (item.getType() == Item.Type.WEAPON) {
				TextButton weaponButton = new TextButton(item.getName(), TextDungeon.skin);
				weaponButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						((Weapon) item).setRune((Rune)selectedItem);
						player.removeFromInventory(selectedItem);
						selectedItem = null;
						runeChoiceTable.setVisible(false);
						runeChoiceTable.clear();
						stage.unfocus(runeChoiceTable);
						initInventoryItems();
					}
				});
				
				runeChoiceTable.add(weaponButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
				counter++;
				if (counter % 3 == 0)
					runeChoiceTable.row();
			}
		}
		
	}

	private void initTopRow() {
		initInventoryMenu();
		
		TextButton exitMapButton = new TextButton("Close", TextDungeon.skin);
		exitMapButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				map.setVisible(false);
			}
		});
		
		map = new Table();
		mapLabel = new Label("", TextDungeon.skin);
		mapLabel.setWrap(true);
		mapScroller = new ScrollPane(mapLabel);
		map.add(mapScroller).width(stage.getWidth() * 2 / 3);
		map.row();
		map.add(exitMapButton).center();
		map.setFillParent(true);
		map.center();
		padTable(map, 6);
		map.setBackground(blackBackground);

		menu = new Table();
		
		TextButton exitChoicesButton = new TextButton("Close", TextDungeon.skin);
		exitChoicesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				choicesMenu.setVisible(false);
			}
		});
		
		choicesMenu = new Table();
		choicesLabel = new Label("", TextDungeon.skin);
		choicesLabel.setWrap(true);
		ScrollPane choicesScroller = new ScrollPane(choicesLabel);
		choicesMenu.add(choicesScroller).width(stage.getWidth() * 2 / 3);
		choicesMenu.row();
		choicesMenu.add(exitChoicesButton).center();
		choicesMenu.setFillParent(true);
		choicesMenu.center();
		padTable(choicesMenu, 6);
		choicesMenu.setBackground(blackBackground);

		inventoryButton = new TextButton("Inventory", TextDungeon.skin);
		inventoryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				leftInventory.clearChildren();
				initInventoryItems();
				inventory.setVisible(true);
				stage.setKeyboardFocus(inventory);
				stage.setScrollFocus(inventory);
			}
		});
		mapButton = new TextButton("Map", TextDungeon.skin);
		mapButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				map.setVisible(true);
				mapLabel.clear();
				mapLabel.setText("DUNGEON MAP:\n" + dungeon.getDungeonFloors().get(floorCounter - 1).getMap());
			}
		});
		menuButton = new TextButton("Menu", TextDungeon.skin);
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO create and show menu. Return, Save/Exit, Quit, and Settings
				// functionality
			}
		});
		choicesButton = new TextButton("Combat Choices", TextDungeon.skin);
		choicesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				choicesMenu.setVisible(true);
				choicesLabel.clear();
				String str = "Combat Choices:";
				for (Choice c : player.getCombatChoices()) {
					str += "\n   " + c.getName() + ": " + c.getDescription(player);
				}
				choicesLabel.setText(str);
			}
		});
		
		floorLabel = new Label("", TextDungeon.skin);
		roomLabel = new Label("", TextDungeon.skin);

		topRowTable.add(inventoryButton).pad(20);
		topRowTable.add(mapButton).pad(20);
		topRowTable.add(menuButton).pad(20);
		topRowTable.add(choicesButton).pad(20);
		topRowTable.add(floorLabel).pad(20).expandX();
		topRowTable.add(roomLabel).pad(20).width(100f);
	}

	private void updatePerkChoiceTable() {
		perkChoiceTable.clear();
		Label perkChoiceLabel = new Label("Choose a Perk", TextDungeon.skin);
		perkChoiceDescription.setText("");
		perkChoiceTable.add(perkChoiceLabel).center().colspan(2);
		perkChoiceTable.row();
		
		int counter = 0;
		for (Perk perk : player.getPerkChoices()) {
			TextButton button = new TextButton(perk.getName(), TextDungeon.skin);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					perkChoiceDescription.setText(perk.getDescription());
					perkChoice = perk;
				}
			});
			
			perkChoiceTable.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 2 == 0 && perk != player.getPerkChoices().get(player.getPerkChoices().size()-1))
				perkChoiceTable.row();
		}
		
		//start a new row only if it didn't at the end of the list of perks
		if (counter % 2 != 0)
			perkChoiceTable.row();
		
		perkChoiceTable.add(perkChoiceDescription).colspan(2);
	}
	
	private void showLevelUpTable() {
		levelUpLabel.setText("LEVEL UP! " + player.getName() + " has reached level " + (player.getLevel()) + "!");
		statChoice = null;
		perkChoice = null;
		levelUpTable.setVisible(true);
	}
	
	private void initLevelUpTable() {
		levelUpTable = new Table();
		levelUpLabel = new Label("", TextDungeon.skin);
		Table statChoiceTable = new Table();
		Label statChoiceLabel = new Label("Increase A Statistic", TextDungeon.skin);
		Label statChoiceDescription = new Label("", TextDungeon.skin);
		statChoiceDescription.setWrap(true);
		perkChoiceDescription = new Label("", TextDungeon.skin);
		perkChoiceDescription.setWrap(true);
		
		Gdx.input.setInputProcessor(stage);

		TextButton accuracyButton = new TextButton("Accuracy", TextDungeon.skin);
		accuracyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				statChoice = Adventurer.StatChoice.ACCURACY;
				statChoiceDescription.setText("ACCURACY\n" + player.getAdventurer().getTempAccuracyIncrease(player));
			}
		});
		TextButton agilityButton = new TextButton("Agility", TextDungeon.skin);
		agilityButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				statChoice = Adventurer.StatChoice.AGILITY;
				statChoiceDescription.setText("AGILITY\n" + player.getAdventurer().getTempAgilityIncrease(player));
			}
		});
		TextButton mindButton = new TextButton("Mind", TextDungeon.skin);
		mindButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				statChoice = Adventurer.StatChoice.MIND;
				statChoiceDescription.setText("MIND\n" + player.getAdventurer().getTempMindIncrease(player));
			}
		});
		TextButton strengthButton = new TextButton("Strength", TextDungeon.skin);
		strengthButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				statChoice = Adventurer.StatChoice.STRENGTH;
				statChoiceDescription.setText("STRENGTH\n" + player.getAdventurer().getTempStrengthIncrease(player));
			}
		});
		TextButton toughnessButton = new TextButton("Toughness", TextDungeon.skin);
		toughnessButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				statChoice = Adventurer.StatChoice.TOUGHNESS;
				statChoiceDescription.setText("TOUGHNESS\n" + player.getAdventurer().getTempToughnessIncrease(player));
			}
		});

		perkChoiceTable = new Table();
		updatePerkChoiceTable();
		
		TextButton confirmButton = new TextButton("Confirm", TextDungeon.skin);
		confirmButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (statChoice != null && perkChoice != null) { //do nothing unless statChoice and perkChoice are both not null
					player.getAdventurer().applyStatBonus(player, statChoice);
					perkChoice.applyBenefits(player);
					player.addChosenPerk(perkChoice);
					if (!perkChoice.getName().equals("Stat Boost")) { // if the perk is not a stat boost
						player.getPerkChoices().remove(perkChoice); //remove it from perk choices
					}
					levelUpTable.setVisible(false);
					if (!eventHandler.isEventQueueEmpty() && !(perkChoice instanceof JackOfAllTrades)) {
						eventHandler.setInputToEventHandler();
					}
				}
				statChoice = null;
				perkChoice = null;
			}
		});
		
		statChoiceTable.add(statChoiceLabel).center().colspan(2);
		statChoiceTable.row();
		statChoiceTable.add(accuracyButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		statChoiceTable.add(agilityButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		statChoiceTable.row();
		statChoiceTable.add(mindButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		statChoiceTable.add(strengthButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		statChoiceTable.row();
		statChoiceTable.add(toughnessButton).center().colspan(2).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		statChoiceTable.row();
		statChoiceTable.add(statChoiceDescription).width(stage.getWidth() * 2 / 9 ).colspan(2);

		levelUpTable.add(levelUpLabel).colspan(2);
		levelUpTable.row();
		levelUpTable.add(statChoiceTable).expand().top().left();
		levelUpTable.add(perkChoiceTable).expand().top().left();
		levelUpTable.row();
		levelUpTable.add(confirmButton).colspan(2);
		levelUpTable.setFillParent(true);
		levelUpTable.center();
		padTable(levelUpTable, 6);

		levelUpTable.setBackground(blackBackground);
	}
	
	private void initGameOverTable() {
		gameOverTable = new Table();
		
		gameOverTable.setFillParent(true);
		gameOverTable.center();
		padTable(gameOverTable, 3);
		gameOverTable.setBackground(blackBackground);
		
		Label gameOverLabel = new Label("GAME OVER!", new LabelStyle(new BitmapFont(Gdx.files.internal("Skins/NovemberBitmapFont96.fnt")), Color.WHITE));
		
		TextButton mainMenu = new TextButton("Main Menu", TextDungeon.skin);
		mainMenu.addListener(new ClickListener () {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameOverTable.setVisible(false);
				resetGameScreen();
				game.setScreen(TextDungeon.ScreenKey.MAINMENU);
			}
		});
		TextButton sameCharacter = new TextButton("Same Character", TextDungeon.skin);
		sameCharacter.addListener(new ClickListener () {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				CharacterSelectScreen.KinChoice kinChoice = player.getKinChoice();
				CharacterSelectScreen.AdventurerChoice adventurerChoice = player.getAdventurerChoice();
				CharacterSelectScreen.ArchetypeChoice archetypeChoice = player.getArchetypeChoice();
				
				gameOverTable.setVisible(false);
				resetGameScreen();
				player.setKin(kinChoice);
				player.setAdventurer(adventurerChoice);
				player.setArchetype(archetypeChoice);
				game.setScreen(ScreenKey.GAME);
			}
		});
		TextButton characterSelect = new TextButton("New Character", TextDungeon.skin);
		characterSelect.addListener(new ClickListener () {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameOverTable.setVisible(false);
				resetGameScreen();
				game.setScreen(TextDungeon.ScreenKey.CHARACTER);
			}
		});
		
		gameOverTable.add(gameOverLabel).center();
		gameOverTable.row();
		gameOverTable.add(mainMenu).center().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		gameOverTable.row();
		gameOverTable.add(sameCharacter).center().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		gameOverTable.row();
		gameOverTable.add(characterSelect).center().width(BUTTON_WIDTH).pad(BUTTON_PAD);
	}
	
	/**
	 * Pads the parameter table by a fraction of the stage's height and width,
	 * designated by div. For example, 6 would pad the table on all sides by 
	 * 1/6 of the stage's length or width, as appropriate.
	 * @param table the table to pad
	 * @param div the fraction of the stage's height and width to pad
	 */
	private void padTable(Table table, int div) {
		table.padLeft(stage.getWidth()/div).padRight(stage.getWidth()/div);
		table.padTop(stage.getHeight()/div).padBottom(stage.getHeight()/div);
	}

	private void resetGameScreen() {
		player.reset();
		dungeon = new Dungeon();
		isBlacksmithFreed = false;
		wraithIsSlain = false;
		isEscortingWizard = false;
		isWizardDead = false;
		escortCombats = 0;
		isStolenLootReturned = false;
		isForbiddenTomeRead = false;
		isCursedPrisonStudied = false;
		eventHandler.clearEvents();
		
		floorCounter = 1;
		currentFloor = dungeon.getDungeonFloors().get(0);
		currentRoom = null;
		previousRoom = null;
		combatIsRunning = false;
		roundCounter = 0;
		playerIsFleeing = false;
		playerTarget = null;
		statChoice = null;
		
		displayRoom(currentFloor.getFloorRooms().get(0));
		
		Gdx.input.setInputProcessor(touchToBegin);
	}
	
	private void checkPlayerStatus() {
		String str = player.checkRoundCounters();
		updateCharacterStatsTable();
		if (!str.isBlank())
			logQueue(str);
		if (player.getCurrentHitPoints() < 1) {
			eventHandler.addEvent(new GameEvent() {
				@Override
				public void runEvent() {
					gameOver();
				}
			});
		}
			
	}

	public static int generateRandom(int min, int max) {
		return (int)Math.floor(Math.random()*(max-min+1)+min);
	}

	public static int getRoundCounter() {
		return roundCounter;
	}
	
	public static Mob getPlayerTarget() {
		return playerTarget;
	}
	
	public static Room getCurrentRoom() {
		return currentRoom;
	}
	
	/**
	 * Puts the string into an event and hands it to the eventHandler to put in the
	 * event queue.
	 * @param str the string to be printed.
	 */
	public static void logQueue(String str) {
		eventHandler.addEvent(new GameEvent() {
			@Override
			public void runEvent() {
				logger.setText(str);
			}
		});
	}
	
	/**
	 * If the eventHandler's Queue is empty, prints the string to the UI. Otherwise, 
	 * inserts the string into an event and gives it to the eventHandler to be logged.
	 * @param str the string to be logged on the UI
	 */
	public static void log(String str) {
		if (!str.isBlank()) {
			if (!eventHandler.isEventQueueEmpty()) {
				
				eventHandler.addEvent(new GameEvent() {
					@Override
					public void runEvent() {
						logger.setText(str);
					}
				});
				
			} else {
				logger.setText(str);
			}
		}
	}
	
	/**
	 * Sets the string as the Logger's current text on the UI
	 * @param str the string to display on the UI
	 */
	public static void setLogger(String str) {
		if (!str.isBlank())
			logger.setText(str);
	}
	
}
