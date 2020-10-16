package com.daemonw.deviceinfo.model;

public class ItemInfo {
    public static final int TYPE_ITEM_INFO = 0;
    public static final int TYPE_ITEM_HEADER = 1;
    private int type;
    private String key;
    private String val;
    private String tag;
    private boolean checked;

    public ItemInfo(String key, String val) {
        this(key, val, TYPE_ITEM_INFO, "");
    }

    public ItemInfo(String key, String val, String tag) {
        this(key, val, TYPE_ITEM_INFO, tag);
    }

    public ItemInfo(String key, String val, int type) {
        this(key, val, type, "");
    }

    public ItemInfo(String key, String val, int type, String tag) {
        this.key = key;
        this.val = val;
        this.type = type;
        this.tag = tag;
    }

    public int getItemType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        if (val == null) {
            return "";
        }
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
