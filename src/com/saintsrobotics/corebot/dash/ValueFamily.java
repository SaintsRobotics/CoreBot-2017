package com.saintsrobotics.corebot.dash;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The preferred method of reading and writing values to and from the WebDashboard
 * @author Benjamin Yin
 *
 */
public class ValueFamily {
	public final String index;
	private final WebDashboard wi;
	public ValueFamily(String index, WebDashboard webDashboard){
		this.index = index;
		this.wi = webDashboard;
	}
	public Object get(String key){
		return this.wi.values().getJSONObject("client").getJSONObject(index).get(key);
	}
	public int getInt(String key){
		return this.wi.values().getJSONObject("client").getJSONObject(index).getInt(key);
	}
	public double getDouble(String key){
		return this.wi.values().getJSONObject("client").getJSONObject(index).getDouble(key);
	}
	public String getString(String key){
		return this.wi.values().getJSONObject("client").getJSONObject(index).getString(key);
	}
	public JSONObject getJSON(String key){
		return this.wi.values().getJSONObject("client").getJSONObject(index).getJSONObject(key);
	}
	public JSONArray getArray(String key){
		return this.wi.values().getJSONObject("client").getJSONObject(index).getJSONArray(key);
	}
	public Object getOutput(String key){
		return this.wi.values().getJSONObject("server").getJSONObject(index).get(key);
	}
	public int getIntOutput(String key){
		return this.wi.values().getJSONObject("server").getJSONObject(index).getInt(key);
	}
	public double getDoubleOutput(String key){
		return this.wi.values().getJSONObject("server").getJSONObject(index).getDouble(key);
	}
	public String getStringOutput(String key){
		return this.wi.values().getJSONObject("server").getJSONObject(index).getString(key);
	}
	public JSONObject getJSONOutput(String key){
		return this.wi.values().getJSONObject("server").getJSONObject(index).getJSONObject(key);
	}
	public JSONArray getArrayOutput(String key){
		return this.wi.values().getJSONObject("server").getJSONObject(index).getJSONArray(key);
	}
	public JSONObject getAll(){
		return this.wi.values().getJSONObject("server").getJSONObject(index);
	}
	public JSONObject getAllOutputs(){
		return this.wi.values().getJSONObject("client").getJSONObject(index);
	}
	public void change(String key, Object value){
		JSONObject obj = new JSONObject();
		obj.put(key, value);
		change(obj);
	}
	public void change(JSONObject obj){
		JSONObject wrapper = new JSONObject();
		wrapper.put("type", "delta");
		wrapper.put(index, obj);
		this.wi.send(wrapper.toString());
	}
}
