package com.saintsrobotics.corebot.dash;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.json.*;

import com.saintsrobotics.corebot.Robot;


public class WebDashboardActual extends WebSocketServer implements WebDashboard {
	
	public JSONObject values;
	public JSONObject values(){
		return values;
	}
	private WebSocket defaultSocket;
	
	
	
	public WebDashboardActual() throws UnknownHostException {
		super(new InetSocketAddress(1899));
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
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		Robot.log("Connected to " + conn.getRemoteSocketAddress().toString());
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		Robot.log("closed " + conn.getRemoteSocketAddress().toString() + " with code " + code + " because " + reason + "; closed by " + (remote ? "remote":"local"));
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		this.defaultSocket = conn;
		Robot.log("Message: " + message);
		if(message.equals("Debug!")){
			Robot.log(values.toString());
			return;
		}
		JSONObject json = new JSONObject(message);
		if(json.getString("type").equals("error")){
			Robot.log(json.toString());
			return;
			//welp
			//TODO: actually fix this
		}
		boolean confirm = json.getString("type").equals("confirm");
		Iterator<String> keys =json.keys();
		while(keys.hasNext()){
			String changeKey = keys.next();
			if(changeKey.equals("type")){
				if(!keys.hasNext()) break;
				changeKey = keys.next();
			}
			JSONObject changeValues = values.getJSONObject(confirm?"client":"server").getJSONObject(changeKey);
			Iterator<String> things = json.getJSONObject(changeKey).keys();
			while(things.hasNext()){
				String key = things.next();
				if(!changeValues.has(key)){
					defaultSocket.send("{'type':'error','message':'I never told you to do that'}");
					return;
				}
			}
			things = json.getJSONObject(changeKey).keys();
			while(things.hasNext()){
				String key = things.next();
				values.getJSONObject(confirm?"client":"server").getJSONObject(changeKey).put(key, json.getJSONObject(changeKey).get(key));
			}
		}
		if(!confirm)json.put("type", "confirm");
		send(json.toString());
	}
	public void send(String message){
		if(this.defaultSocket == null)
			this.defaultSocket = this.connections().iterator().next();
		this.defaultSocket.send(message);
	}
	public ValueFamily family(String s){
		return new ValueFamily(s,this);
	}
	@Override
	public void onError(WebSocket conn, Exception ex) {
		Robot.log("ERROR: " + ex.getMessage());
	}
	
}
