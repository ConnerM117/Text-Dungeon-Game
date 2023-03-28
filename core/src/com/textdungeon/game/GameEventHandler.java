package com.textdungeon.game;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class GameEventHandler {
	
	private Queue<GameEvent> eventQueue;
	private InputAdapter touchToContinue;
	
	public GameEventHandler(GameScreen gameScreen) {
		eventQueue = new LinkedList<>();
		
		touchToContinue = new InputAdapter() {
			@Override
			public boolean touchUp(int x, int y, int pointer, int button) {
				if (!eventQueue.isEmpty()) {
					GameEvent event = eventQueue.poll();
					event.runEvent();
				}
				Gdx.input.setInputProcessor(gameScreen.stage);
				return true;
			}
		};
	}
	
	public void setInputToEventHandler() {
		Gdx.input.setInputProcessor(touchToContinue);
	}
	
	public void addEvent(GameEvent event) {
		eventQueue.add(event);
	}
	
	public boolean isEventQueueEmpty() {
		return eventQueue.isEmpty();
	}
	
	public void clearEvents() {
		eventQueue.clear();
	}
}
