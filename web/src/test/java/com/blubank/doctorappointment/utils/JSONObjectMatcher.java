package com.blubank.doctorappointment.utils;

import net.minidev.json.JSONObject;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.HashMap;

public class JSONObjectMatcher extends BaseMatcher<JSONObject> {

    private final JSONObject jsonObject;

    private JSONObjectMatcher(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public boolean matches(Object actual) {
        if (!(actual instanceof HashMap)) {
            return false;
        }

        for (String key : jsonObject.keySet()) {
            if (!jsonObject.getAsString(key).equals(((HashMap<?, ?>) actual).get(key))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {

    }

    public static JSONObjectMatcher equivalentToJson(JSONObject jsonObject) {
        return new JSONObjectMatcher(jsonObject);
    }
}
