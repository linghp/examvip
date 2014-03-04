package com.cqvip.mobilevers;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.db.DaoMaster;
import com.cqvip.mobilevers.db.DaoMaster.DevOpenHelper;
import com.cqvip.mobilevers.db.DaoSession;

public class MyApplication extends Application{
    public DaoMaster daoMaster;
    public DaoSession daoSession;
    public SQLiteDatabase db;
    
    @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this.getApplicationContext(), ConstantValues.DBNAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
