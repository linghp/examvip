package com.cqvip.mobilevers.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table FOUR_LEVEL_TYPE.
 */
public class FourLevelType {

    private Long id;
    private Long orderid;
    private Integer threeLevelTypeid;
    private Integer fourLevelTypeid;
    private String typename;
    private Boolean isprivate;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public FourLevelType() {
    }

    public FourLevelType(Long id) {
        this.id = id;
    }

    public FourLevelType(Long id, Long orderid, Integer threeLevelTypeid, Integer fourLevelTypeid, String typename, Boolean isprivate) {
        this.id = id;
        this.orderid = orderid;
        this.threeLevelTypeid = threeLevelTypeid;
        this.fourLevelTypeid = fourLevelTypeid;
        this.typename = typename;
        this.isprivate = isprivate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Integer getThreeLevelTypeid() {
        return threeLevelTypeid;
    }

    public void setThreeLevelTypeid(Integer threeLevelTypeid) {
        this.threeLevelTypeid = threeLevelTypeid;
    }

    public Integer getFourLevelTypeid() {
        return fourLevelTypeid;
    }

    public void setFourLevelTypeid(Integer fourLevelTypeid) {
        this.fourLevelTypeid = fourLevelTypeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Boolean getIsprivate() {
        return isprivate;
    }

    public void setIsprivate(Boolean isprivate) {
        this.isprivate = isprivate;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}