package org.keith.commons.serializable.hessian.proxy.server;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wubin
 * @date 2017/12/14
 **/
public class BasicAPImpl implements BasicAPI {

    @Override
    public List<GoodsPrefer> getPrefers() {
        List<GoodsPrefer> list = new ArrayList<>();
        GoodsPrefer p1 = new GoodsPrefer();
        p1.setSeq(1);
        p1.setCourierId("31231");
        p1.setGoodsType("type1");
        p1.setPrefer("prefer001");
        list.add(p1);
        p1 = new GoodsPrefer();
        p1.setSeq(2);
        p1.setCourierId("31232");
        p1.setGoodsType("type2");
        p1.setPrefer("prefer002");
        list.add(p1);
        p1 = new GoodsPrefer();
        p1.setSeq(3);
        p1.setCourierId("31233");
        p1.setGoodsType("type3");
        p1.setPrefer("prefer003");
        list.add(p1);
        return list;
    }
}
