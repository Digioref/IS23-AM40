package it.polimi.ingsw.am40.GUI;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * It represents a custom event to be triggered when specific conditions happen
 */
public class CustomEvent extends Event {

	/**
	 * It represents the event related to the selection of a tile
	 */
	public static final EventType<CustomEvent> TILE_SELECTED = new EventType<>(Event.ANY, "Tile selected");
	/**
	 * It represents the event related to the completion of the bookshelf
	 */
	public static final EventType<CustomEvent> BOOKSHELF_DONE = new EventType<>(Event.ANY, "Bookshelf done");

	private Object obj;
	private boolean flag;

	/**
	 * It returns the object of the event
	 * @return object of the event
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * It returns the flag of the object
	 * @return flag of the object
	 */
	public boolean getFlag() {
		return flag;
	}

	/**
	 * It creates a custom event assigning the type of the event, the object of the event and the flag of the event
	 * @param arg0 type of the event
	 * @param obj object of the event
	 * @param selected flag of the event
	 */
	public CustomEvent(EventType<? extends Event> arg0, Object obj, boolean selected) {
		super(arg0);

		this.obj = obj;
		this.flag = selected;
	}

	/**
	 * It creates the custom event by assigning only the type of the event
	 * @param arg0 type of the event
	 */
	public CustomEvent(EventType<? extends Event> arg0) {
		super(arg0);
	}

}
