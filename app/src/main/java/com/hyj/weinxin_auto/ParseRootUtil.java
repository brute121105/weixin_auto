package com.hyj.weinxin_auto;

import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.hyj.weinxin_auto.model.NodeAttr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/10/29.
 */

public class ParseRootUtil {
    /**
     * @param root 非空根节点
     * @return 层级为1的root树形
     */
    public static List<List<NodeAttr>> initRootLevelNodes(AccessibilityNodeInfo root){
        List<List<NodeAttr>> treeNodes = new ArrayList<List<NodeAttr>>();
        List<NodeAttr> levelNodes = new ArrayList<NodeAttr>();
        levelNodes.add(setFuntion(root,0,""));
        treeNodes.add(levelNodes);
        return treeNodes;
    }

    /**
     * 根据 树形最后一层节点 生成 下一次节点
     * @param levelNodes 树形最后一层节点
     * @param treeNodes 树形
     */
    private static void parseNextLevelNodes(List<NodeAttr> levelNodes, List<List<NodeAttr>> treeNodes){
        List<NodeAttr> nexLlevelNodes = new ArrayList<NodeAttr>();
        if(levelNodes!=null&&levelNodes.size()>0){
            for(NodeAttr levelNode:levelNodes){
                int childCount = levelNode.getNode().getChildCount();
                if(childCount>0){
                    for(int i=0;i<childCount;i++){
                        NodeAttr na = setFuntion(levelNode.getNode().getChild(i),i,levelNode.getChildPath());
                        nexLlevelNodes.add(na);
                    }
                }
            }
        }
        treeNodes.add(nexLlevelNodes);
    }
   /* public static void parseTreeNode( List<List<NodeAttr>> treeNodes){
       // List<NodeAttr> levelNodes = treeNodes.get(treeNodes.size()-1);
        while (haveNextLevel(treeNodes.get(treeNodes.size()-1))){
            List<NodeAttr> levelNodes = treeNodes.get(treeNodes.size()-1);
            parseNextLevelNodes(levelNodes,treeNodes);
        }
    }*/
    public static List<List<NodeAttr>> createLevelNodes(AccessibilityNodeInfo root){
        List<List<NodeAttr>> treeNodes = initRootLevelNodes(root);
        while (haveNextLevel(treeNodes.get(treeNodes.size()-1))){
            List<NodeAttr> lastLevelNodes = treeNodes.get(treeNodes.size()-1);
            parseNextLevelNodes(lastLevelNodes,treeNodes);
        }
        return treeNodes;
    }

    /**
     * 判断最后一层的节点 是否还有有子节点
     * @param levelNodes
     * @return
     */
    private static boolean haveNextLevel(List<NodeAttr> levelNodes){
        boolean flag = false;
        for(NodeAttr levelNode:levelNodes){
            if(levelNode.getNode().getChildCount()>0){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 封装节点属性
     * @param node
     * @param childNum
     * @param childPath
     * @return
     */
    private static NodeAttr setFuntion(AccessibilityNodeInfo node,int childNum,String childPath){
        NodeAttr attr = new NodeAttr();
        //attr.setChildNum(childNum);
        attr.setChildPath(childPath+childNum);
        attr.setNode(node);
        Map<String,Object> function = new HashMap<String,Object>();
        function.put("isClickable",node.isClickable());
        function.put("isEditable",node.isEditable());
        function.put("getChildCount",node.getChildCount());
        function.put("getClassName",node.getClassName());
        function.put("getText",node.getText());
        function.put("getContentDescription",node.getContentDescription());
        attr.setFunction(function);
        return attr;
    }

    public static AccessibilityNodeInfo getNodePath(AccessibilityNodeInfo root,String path){
        if(path.length()<2){
            return null;
        }
        for(int i=1;i<path.length();i++){
            int childNo = Integer.parseInt(path.substring(i,i+1));
            if(root==null||childNo>=root.getChildCount()){
                return null;
            }else {
                root = root.getChild(childNo);
            }
        }
        return root;
    }
    public static void debugRoot(AccessibilityNodeInfo root){
        List<List<NodeAttr>> treeNodes =  ParseRootUtil.createLevelNodes(root);

            for(List<NodeAttr> levelNodes :treeNodes){
                for(NodeAttr levelNode:levelNodes){
                    System.out.println("debug--->"+ JSON.toJSON(levelNode));
                }
            }
    }
}
