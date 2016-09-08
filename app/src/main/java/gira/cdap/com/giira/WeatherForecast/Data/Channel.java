package gira.cdap.com.giira.WeatherForecast.Data;
import org.json.JSONObject;
/**
 * Created by siva on 9/5/2016.
 */
public class Channel {
    private Units units;
    private Item item;

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }


    public void populate(JSONObject data) {

        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

    }
}
