package org.keith.commons.algrorithm.tree;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String lineStr = "&&&&&";
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
    }
}
