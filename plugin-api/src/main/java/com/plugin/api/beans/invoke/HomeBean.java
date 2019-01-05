package com.plugin.api.beans.invoke;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author wujf
 *
 */
public class HomeBean implements Serializable {

    private String hid;//房间id
    private String hname;//房间名
    private int hsize;//大小
    private String hmeasurement;//尺寸
    
    private Date date;//传递时间出错
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getHid() {
        return hid;
    }
    public void setHid(String hid) {
        this.hid = hid;
    }
    public String getHname() {
        return hname;
    }
    public void setHname(String hname) {
        this.hname = hname;
    }
    public int getHsize() {
        return hsize;
    }
    public void setHsize(int hsize) {
        this.hsize = hsize;
    }
    public String getHmeasurement() {
        return hmeasurement;
    }
    public void setHmeasurement(String hmeasurement) {
        this.hmeasurement = hmeasurement;
    }
    
}
