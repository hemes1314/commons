package org.keith.commons.algrorithm.tree;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;

public class TreeQryByFile {
    public static void main(String[] args) {

        String root = "0";
        Integer level = 6;
        File file = new File("d:/file/tree2.txt");

        List<ResultNode> result = null;
        try {
            result = new TreeQryByFile().getTree(file, root, level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result.count:"+result.size());
        System.out.println(JSON.toJSONString(result));
    }

    public List<ResultNode> getTree(File file, String rootNodeId, Integer level) throws Exception {
        Map<String, LevelNode> surplusMap = new HashMap() {{
            put(rootNodeId, new LevelNode(1, null));
        }};
        List<ResultNode> result = new ArrayList<>();
//        Set<String> discardKeys = new HashSet<>();
        // readFile readLine()
        int i = 0;
        String data = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while (StringUtils.isNotBlank(data = bufferedReader.readLine())) {
            String surplusKey = String.valueOf(i++);
            LevelNode currentLevel = surplusMap.get(surplusKey);
            if(currentLevel == null) {
                continue;
            }
            if (currentLevel.getLevel() > level) {
                surplusMap.remove(surplusKey);
                if (surplusMap.keySet().isEmpty()) {
                    return result;
                }
                continue;
            }
            if (data.startsWith(surplusKey + "&")) {
                Node currentNode = parseNode(data);
                // add result
                result.add(getResultNode(currentNode, currentLevel));
                // remove this
                surplusMap.remove(surplusKey);
                // set children -> surplusMap
                setLevelNode(surplusMap, currentNode, currentLevel, level);
                if (surplusMap.keySet().isEmpty()) {
                    return result;
                }
            }
        }
        return result;
    }

    private void setLevelNode(Map<String, LevelNode> surplusMap, Node currentNode, LevelNode currentLevel, Integer level) {
        LevelNode leftChild = new LevelNode(currentLevel.getLevel() + 1, currentNode.getId());
        if (currentLevel.getLevel() < level) {
            leftChild.setEdge(currentNode.getLeftEdge());
        }
        LevelNode rightChild = new LevelNode(currentLevel.getLevel() + 1, currentNode.getId());
        if (currentLevel.getLevel() < level) {
            rightChild.setEdge(currentNode.getRightEdge());
        }
        surplusMap.put(currentNode.getLeftId(), leftChild);
        surplusMap.put(currentNode.getRightId(), rightChild);
    }

    private ResultNode getResultNode(Node currentNode, LevelNode currentLevel) {
        ResultNode resultNode = new ResultNode();
        resultNode.setId(currentNode.getId());
        resultNode.setName(currentNode.getName());
        resultNode.setPid(currentLevel.getParentId());
        resultNode.setEdge(currentLevel.getEdge());
        return resultNode;
    }

    private static Node parseNode(String lineStr) {
        List<String> dataList = CollectionUtil.toList(lineStr.split("&"));
        int total = 6 - dataList.size();
        for(int i = 0; i < total; i++) {
            dataList.add("");
        }
        String id = dataList.get(0);
        String name = dataList.get(1);
        String left = dataList.get(2);
        String right = dataList.get(3);
        String leftEdge = dataList.get(4);
        String rightEdge = dataList.get(5);
        System.out.println("id:"+id+";"+"name:"+name+";"+"left:"+left+";"+"right:"+right+";"+"leftEdge:"+leftEdge+";"+"rightEdge:"+rightEdge);
        Node node = new Node();
        node.setId(id);
        node.setName(name);
        node.setLeftId(left);
        node.setRightId(right);
        node.setLeftEdge(leftEdge);
        node.setRightEdge(rightEdge);
        return node;
    }

    /*
     try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            int i=0;
            while((str = bufferedReader.readLine()) != null)
            {
                if(i++==rowIndex){
                    break;
                }
                //System.out.println(str);
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
     */
}
