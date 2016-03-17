package hax.crutchbike.hakaton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by lantain on 17.03.16.
 */
public class Helpers {

    public static String JSONObjectToString(JSONObject object) throws JSONException {
        Iterator<String> keys = object.keys();
        String response = "";
        while (keys.hasNext()) {
            String key = keys.next();
            response += key + "=" + object.get(key).toString() + "&";
        }
        response.substring(0, response.length() - 1);
        return response;
    }
}
