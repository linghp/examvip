package com.cqvip.mobilevers.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.cqvip.mobilevers.db.ThreeLevelType;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table THREE_LEVEL_TYPE.
*/
public class ThreeLevelTypeDao extends AbstractDao<ThreeLevelType, Long> {

    public static final String TABLENAME = "THREE_LEVEL_TYPE";

    /**
     * Properties of entity ThreeLevelType.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Orderid = new Property(1, Long.class, "orderid", false, "ORDERID");
        public final static Property Twoleveltypeid = new Property(2, Integer.class, "twoleveltypeid", false, "TWOLEVELTYPEID");
        public final static Property ThreeLevelTypeid = new Property(3, Integer.class, "threeLevelTypeid", false, "THREE_LEVEL_TYPEID");
        public final static Property Typename = new Property(4, String.class, "typename", false, "TYPENAME");
        public final static Property Isprivate = new Property(5, Boolean.class, "isprivate", false, "ISPRIVATE");
    };


    public ThreeLevelTypeDao(DaoConfig config) {
        super(config);
    }
    
    public ThreeLevelTypeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'THREE_LEVEL_TYPE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ORDERID' INTEGER," + // 1: orderid
                "'TWOLEVELTYPEID' INTEGER UNIQUE ," + // 2: twoleveltypeid
                "'THREE_LEVEL_TYPEID' INTEGER UNIQUE ," + // 3: threeLevelTypeid
                "'TYPENAME' TEXT," + // 4: typename
                "'ISPRIVATE' INTEGER);"); // 5: isprivate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'THREE_LEVEL_TYPE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ThreeLevelType entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long orderid = entity.getOrderid();
        if (orderid != null) {
            stmt.bindLong(2, orderid);
        }
 
        Integer twoleveltypeid = entity.getTwoleveltypeid();
        if (twoleveltypeid != null) {
            stmt.bindLong(3, twoleveltypeid);
        }
 
        Integer threeLevelTypeid = entity.getThreeLevelTypeid();
        if (threeLevelTypeid != null) {
            stmt.bindLong(4, threeLevelTypeid);
        }
 
        String typename = entity.getTypename();
        if (typename != null) {
            stmt.bindString(5, typename);
        }
 
        Boolean isprivate = entity.getIsprivate();
        if (isprivate != null) {
            stmt.bindLong(6, isprivate ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ThreeLevelType readEntity(Cursor cursor, int offset) {
        ThreeLevelType entity = new ThreeLevelType( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // orderid
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // twoleveltypeid
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // threeLevelTypeid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // typename
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0 // isprivate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ThreeLevelType entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setOrderid(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTwoleveltypeid(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setThreeLevelTypeid(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setTypename(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIsprivate(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ThreeLevelType entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ThreeLevelType entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}