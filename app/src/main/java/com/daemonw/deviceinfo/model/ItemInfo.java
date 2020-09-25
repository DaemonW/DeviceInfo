package com.daemonw.deviceinfo.model;

public class ItemInfo {
    public static final int TYPE_ITEM_INFO = 0;
    public static final int TYPE_ITEM_HEADER = 1;
    private int type;
    private String key;
    private String val;

    public ItemInfo(String key, String val) {
        this.key = key;
        this.val = val;
        this.type = TYPE_ITEM_INFO;
    }

    public ItemInfo(String key, String val, int type) {
        this.key = key;
        this.val = val;
        this.type = type;
    }

    public int getItemType(){
        return type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
