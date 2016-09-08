package gira.cdap.com.giira.WeatherForecast.Data;

import org.json.JSONObject;

/**
 * Created by siva on 9/5/2016.
 */
public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
