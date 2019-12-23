package org.keith.commons.algrorithm.tree;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class TreeQryByStr {

    private static final List<String> datas = Arrays.asList(
            "1&1&2&3&1-left_edge&1-right_edge",
            "2&2&4&5&2-left_edge&2-right_edge",
            "3&3&6&7&3-left_edge&3-right_edge",
            "4&4&8&9&4-left_edge&4-right_edge",
            "5&5&10&11&5-left_edge&5-right_edge",
            "6&6&12&13&6-left_edge&6-right_edge",
            "7&7&14&15&7-left_edge&7-right_edge",
            "8&8&16&17&8-left_edge&8-right_edge",
            "9&9&18&19&9-left_edge&9-right_edge",
            "10&10&20&21&10-left_edge&10-right_edge",
            "11&11&22&23&11-left_edge&11-right_edge",
            "12&12&24&25&12-left_edge&12-right_edge",
            "13&13&26&27&13-left_edge&13-right_edge",
            "14&14&28&29&14-left_edge&14-right_edge",
            "15&15&30&31&15-left_edge&15-right_edge",
            "16&16&32&33&16-left_edge&16-right_edge",
            "17&17&34&35&17-left_edge&17-right_edge",
            "18&18&36&37&18-left_edge&18-right_edge",
            "19&19&38&39&19-left_edge&19-right_edge",
            "20&20&40&41&20-left_edge&20-right_edge",
            "21&21&42&43&21-left_edge&21-right_edge",
            "22&22&44&45&22-left_edge&22-right_edge",
            "23&23&46&47&23-left_edge&23-right_edge",
            "24&24&48&49&24-left_edge&24-right_edge",
            "25&25&50&51&25-left_edge&25-right_edge",
            "26&26&52&53&26-left_edge&26-right_edge",
            "27&27&54&55&27-left_edge&27-right_edge",
            "28&27&56&57&28-left_edge&28-right_edge",
            "29&29&58&59&29-left_edge&29-right_edge",
            "30&30&60&61&29-left_edge&30-right_edge",
            "31&31&62&63&31-left_edge&31-right_edge");

    public static void main(String[] args) {

        String root = "2";
        Integer level = 5;
        List<ResultNode> result = getTree(root, level);
        System.out.println("result.count:"+result.size());
        System.out.println(JSON.toJSONString(result));
    }

    public static List<ResultNode> getTree(String rootNodeId, Integer level) {
        Map<String, LevelNode> surplusMap = new LinkedHashMap(){{
            put(rootNodeId, new LevelNode(1, null));
        }};
        List<ResultNode> result = new ArrayList<>();
        // readFile readLine()
        datas:
        for(int i = 0; i < datas.size(); i++) {
            String currentData = datas.get(i);
            Iterator<String> iterator = surplusMap.keySet().iterator();
            while(iterator.hasNext()) {
                String surplusKey = iterator.next();
                if(surplusMap.get(surplusKey).getLevel() > level) {
                    iterator.remove();
                    if(!iterator.hasNext()) {
                        return result;
                    }
                    continue;
                }
                if(currentData.startsWith(surplusKey+"&")) {
                    Node currentNode = parseNode(currentData);
                    LevelNode currentLevel = surplusMap.get(surplusKey);
                    // add result
                    ResultNode resultNode = new ResultNode();
                    resultNode.setId(currentNode.getId());
                    resultNode.setName(currentNode.getName());
                    resultNode.setPid(currentLevel.getParentId());
                    resultNode.setEdge(currentLevel.getEdge());
                    result.add(resultNode);
                    // remove this
                    surplusMap.remove(surplusKey);
                    // put children
                    LevelNode leftChild = new LevelNode(currentLevel.getLevel()+1, currentNode.getId());
                    if(currentLevel.getLevel() < level) {
                        leftChild.setEdge(currentNode.getLeftEdge());
                    }
                    LevelNode rightChild = new LevelNode(currentLevel.getLevel()+1, currentNode.getId());
                    if(currentLevel.getLevel() < level) {
                        rightChild.setEdge(currentNode.getRightEdge());
                    }
                    surplusMap.put(currentNode.getLeftId(), leftChild);
                    surplusMap.put(currentNode.getRightId(), rightChild);
                    continue datas;
                }
            }
        }
        return result;
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
