package it.polimi.ingsw.am40.GUI;

import javafx.event.Event;
import javafx.event.EventType;

public class CustomEvent extends Event {

	private static final long serialVersionUID = 123456L;

	public static final EventType<CustomEvent> TILE_SELECTED = new EventType<>(Event.ANY, "Tile selected");
	public static final EventType<CustomEvent> BOOKSHELF_DONE = new EventType<>(Event.ANY, "Bookshelf done");
	public static final EventType<CustomEvent> BOARD_ADD_TILE = new EventType<>(Event.ANY, "Board add tile");
	public static final EventType<CustomEvent> BOARD_TILE_PICKABLE = new EventType<>(Event.ANY,
			"Board set tile pickable");

	private Object obj;
	private boolean flag;
	private int x;
	private int y;

	public Object getObj() {
		return obj;
	}

	public boolean getFlag() {
		return flag;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public CustomEvent(EventType<? extends Event> arg0, Object obj, boolean selected) {
		super(arg0);

		this.obj = obj;
		this.flag = selected;
	}

	public CustomEvent(EventType<? extends Event> arg0) {
		super(arg0);
	}

	public CustomEvent(EventType<? extends Event> arg0, Object obj) {
		super(arg0);

		this.obj = obj;
	}

	public CustomEvent(EventType<? extends Event> arg0, int x, int y) {
		super(arg0);

		this.x = x;
		this.y = y;
	}
}
