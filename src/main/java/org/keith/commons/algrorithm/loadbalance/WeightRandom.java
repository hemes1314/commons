package org.keith.commons.algrorithm.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 实现思路
 * 还是拿上述的例子，3出现的概率是70%，我们给他的权重赋值为70，5出现的概率为25%，我们给他的权重赋值为25，10出现的概率为5%，我们给他的权重赋值为5.
 * 我们按照顺序计算出权重的加和，把当前数字出现的权重加和前的值作为其权重范围的起点值，把加和后的值作为其权重范围的终点值。
 * 这样的话，我们就可以使用Random.next(100)来做随机数，然后判断随机数落在的范围，然后映射到对应的优惠券数值即可。
 *
 * @date 2016-7-19
 * @author keith
 */
public class WeightRandom {
    public static void main(String[] args){
        WeightRandom wr = new WeightRandom();
        wr.initWeight(new String[]{"1","2","3","4"}, new Integer[]{100,100,200,600});

        Random r = new Random();
        for(int i = 0; i < 10; i++){
            Integer rv = r.nextInt(wr.getMaxRandomValue());
            System.out.println(rv);
            System.out.println(wr.getElementByRandomValue(rv).getKey() + " " + rv);
        }

        HashMap<String, Integer> keyCount = new HashMap<String, Integer>();
        keyCount.put("1", 0);
        keyCount.put("2", 0);
        keyCount.put("3", 0);
        keyCount.put("4", 0);
        for(int i = 0; i < 10000; i++){
            Integer rv = r.nextInt(wr.getMaxRandomValue());
            String key = wr.getElementByRandomValue(rv).getKey();
            keyCount.put(key, keyCount.get(key).intValue()+1);
        }

        System.out.println("");
    }

    private List<WeightElement> weightElements;

    public void initWeight(String[] keys, Integer[] weights){
        if(keys == null || weights == null || keys.length != weights.length){
            return;
        }

        weightElements = new ArrayList<WeightElement>();

        for(int i=0; i< keys.length; i++){
            weightElements.add(new WeightElement(keys[i], weights[i]));
        }

        rangeWeightElemnts();

        printRvs();
    }

    private void rangeWeightElemnts(){
        if(weightElements.size() == 0){
            return;
        }

        WeightElement ele0 = weightElements.get(0);
        ele0.setThresholdLow(0);
        ele0.setThresholdHigh(ele0.getWeight());

        for(int i = 1; i < weightElements.size(); i++){
            WeightElement curElement = weightElements.get(i);
            WeightElement preElement = weightElements.get(i - 1);

            curElement.setThresholdLow(preElement.getThresholdHigh());
            curElement.setThresholdHigh(curElement.getThresholdLow() + curElement.getWeight());
        }
    }

    public WeightElement getElementByRandomValue(Integer rv){
        //因为元素权重范围有序递增，所以这里可以改为二分查找

//        for(WeightElement e:weightElements){
//            if(rv >= e.getThresholdLow() && rv < e.getThresholdHigh()){
//                return e;
//            }
//        }
//    	return null;

        if(rv < 0 || rv > getMaxRandomValue()-1){
                return null;
        }

        //此时rv必然在0 - getMaxRandomValue()-1范围内，
        //也就是必然能够命中某一个值
        int start = 0, end = weightElements.size() - 1;
        int index = weightElements.size()/2;
        while(true){
            if(rv < weightElements.get(index).getThresholdLow()){
                end = index - 1;
            }else if(rv >= weightElements.get(index).getThresholdHigh()){
                start = index + 1;
            }else{
                return weightElements.get(index);
            }


            index = (start + end)/2;
        }
    }

    public Integer getMaxRandomValue(){
        if(weightElements == null || weightElements.size() == 0){
            return null;
        }

        return weightElements.get(weightElements.size() - 1).getThresholdHigh();
    }

    public void printRvs(){
        for(WeightElement e:weightElements){
            System.out.println(e.toString());
        }
    }

    static class WeightElement{
        /**
         * 元素标记
         */
        private String key;
        /**
         * 元素权重
         */
        private Integer weight;
        /**
         * 权重对应随机数范围低线
         */
        private Integer thresholdLow;
        /**
         * 权重对应随机数范围高线
         */
        private Integer thresholdHigh;

        public WeightElement(){
        }

        public WeightElement(Integer weight){
            this.key = weight.toString();
            this.weight = weight;
        }

        public WeightElement(String key, Integer weight){
            this.key = key;
            this.weight = weight;
        }

        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public Integer getWeight() {
            return weight;
        }
        public void setWeight(Integer weight) {
            this.weight = weight;
        }
        public Integer getThresholdLow() {
            return thresholdLow;
        }
        public void setThresholdLow(Integer thresholdLow) {
            this.thresholdLow = thresholdLow;
        }
        public Integer getThresholdHigh() {
            return thresholdHigh;
        }
        public void setThresholdHigh(Integer thresholdHigh) {
            this.thresholdHigh = thresholdHigh;
        }

        public String toString(){
            return "key:"+this.key + " weight:" + this.weight + " low:"+this.thresholdLow+" heigh:"+this.thresholdHigh;
        }
    }
}