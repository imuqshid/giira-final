package gira.cdap.com.giira.WeatherForecast.Data.Service;

import gira.cdap.com.giira.WeatherForecast.Data.Channel;

/**
 * Created by siva on 9/5/2016.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
