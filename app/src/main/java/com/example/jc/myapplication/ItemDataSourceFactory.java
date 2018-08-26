package com.example.jc.myapplication;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.jc.myapplication.model.ImageItem;

public class ItemDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, ImageItem>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, ImageItem> create() {
        //getting our data source object
        ItemDataSource itemDataSource = new ItemDataSource();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, ImageItem>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}