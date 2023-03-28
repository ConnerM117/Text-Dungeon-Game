package rooms;

import java.util.ArrayList;
import java.util.List;

import floors.Floor;
import items.Item;

public abstract class ScriptedCombatRoom extends Room {

	protected List<Item> dummyList;
	protected boolean isAttacking;
	
	public ScriptedCombatRoom(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		hasScriptedCombat = true;
		dummyList = new ArrayList<>();
		isAttacking = false;
	}

	@Override
	public List<Item> getItems() {
		if (isCompleted()) { //only return items if the combat has been defeated
			return droppedItems;
		} else { //otherwise return an empty list
			return dummyList;
		}
	}
	
	@Override
	public boolean hasCombat() {
		if (isAttacking) {
			isAttacking = false;
			return true;
		}
		return false;
	}
	
	@Override
	public void setCompleted(boolean isCompleted) {
		super.setCompleted(isCompleted);
		isAttacking = false;
	}
}
