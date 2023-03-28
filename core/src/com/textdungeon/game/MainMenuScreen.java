package com.textdungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.textdungeon.game.TextDungeon.ScreenKey;

public class MainMenuScreen implements Screen {

	private static final int FRAME_COLS = 8, FRAME_ROWS = 1;
	private static final int LEFT_TORCH_X = 50, LEFT_TORCH_Y = 200,
			RIGHT_TORCH_X = 460, RIGHT_TORCH_Y = 200;
	
	final TextDungeon game;
	
	Stage stage;
	Viewport viewport;
	Table table;
	Label titleLabel;
	TextButton enterDungeon;
	TextButton tutorials;
	TextButton exitGame;
	OrthographicCamera camera;
	
	Animation<TextureRegion> torchAnimation;
	Texture torchTexture;
	SpriteBatch spriteBatch;
	
	float stateTime;
	
	public MainMenuScreen(final TextDungeon game) {
		this.game = game;
		
		viewport = new FitViewport(800, 480);
		stage = new Stage(viewport);
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		torchTexture = new Texture(Gdx.files.internal("Torch.png"));
		TextureRegion[][] tmp = TextureRegion.split(torchTexture, 
				torchTexture.getWidth() / FRAME_COLS, 
				torchTexture.getHeight() / FRAME_ROWS);
		
		TextureRegion[] torchFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				torchFrames[index++] = tmp[i][j];
			}
		}
		
		torchAnimation = new Animation<TextureRegion>(0.25f, torchFrames);
		
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
		
		Window exit = new Window("Exit Game", TextDungeon.skin);
		exit.setMovable(false);
		TextButton yes = new TextButton("Yes", TextDungeon.skin);
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		TextButton no = new TextButton("No", TextDungeon.skin);
		no.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				exit.setVisible(false);
			}
		});
		exit.add(new Label("Are you sure you want to exit?", TextDungeon.skin)).colspan(2);
		exit.row();
		exit.add(yes);
		exit.add(no);
		exit.pack();
		float newWidth = 300, newHeight = 100;
		exit.setBounds((stage.getWidth() - newWidth ) / 2, (stage.getHeight() - newHeight ) / 2, newWidth , newHeight );
		exit.setVisible(false);
		stage.addActor(exit);
		
		titleLabel = new Label("TEXT\nDUNGEON", new LabelStyle(new BitmapFont(Gdx.files.internal("Skins/NovemberBitmapFont96.fnt")), Color.WHITE));
		titleLabel.setAlignment(Align.center);
		enterDungeon = new TextButton("Enter the Dungeon", TextDungeon.skin);
		enterDungeon.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(ScreenKey.CHARACTER);
			}
		});
		
		tutorials = new TextButton("Tutorials", TextDungeon.skin);
		tutorials.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(ScreenKey.TUTORIAL);
			}
		});
		
		exitGame = new TextButton("Exit Game", TextDungeon.skin);
		exitGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				exit.setVisible(true);
			}
		});		
		
		//table.setDebug(true);
		
		table.add(titleLabel).expandY().colspan(3).center();
		table.row();
		table.add(enterDungeon).width(Gdx.graphics.getWidth()/3).pad(20).padBottom(50);
		table.add(tutorials).width(Gdx.graphics.getWidth()/3).pad(20).padBottom(50);
		table.add(exitGame).width(Gdx.graphics.getWidth()/3).pad(20).padBottom(50);
		
		camera = new OrthographicCamera();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stateTime = 0f;
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);
		
		stateTime += Gdx.graphics.getDeltaTime();
		
		TextureRegion currentFrame = torchAnimation.getKeyFrame(stateTime, true);
		
		camera.update();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, LEFT_TORCH_X, LEFT_TORCH_Y);
		spriteBatch.draw(currentFrame, RIGHT_TORCH_X, RIGHT_TORCH_Y);
		spriteBatch.end();
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
		spriteBatch.dispose();
		stage.dispose();
		game.dispose();
	}

}
