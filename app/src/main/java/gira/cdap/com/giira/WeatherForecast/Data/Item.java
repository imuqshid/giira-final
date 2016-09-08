package gira.cdap.com.giira.WeatherForecast.Data;
import org.json.JSONObject;
/**
 * Created by siva on 9/5/2016.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
