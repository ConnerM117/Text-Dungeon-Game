package com.textdungeon.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.textdungeon.game.TextDungeon.ScreenKey;

import items.armor.*;
import items.consumables.*;
import items.gear.*;
import items.weapons.*;

public class TutorialScreen implements Screen {

	private static final int WEAPON_ATLAS_COLS = 5, WEAPON_ATLAS_ROWS = 6;
	private static final int WEAPON_X_POS = 350, WEAPON_Y_POS = 150, WEAPON_WIDTH = 256, WEAPON_HEIGHT = 256;
	private static final int CONS_ATLAS_COLS = 5, CONS_ATLAS_ROWS = 5;
	private static final int CONS_X_POS = 370, CONS_Y_POS = 150, CONS_WIDTH = 256, CONS_HEIGHT = 256;
	
	private static float BUTTON_PAD = 5f;
	private static float BUTTON_WIDTH = 150f;
	
	final TextDungeon game;

	OrthographicCamera camera;
	Stage stage;
	Table table;
	Table rootColumn;
	Viewport viewport;

	Table display;
	Label displayTitle;
	Label displayDescription;

	ScrollPane scrollPaneCenter;
	ScrollPane scrollPaneRight;
	ScrollPane displayScrollPane;
	Stack centerStack;
	Stack rightStack;

	TextButton basicGameplayButton;
	TextButton characterButton;
	TextButton dungeonButton;
	TextButton equipmentButton;

	Table basicGameplayTable;
	TextButton characterGameplayButton;
	TextButton combatButton;
	TextButton equipmentGameplayButton;
	TextButton equippedItemsButton;
	TextButton explorationButton;
	TextButton inventoryButton;
	TextButton statisticsButton;
	TextButton winButton;

	Table characterTable;
	TextButton adventurerButton;
	TextButton archetypeButton;
	TextButton kinButton;
	TextButton statisticsCharacterButton;

	Table dungeonTable;
	TextButton floorsButton;
	TextButton mobsButton;
	TextButton roomsButton;

	Table equipmentTable;
	TextButton armorButton;
	TextButton consumablesButton;
	TextButton gearButton;
	TextButton weaponsButton;

	Table armorTable;
	Table consumableTable;
	Table gearTable;
	Table weaponTable;

	Map<String, String> armorMap;
	List<Consumable> consumableList;
	Map<String, String> gearMap;
	List<Weapon> weaponList;

	SpriteBatch batch;
	TextureRegion currentImage;
	TextureRegion[] weaponFrames;
	TextureRegion[] consumableFrames;

	enum ImageType {
		NONE, ARMOR, CONSUMABLE, GEAR, RUNE, WEAPON
	}

	ImageType imageType;

