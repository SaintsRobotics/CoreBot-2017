package com.saintsrobotics.corebot.output;
import org.json.JSONObject;

import com.saintsrobotics.corebot.dash.WebDashboardActual;
public class MotorsWebDashboard{
    private static JSONObject vals;
    private static WebDashboardActual web;
    public static void init(WebDashboardActual webDash){
        web = webDash;
        vals = web.values.getJSONObject("server").getJSONObject("motors");
    }
    public static Motor get(String motor){
        return Motors.get(vals.getInt(motor),false);//vals.getJSONObject(motor).getBoolean("inverted"));
    }
    public static void refresh(){
        Motors.stopAll();
        vals = web.values.getJSONObject("server").getJSONObject("motors");
    }
}
