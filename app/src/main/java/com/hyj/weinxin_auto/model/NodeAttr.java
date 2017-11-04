package com.hyj.weinxin_auto.model;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/10/28.
 */

public class NodeAttr {
    private Map<String,Object> function = new HashMap<String,Object>();
    private AccessibilityNodeInfo node;
    private String childPath;


    public String getChildPath() {
        return childPath;
    }

    public void setChildPath(String childPath) {
        this.childPath = childPath;
    }

    public Map<String, Object> getFunction() {
        return function;
    }

    public void setFunction(Map<String, Object> function) {
        this.function = function;
    }

    public AccessibilityNodeInfo getNode() {
        return node;
    }

    public void setNode(AccessibilityNodeInfo node) {
        this.node = node;
    }


}
