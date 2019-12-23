package org.keith.commons.algrorithm.tree;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;

public class TreeQryByFile {
    public static void main(String[] args) {

        String root = "1";
        Integer level = 5;
        File file = new File("d:/file/tree.txt");

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
        Map<String, LevelNode> surplusMap = new LinkedHashMap() {{
            put(rootNodeId, new LevelNode(1, null));
        }};
        List<ResultNode> result = new ArrayList<>();
        // readFile readLine()
        int i = 0;
        String data = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        data:while (StringUtils.isNotBlank(data = bufferedReader.readLine())) {
            Iterator<String> iterator = surplusMap.keySet().iterator();
            while (iterator.hasNext()) {
                String surplusKey = iterator.next();
                if (surplusMap.get(surplusKey).getLevel() > level) {
                    iterator.remove();
                    if (!iterator.hasNext()) {
                        return result;
                    }
                    continue;
                }
                if (data.startsWith(surplusKey + "&")) {
                    Node currentNode = parseNode(data);
                    LevelNode currentLevel = surplusMap.get(surplusKey);
                    // add result
                    result.add(getResultNode(currentNode, currentLevel));
                    // remove this
                    surplusMap.remove(surplusKey);
                    // set children -> surplusMap
                    setLevelNode(surplusMap, currentNode, currentLevel, level);
                    continue data;
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
        String[] dataArr = lineStr.split("&");
        String id = dataArr[0];
        String name = dataArr[1];
        String left = dataArr[2];
        String right = dataArr[3];
        String leftEdge = dataArr[4];
        String rightEdge = dataArr[5];
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
