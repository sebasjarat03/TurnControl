package model;

import java.io.Serializable;
import java.util.ArrayList;

public class TurnType implements Serializable{
	
	private String name;
	private float duration;
	private ArrayList<Turn> turns;
	
	public TurnType(String name, float duration) {
		this.name = name;
		this.duration = duration;
		turns = new ArrayList<Turn>();
	}

	public String getName() {
		return name;
	}

	public float getDuration() {
		return duration;
	}

	public ArrayList<Turn> getTurns() {
		return turns;
	}
	
	public String toString() {
		String msg = "NAME: " + name + " - DURATION: " + duration;
		return msg;
	}

}
