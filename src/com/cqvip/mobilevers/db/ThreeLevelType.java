package com.cqvip.mobilevers.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table THREE_LEVEL_TYPE.
 */
public class ThreeLevelType {

    private Long id;
    private Long orderid;
    private Integer twoleveltypeid;
    private Integer threeLevelTypeid;
    private String typename;
    private Boolean isprivate;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ThreeLevelType() {
    }

    public ThreeLevelType(Long id) {
        this.id = id;
    }

    public ThreeLevelType(Long id, Long orderid, Integer twoleveltypeid, Integer threeLevelTypeid, String typename, Boolean isprivate) {
        this.id = id;
        this.orderid = orderid;
        this.twoleveltypeid = twoleveltypeid;
        this.threeLevelTypeid = threeLevelTypeid;
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

    public Integer getTwoleveltypeid() {
        return twoleveltypeid;
    }

    public void setTwoleveltypeid(Integer twoleveltypeid) {
        this.twoleveltypeid = twoleveltypeid;
    }

    public Integer getThreeLevelTypeid() {
        return threeLevelTypeid;
    }

    public void setThreeLevelTypeid(Integer threeLevelTypeid) {
        this.threeLevelTypeid = threeLevelTypeid;
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