package com.example.jc.myapplication.data;

import android.provider.BaseColumns;

/**
 * Created by JC on 3/2/18.
 */

public class InfoContract {


    public static final class InfoEntry implements BaseColumns{

        public static final String TABLE_NAME = "info";
        public static final String COLUMN_ID = "identification";
        public static final String COLUMN_TYPE = "json_type";
        public static final String COLUMN_JSON = "json_data";

    }
}
