package com.daemonw.deviceinfo.model;

import java.util.ArrayList;
import java.util.List;

public class SocInfo implements ListInfo {
    public String cpuInfo;
    public String cpu;
    public String processor;
    public String core;
    public String features;

    public String gpu;
    public String gpuVendor;
    public String gpuVersion;

    @Override
    public List<ItemInfo> toList() {
        List<ItemInfo> info = new ArrayList<>();
        info.add(new ItemInfo("CPU信息", "", ItemInfo.TYPE_ITEM_HEADER));
        info.add(new ItemInfo("CPU", cpu, "cpu"));
        info.add(new ItemInfo("Processor", processor, "processor"));
        info.add(new ItemInfo("Feature", features, "features"));
        info.add(new ItemInfo("Core", core, "core"));
        //info.add(new ItemInfo("CPU信息", cpuInfo));
        info.add(new ItemInfo("GPU信息", "", ItemInfo.TYPE_ITEM_HEADER));
        info.add(new ItemInfo("GPU", gpu, "gpu"));
        info.add(new ItemInfo("Vendor", gpuVendor, "gpuVendor"));
        info.add(new ItemInfo("OpenGL ES", gpuVersion, "gpuVersion"));
        return info;
    }
}
