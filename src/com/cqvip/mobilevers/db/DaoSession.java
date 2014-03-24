package com.cqvip.mobilevers.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.cqvip.mobilevers.db.OneLevelType;
import com.cqvip.mobilevers.db.TwoLevelType;
import com.cqvip.mobilevers.db.User;

import com.cqvip.mobilevers.db.OneLevelTypeDao;
import com.cqvip.mobilevers.db.TwoLevelTypeDao;
import com.cqvip.mobilevers.db.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig oneLevelTypeDaoConfig;
    private final DaoConfig twoLevelTypeDaoConfig;
    private final DaoConfig userDaoConfig;

    private final OneLevelTypeDao oneLevelTypeDao;
    private final TwoLevelTypeDao twoLevelTypeDao;
    private final UserDao userDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        oneLevelTypeDaoConfig = daoConfigMap.get(OneLevelTypeDao.class).clone();
        oneLevelTypeDaoConfig.initIdentityScope(type);

        twoLevelTypeDaoConfig = daoConfigMap.get(TwoLevelTypeDao.class).clone();
        twoLevelTypeDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        oneLevelTypeDao = new OneLevelTypeDao(oneLevelTypeDaoConfig, this);
        twoLevelTypeDao = new TwoLevelTypeDao(twoLevelTypeDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(OneLevelType.class, oneLevelTypeDao);
        registerDao(TwoLevelType.class, twoLevelTypeDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        oneLevelTypeDaoConfig.getIdentityScope().clear();
        twoLevelTypeDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public OneLevelTypeDao getOneLevelTypeDao() {
        return oneLevelTypeDao;
    }

    public TwoLevelTypeDao getTwoLevelTypeDao() {
        return twoLevelTypeDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
