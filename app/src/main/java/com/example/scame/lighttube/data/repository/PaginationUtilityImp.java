package com.example.scame.lighttube.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.util.Pair;

public class PaginationUtilityImp implements PaginationUtility {

    private SharedPreferences sharedPreferences;

    private Context context;

    @StringRes
    private int pageStringId;

    @StringRes
    private int tokenStringId;

    public PaginationUtilityImp(SharedPreferences sharedPreferences, Context context,
                                Pair<Integer, Integer> keysPair) {
        this.sharedPreferences = sharedPreferences;
        this.context = context;
        this.tokenStringId = keysPair.first;
        this.pageStringId = keysPair.second;
    }

    @Override
    public void saveCurrentPage(int page) {
        sharedPreferences.edit().putInt(context.getString(pageStringId), page).apply();
    }

    @Override
    public void saveNextPageToken(String nextPageToken) {
        sharedPreferences.edit().putString(context.getString(tokenStringId), nextPageToken).apply();
    }

    @Override
    public int getSavedPage() {
        return sharedPreferences.getInt(context.getString(pageStringId), 0);
    }

    @Override
    public String getNextPageToken(int page) {
        return page > getSavedPage() ? sharedPreferences.getString(context.getString(tokenStringId), null)
                                     : null;
    }

    @Override
    public void setPageStringId(int pageStringId) {
        this.pageStringId = pageStringId;
    }

    @Override
    public void setTokenStringId(int tokenStringId) {
        this.tokenStringId = tokenStringId;
    }

    @Override
    public int getPageStringId() {
        return pageStringId;
    }

    @Override
    public int getTokenStringId() {
        return tokenStringId;
    }
}
