package com.saintsrobotics.corebot.dash;

import org.json.JSONObject;

public interface WebDashboard {
	public ValueFamily family(String key);
	public JSONObject values();
	public void send(String s);
	public void log(String s);
	public void log(String s, String color);
	public void save();
}
