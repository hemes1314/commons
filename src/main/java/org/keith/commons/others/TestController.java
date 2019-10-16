package org.keith.commons.others;

//import com.datacanvas.aps.model.repository.ModelRepositoryAPIClientTest;
//import com.datacanvas.aps.pipes.api.common.RestResult;
//import com.datacanvas.aps.pipes.api.common.ResultMaker;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Enumeration;
//
//@RestController
//@RequestMapping("aps/pipes/test")
public class TestController {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @GetMapping("/model/repo/rest")
//    public String restTest() {
//        return new ModelRepositoryAPIClientTest().putTestWithBody();
//    }
//
//    @PutMapping("/rest/put")
//    public RestResult putTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        System.out.println("request uri:"+request.getRequestURI());
//        System.out.println("request url:"+request.getRequestURL());
//        System.out.println("request queryString:"+request.getQueryString());
//
//        System.out.println("request parameters:");
//        Enumeration<String> parameterNames = request.getParameterNames();
//        while(parameterNames.hasMoreElements()) {
//            String name = parameterNames.nextElement();
//            System.out.println("param key:"+name+" -> "+request.getParameter(name));
//        }
//
//        System.out.println("request body:");
//        StringBuilder sb = new StringBuilder();
//        InputStream is = request.getInputStream();
//        byte[] b = new byte[4096];
//        for(int n; (n = is.read(b)) != -1;) {
//            sb.append(new String(b,0,n));
//        }
//        System.out.println(sb.toString());
//        return ResultMaker.success(Boolean.TRUE);
//    }
}