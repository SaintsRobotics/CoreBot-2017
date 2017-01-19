package com.saintsrobotics.corebot.dash;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

import com.saintsrobotics.corebot.Robot;

public class WebDashboardDummy implements WebDashboard{
	public WebDashboardDummy() {
		try {
			values = new JSONObject(new String(Files.readAllBytes(Paths.get("/home/lvuser/html/define.json"))));
			//Robot.log(new String(Files.readAllBytes(Paths.get("/home/lvuser/html/define.json"))));
		} catch (JSONException e) {
			Robot.log("JSONException in WebDashboard Boot!");
		} catch (IOException e) {
			Robot.log("IOException in WebDashboard Boot!");
			Robot.log(e.getMessage());
		}
	}
	public void send(String message){
		Robot.log("WebDashboard Down!", "Message is: " + message);
	}
	public ValueFamily family(String s){
		return new ValueFamily(s,this);
	}
	public JSONObject values;
	public JSONObject values(){
		return values;
	}
}
