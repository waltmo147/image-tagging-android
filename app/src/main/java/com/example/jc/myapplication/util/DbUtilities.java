package com.example.jc.myapplication.util;

import android.content.ContentValues;
import android.content.Context;

import com.example.jc.myapplication.data.InfoContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JC on 3/2/18.
 */

public final class DbUtilities {

    public static ContentValues[] getInfoValuesFromJson(Context context,String json, String type){
        try {
            JSONArray parentArray = new JSONArray(json);
            ContentValues[] values = new ContentValues[parentArray.length()];


            for (int i = 0; i < parentArray.length(); i++) {
                ContentValues value = new ContentValues();

                JSONObject child = parentArray.getJSONObject(i);
                value.put(InfoContract.InfoEntry.COLUMN_ID,child.getInt("id"));
                value.put(InfoContract.InfoEntry.COLUMN_TYPE,type);
                value.put(InfoContract.InfoEntry.COLUMN_JSON,child.toString());

                values[i] = value;


            }
            return values;
        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }

    }
}
