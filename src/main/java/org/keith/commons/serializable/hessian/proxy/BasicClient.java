package org.keith.commons.serializable.hessian.proxy;

import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;

/**
 * @author wubin
 * @date 2017/12/14
 **/
public class BasicClient {

    public static void main(String[] args) throws MalformedURLException {
        String url = "http://127.0.0.1:8080/prefertest";
        HessianProxyFactory factory = new HessianProxyFactory();
        BasicAPI basic = (BasicAPI) factory.create(BasicAPI.class, url);
        System.out.println("Test:" + basic.getPrefers());
    }

}
