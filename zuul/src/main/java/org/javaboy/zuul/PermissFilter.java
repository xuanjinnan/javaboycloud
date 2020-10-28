package org.javaboy.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PermissFilter extends ZuulFilter {
    /**
     * 过滤起的类型，权限判断一般为 pre
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的优先级
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //比如判断是否转发，RequestContext.getCurrentContext()通过获取请求上下文
        //RequestContext cxt = RequestContext.getCurrentContext();
        //HttpServletRequest request = cxt.getRequest();
        return true;
    }

    /**
     * 核心的过滤逻辑写在这里
     *
     * @return 这个方法虽然有返回值，但是这个返回值目前无所谓
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //如果不满足在这个方法里处理，如果满足什么都不用做，会自动往下走
        RequestContext cxt = RequestContext.getCurrentContext();
        HttpServletRequest request = cxt.getRequest();//获取当前请求
        System.out.println(request.getRequestURI());
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!"javaboy".equals(username) || !"123".equals(password)) {
            //如果请求条件不满足，直接再这里给出响应
            cxt.setSendZuulResponse(false);//请求到此为止
            cxt.setResponseStatusCode(401);//响应码
            cxt.addZuulResponseHeader("content-type", "text/html;charset=utf-8");//响应头
            cxt.setResponseBody("非法访问");//响应体
        }
        return null;
    }
}