	public TutorialScreen(final TextDungeon game) {
		this.game = game;
		imageType = ImageType.NONE;
		batch = new SpriteBatch();

		camera = new OrthographicCamera();

		initTextures();

		initEquipmentLists();

		viewport = new FitViewport(800, 480);
		stage = new Stage(viewport);
		table = new Table();
		table.setFillParent(true);

		display = new Table();
		displayTitle = new Label("", TextDungeon.skin);
		displayDescription = new Label("", TextDungeon.skin);
		displayDescription.setWrap(true);
		// displayDescription.setWidth(Gdx.graphics.getWidth()/5);
		display.add(displayTitle).top().left();
		display.row();
		display.add(displayDescription).prefWidth(250f).top().left();

		stage.addActor(table);

		TextButton backButton = new TextButton("Back", TextDungeon.skin);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(ScreenKey.MAINMENU);
			}
		});
		backButton.setPosition(40, Gdx.graphics.getHeight() - 30);
		stage.addActor(backButton);

		rootColumn = new Table();
		centerStack = new Stack();
		rightStack = new Stack();
		scrollPaneCenter = new ScrollPane(centerStack);
		scrollPaneRight = new ScrollPane(rightStack);
		displayScrollPane = new ScrollPane(display);
		displayScrollPane.setForceScroll(true, true);

		// initialize root tables
		basicGameplayTable = new Table();
		equipmentTable = new Table();

		// initialize basic gameplay table and all children
		initBasicGameplayUI();

		// initialize equipment table and all children
		initEquipmentUI();

		// initialize rootColumn buttons

		basicGameplayButton = new TextButton("Basic Gameplay", TextDungeon.skin);
		basicGameplayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				basicGameplayTable.setVisible(true);
				equipmentTable.setVisible(false);
				imageType = ImageType.NONE;
				currentImage = null;
			}
		});

		equipmentButton = new TextButton("Equipment", TextDungeon.skin);
		equipmentButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				basicGameplayTable.setVisible(false);
				equipmentTable.setVisible(true);
			}
		});

		rootColumn.add(basicGameplayButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		rootColumn.row();
		rootColumn.add(equipmentButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);

		// add stuff to main table
		table.add(rootColumn).width(Gdx.graphics.getWidth() / 5).top().left().pad(10);
		table.add(scrollPaneCenter).width(Gdx.graphics.getWidth() / 5).top().left().pad(10);
		table.add(scrollPaneRight).width(Gdx.graphics.getWidth() / 4).top().left().pad(10);
		table.add(displayScrollPane).expandX().top().left().pad(10);
		table.pad(25);

		scrollPaneCenter.setScrollingDisabled(true, false);
		scrollPaneRight.setScrollingDisabled(true, false);
		// table.setDebug(true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);

		camera.update();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		switch (imageType) {
		case ARMOR:
			break;
		case CONSUMABLE:
			batch.begin();
			batch.draw(currentImage, CONS_X_POS, CONS_Y_POS, CONS_WIDTH, CONS_HEIGHT);
			batch.end();
			break;
		case GEAR:
			break;
		case RUNE:
			break;
		case WEAPON:
			batch.begin();
			batch.draw(currentImage, WEAPON_X_POS, WEAPON_Y_POS, WEAPON_WIDTH, WEAPON_HEIGHT);
			batch.end();
			break;
		default:
			break;
		}
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

	private void setDisplay(String title, String description) {
		displayTitle.clear();
		displayTitle.setText(title);
		displayDescription.clear();
		displayDescription.setText(description);
	}
	
	private void initTextures() {
		Texture weaponTexture = new Texture(Gdx.files.internal("WeaponsAtlas.png"));
		TextureRegion[][] temp = TextureRegion.split(weaponTexture, weaponTexture.getWidth() / WEAPON_ATLAS_COLS,
				weaponTexture.getHeight() / WEAPON_ATLAS_ROWS);
		weaponFrames = new TextureRegion[WEAPON_ATLAS_COLS * WEAPON_ATLAS_ROWS];
		int index = 0;
		for (int i = 0; i < WEAPON_ATLAS_ROWS; i++) {
			for (int j = 0; j < WEAPON_ATLAS_COLS; j++) {
				weaponFrames[index++] = temp[i][j];
			}
		}
		
		Texture consumablesTexture = new Texture(Gdx.files.internal("ConsumablesAtlas.png"));
		temp = TextureRegion.split(consumablesTexture, consumablesTexture.getWidth() / CONS_ATLAS_COLS, 
				consumablesTexture.getHeight() / CONS_ATLAS_ROWS);
		consumableFrames = new TextureRegion[CONS_ATLAS_COLS * CONS_ATLAS_ROWS];
		index = 0;
		for (int i = 0; i < CONS_ATLAS_ROWS; i++) {
			for (int j = 0; j < CONS_ATLAS_COLS; j++) {
				consumableFrames[index++] = temp[i][j];
			}
		}
		
	}

	private void initBasicGameplayUI() {
		characterGameplayButton = new TextButton("Character", TextDungeon.skin);
		characterGameplayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDisplay("Character",
						"When you Enter the Dungeon, you'll create a character. "
								+ "You'll choose a Kin, an Adventurer, and an Archetype. Your choices "
								+ "will impact how you approach the challenges you face in the dungeon. "
								+ "\n\nYou'll also have the opportunity to choose Perks, which grant you "
								+ "special abilities. Your choice of Kin, Adventurer, and Archetype "
								+ "determine the perk choices you have access to."
								+ "\n\nAs you traverse the dungeon, you'll accumulate XP. Accumulate 20 XP, "
								+ "and you level up! When you level up, you get to increase one of your "
								+ "Statistics and choose a new Perk.");
			}
		});
		combatButton = new TextButton("Combat", TextDungeon.skin);
		combatButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDisplay("Combat",
						"The main danger you'll face in the dungeon is monsters "
								+ "and other baddies who want to cut your journey short. Usually with "
								+ "death. Because you're a respectable adventurer, you know how to "
								+ "defend yourself. When combat starts, you'll first be given a "
								+ "chance to act: usually to attack, flee, or use one of your special "
								+ "abilities. Note that using items doesn't take your turn in combat. "
								+ "Once you've chosen and resolved your action, each monster involved "
								+ "will get a turn to act. Some will attack, but most have special "
								+ "abilities that increase their (or their allies') effectiveness, or "
								+ "do other things that will make your life difficult. Combat is "
								+ "deadly and strategic. Knowing when to flee is a valuable skill. "
								+ "Be warned: the longer you spend on a floor, the deadlier your opposition "
								+ "will get. Don't stay for too long!");
			}
		});
		equipmentGameplayButton = new TextButton("Equipment", TextDungeon.skin);
		equipmentGameplayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDisplay("Equipment",
						"You'll come across tons of loot! There are ## different types of equipment: Armor, Consumables, Gear, "
								+ "Glyphs, Runes, and Weapons." + "\nArmor protects you from attacks."
								+ "\nConsumables are used to get special bonuses, like healing. When you use a Consumable, it's gone."
								+ "\nGear is miscellaneous equipment that doesn't fall into the other categories."
								+ "\nGlyphs are magic sigils that can be transferred to Armor to give it special properties. "
								+ "Armor can only have one Glyph at a time."
								+ "\nRunes are magic sigils that can be transferred to Weapons to give them special properties. "
								+ "A weapon can only have one Rune at a time."
								+ "\nWeapons are monster-killing implements.");
			}
		});
		equippedItemsButton = new TextButton("Equipped Items", TextDungeon.skin);
		equippedItemsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDisplay("Equipped Items",
						"Some items require you to equip them before you can make use of them. You can only wear "
								+ "one suit of Armor, one piece of Headgear, and one miscellaneous piece of Gear. You also have "
								+ "two hands, and you can hold Weapons and held Gear (like shields) in one or both hands. "
								+ "\n\nIf you wield two weapons (Dual Wielding), one of them must be Light, and each time you deal damage, damage for each "
								+ "weapon is determined and the higher result is used."
								+ "\n\nIf you wield a one-handed weapon but have your second hand free, you get +5 to Accuracy and +2 Damage. "
								+ "This represents the added power and stabilization of your second hand."
								+ "\n\nWeapons wielded with two hands are exceptionally powerful, but disallow you from having any Gear in your hands.");
			}
		});
		explorationButton = new TextButton("Exploration", TextDungeon.skin);
		explorationButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDisplay("Exploration", "The dungeon is composed of ten floors in a set order. "
						+ "Each floor has ## randomly generated rooms with one boss room. When you enter a room, "
						+ "you'll be given a prompt with a few possible actions you can take. "
						+ "If you get through the room, you get to choose where you go next, "
						+ "but unless you've explored a particular room already, you'll be "
						+ "going forward blind! You'll always know which room is the Boss Room "
						+ "so that you don't go in until you're ready. Bosses are always much tougher and "
						+ "stronger than normal enemies, so make sure you're well equipped before "
						+ "taking them on! After defeating the floor Boss, "
						+ "you'll be able to go down to the next floor. You may want to move on "
						+ "immediately, or you may want to explore the rest of the floor to see what "
						+ "it offers. Resources are often more important than speed.");
			}
		});
		inventoryButton = new TextButton("Inventory", TextDungeon.skin);
		inventoryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDisplay("Inventory", "Each item you obtain is stored "
						+ "in your inventory. Choose carefully what you have with you, because "
						+ "you've only got so much inventory space! If you drop something to make room, you can "
						+ "always come back later to pick it up, but there's always a risk you'll "
						+ "run into something nasty on the way... "
						+ "\n\nPay attention to enemies' abilities and learn which items to use "
						+ "and when. Visit the Shop (there's one on each floor!) often to "
						+ "replenish your stores of oft-used items.");
			}
		});
		statisticsButton = new TextButton("Statistics", TextDungeon.skin);
		statisticsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDisplay("Statistics", "Your character is composed of several Statistics, which are "
						+ "a numerical representation of their physical and mental capabilities. "
						+ "\n\nHit Points are your most valuable resource, and are reduced when you take "
						+ "damage. If it ever hits 0, you die!" + "\n\nArmor reduces incoming damage."
						+ "\n\nDodge increases your chance of dodging an attack entirely. Actions that rely "
						+ "on agility or dexterity, such as sneaking or picking a lock, also rely on your Dodge."
						+ "\n\nToughness increases your base Armor. Actions "
						+ "that test your endurance (like shrugging off the effects of poison) rely on Toughness."
						+ "\n\nMind increases your Magic Damage. Actions that rely on your awareness or intellect "
						+ "(like searching for traps or finding hidden monsters) rely on your Mind."
						+ "\n\nStrength increases your Damage and your Max Inventory. Actions that rely on raw "
						+ "strength (like lifting a heavy rock) rely on your Strength."
						+ "\n\nAccuracy is your likelihood to hit whenever you attack."
						+ "\n\nPiercing Damage ignores armor whenever you deal damage."
						+ "\n\nCrit Rate is the likelihood that you'll score a critical hit on an attack, and Crit Damage "
						+ "is the additional damage you'll do, expressed as a percentage of your normal damage."
						+ "\n\nWound Rate is the likelihood that you'll wound your foe and cause them to start Bleeding when "
						+ "you attack them. Wound Damage is the damage a Bleeding foe takes from its wounds each turn."
						+ "\n\nStamina can be spent to use special abiliites.");
			}
		});
		winButton = new TextButton("Winning", TextDungeon.skin);
		winButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDisplay("Winning the Game",
						"To win, you'll need to successfully traverse all ten floors of the dungeon "
								+ "and defeat the boss on the final floor. Doing so will require you to make use of everything "
								+ "available to you. You'll need to make judicious use of items you find, learn about your abilities and "
								+ "how to use them, learn how to navigate the dungeon and its unique rooms, "
								+ "and make tough decisions in and out of combat. This is not intended to be easy. "
								+ "Expect to fail, but learn from your failures and push onward. The dungeon awaits...");
			}
		});

		basicGameplayTable.add(characterGameplayButton).left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		basicGameplayTable.row();
		basicGameplayTable.add(combatButton).left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		basicGameplayTable.row();
		basicGameplayTable.add(equippedItemsButton).left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		basicGameplayTable.row();
		basicGameplayTable.add(equipmentGameplayButton).left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		basicGameplayTable.row();
		basicGameplayTable.add(explorationButton).left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		basicGameplayTable.row();
		basicGameplayTable.add(inventoryButton).left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		basicGameplayTable.row();
		basicGameplayTable.add(statisticsButton).left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		basicGameplayTable.row();
		basicGameplayTable.add(winButton).left().width(BUTTON_WIDTH).pad(BUTTON_PAD);

		centerStack.add(basicGameplayTable);
		basicGameplayTable.setVisible(false);
	}

	private void initEquipmentUI() {
		armorTable = new Table();
		for (String s : armorMap.keySet()) {
			TextButton textButton = new TextButton(s, TextDungeon.skin);
			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setDisplay(s, armorMap.get(s));
				}
			});
			armorTable.add(textButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
			armorTable.row();
		}
		armorTable.setVisible(false);
		rightStack.add(armorTable);

		consumableTable = new Table();
		for (Consumable c : consumableList) {
			TextButton textButton = new TextButton(c.getName(), TextDungeon.skin);
			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					imageType = ImageType.CONSUMABLE;
					setDisplay(c.getName(), c.getDescription());
					currentImage = consumableFrames[c.getAtlasIndex()];
				}
			});
			consumableTable.add(textButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
			consumableTable.row();
		}
		consumableTable.setVisible(false);
		rightStack.add(consumableTable);

		gearTable = new Table();
		for (String s : gearMap.keySet()) {
			TextButton textButton = new TextButton(s, TextDungeon.skin);
			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setDisplay(s, gearMap.get(s));
				}
			});
			gearTable.add(textButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
			gearTable.row();
		}
		gearTable.setVisible(false);
		rightStack.add(gearTable);

		weaponTable = new Table();
		for (Weapon weapon : weaponList) {
			TextButton textButton = new TextButton(weapon.getName(), TextDungeon.skin);
			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					imageType = ImageType.WEAPON;
					setDisplay(weapon.getName(), weapon.getStatistics());
					currentImage = weaponFrames[weapon.getAtlasIndex()];
				}
			});
			weaponTable.add(textButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
			weaponTable.row();
		}
		weaponTable.setVisible(false);
		rightStack.add(weaponTable);

		equipmentTable = new Table();
		armorButton = new TextButton("Armor", TextDungeon.skin);
		armorButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				armorTable.setVisible(true);
				consumableTable.setVisible(false);
				gearTable.setVisible(false);
				weaponTable.setVisible(false);
			}
		});
		consumablesButton = new TextButton("Consumables", TextDungeon.skin);
		consumablesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				armorTable.setVisible(false);
				consumableTable.setVisible(true);
				gearTable.setVisible(false);
				weaponTable.setVisible(false);
			}
		});
		gearButton = new TextButton("Gear", TextDungeon.skin);
		gearButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				armorTable.setVisible(false);
				consumableTable.setVisible(false);
				gearTable.setVisible(true);
				weaponTable.setVisible(false);
			}
		});
		weaponsButton = new TextButton("Weapons", TextDungeon.skin);
		weaponsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				armorTable.setVisible(false);
				consumableTable.setVisible(false);
				gearTable.setVisible(false);
				weaponTable.setVisible(true);
			}
		});

		equipmentTable.add(armorButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		equipmentTable.row();
		equipmentTable.add(consumablesButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		equipmentTable.row();
		equipmentTable.add(gearButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);
		equipmentTable.row();
		equipmentTable.add(weaponsButton).top().left().width(BUTTON_WIDTH).pad(BUTTON_PAD);

		centerStack.add(equipmentTable);
		equipmentTable.setVisible(false);
	}

	private void initEquipmentLists() {
		armorMap = new TreeMap<>();
		consumableList = new ArrayList<>();
		gearMap = new TreeMap<>();
		weaponList = new ArrayList<>();

		ChainMail chainMail = new ChainMail();
		DemonArmor demonArmor = new DemonArmor();
		DragonhideArmor dragonArmor = new DragonhideArmor();
		EnchantedRobes enRobes = new EnchantedRobes();
		LeatherArmor leatherArmor = new LeatherArmor();
		PaddedArmor paddedArmor = new PaddedArmor();
		PlateArmor plateArmor = new PlateArmor();
		SplintArmor splintArmor = new SplintArmor();

		armorMap.put(chainMail.getName(), chainMail.getStatistics());
		armorMap.put(demonArmor.getName(), demonArmor.getStatistics());
		armorMap.put(dragonArmor.getName(), dragonArmor.getStatistics());
		armorMap.put(enRobes.getName(), enRobes.getStatistics());
		armorMap.put(leatherArmor.getName(), leatherArmor.getStatistics());
		armorMap.put(paddedArmor.getName(), paddedArmor.getStatistics());
		armorMap.put(plateArmor.getName(), plateArmor.getStatistics());
		armorMap.put(splintArmor.getName(), splintArmor.getStatistics());

		consumableList.add(new Antidote());
		consumableList.add(new Bandage());
		consumableList.add(new BurningPitch());
		consumableList.add(new Chalk());
		consumableList.add(new DemonAshes());
		consumableList.add(new EldritchEye());
		consumableList.add(new FlaskOfWater());
		consumableList.add(new GoblinMoonshine());
		consumableList.add(new HealingPotion());
		consumableList.add(new Laudanum());
		consumableList.add(new LiquidCourage());
		consumableList.add(new LizardmanAntidote());
		consumableList.add(new Lockpicks());
		consumableList.add(new MakeshiftTrap());
		consumableList.add(new PoisonVial());
		consumableList.add(new RatBag());
		consumableList.add(new Ration());
		consumableList.add(new Rope());
		consumableList.add(new SketchyMushroom());
		consumableList.add(new TrollBrew());
		consumableList.add(new Whetstone());
		consumableList.add(new WyvernVenom());
		
		AncientShield ancientShield = new AncientShield();
		BearCloak bearCloak = new BearCloak();
		Buckler buckler = new Buckler();
		Cloak cloak = new Cloak();
		DemonHorn demonHorn = new DemonHorn();
		DragonScale dragonScale = new DragonScale();
		DruidStaff druidStaff = new DruidStaff();
		GoblinTalisman gobTalisman = new GoblinTalisman();
		GreatHelm greatHelm = new GreatHelm();
		HolyRelic holyRelic = new HolyRelic();
		LichSkull lichSkull = new LichSkull();
		LightHelm lightHelm = new LightHelm();
		LizardmanShield lizShield = new LizardmanShield();
		NecromancyTalisman necTalisman = new NecromancyTalisman();
		Shield shield = new Shield();
		LesserStaff staff = new LesserStaff();
		TowerShield towerShield = new TowerShield();

		gearMap.put(ancientShield.getName(), ancientShield.getStatistics());
		gearMap.put(bearCloak.getName(), bearCloak.getStatistics());
		gearMap.put(buckler.getName(), buckler.getStatistics());
		gearMap.put(cloak.getName(), cloak.getStatistics());
		gearMap.put(demonHorn.getName(), demonHorn.getStatistics());
		gearMap.put(dragonScale.getName(), dragonScale.getStatistics());
		gearMap.put(druidStaff.getName(), druidStaff.getStatistics());
		gearMap.put(gobTalisman.getName(), gobTalisman.getStatistics());
		gearMap.put(greatHelm.getName(), greatHelm.getStatistics());
		gearMap.put(holyRelic.getName(), holyRelic.getStatistics());
		gearMap.put(lichSkull.getName(), lichSkull.getStatistics());
		gearMap.put(lightHelm.getName(), lightHelm.getStatistics());
		gearMap.put(lizShield.getName(), lizShield.getStatistics());
		gearMap.put(necTalisman.getName(), necTalisman.getStatistics());
		gearMap.put(shield.getName(), shield.getStatistics());
		gearMap.put(staff.getName(), staff.getStatistics());
		gearMap.put(towerShield.getName(), towerShield.getStatistics());

		weaponList.add(new ArmingSword());
		weaponList.add(new Battleaxe());
		weaponList.add(new Dagger());
		weaponList.add(new Estoc());
		weaponList.add(new ExecutionerBlade());
		weaponList.add(new Flail());
		weaponList.add(new Greataxe());
		weaponList.add(new Greatsword());
		weaponList.add(new Halberd());
		weaponList.add(new Handaxe());
		weaponList.add(new Longsword());
		weaponList.add(new Mace());
		weaponList.add(new Maul());
		weaponList.add(new Morningstar());
		weaponList.add(new OldSword());
		weaponList.add(new Pike());
		weaponList.add(new Rapier());
		weaponList.add(new RustySword());
		weaponList.add(new Shortsword());
		weaponList.add(new Spear());
		weaponList.add(new Warhammer());

		weaponList.add(new AncientAxe());
		weaponList.add(new AncientSword());
		weaponList.add(new DemonSword());
		weaponList.add(new EldritchHorn());
		weaponList.add(new GiantSnakeFang());
		weaponList.add(new GoblinKnife());
		weaponList.add(new HydraFang());
		weaponList.add(new OgreClub());
	}

}
