package org.keith.commons.algrorithm.tree;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadFile {

    public static void main(String[] args) throws Exception {
        File file = new File("d:/file/tree.txt");
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        int i=0;
        String str = null;
        while(StringUtils.isNotBlank(str = bufferedReader.readLine()))
        {
//                if(i++==rowIndex){
//                    break;
//                }
            System.out.println("line:"+str);
        }

        bufferedReader.close();

    }
}
