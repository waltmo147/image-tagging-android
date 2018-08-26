package com.example.jc.myapplication.util;

import android.content.Context;

public class TagSingleton {
    private static TagSingleton uniqInstance;

    private TagSingleton() {
    }

    private String tag_id;

    private Context context;

    public static TagSingleton getInstance() {
        if (uniqInstance == null) {
            {
                if (uniqInstance == null)
                    uniqInstance = new TagSingleton();
            }
        }
        return uniqInstance;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
