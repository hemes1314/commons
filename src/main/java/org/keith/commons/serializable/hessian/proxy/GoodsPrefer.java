package org.keith.commons.serializable.hessian.proxy;

import java.io.Serializable;

/**
 * @author wubin
 * @date 2017/12/14
 **/
public class GoodsPrefer implements Serializable {

    private static final long serialVersionUID = -5261123984639892377L;

    private Integer seq;

    private String courierId;

    private String goodsType;

    private String prefer;

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getCourierId() {
        return this.courierId;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsType() {
        return this.goodsType;
    }

    public void setPrefer(String prefer) {
        this.prefer = prefer;
    }

    public String getPrefer() {
        return this.prefer;
    }

    @Override
    public String toString() {
        return "seq:"+this.seq+";courierId:"+this.courierId+";goodsType:"+this.goodsType+";prefer:"+this.prefer;
    }
}
