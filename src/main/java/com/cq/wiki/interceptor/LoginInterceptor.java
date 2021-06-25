package com.cq.wiki.interceptor;


import com.cq.wiki.annotation.PassToken;
import com.cq.wiki.exception.BusinessException;
import com.cq.wiki.exception.BusinessExceptionCode;
import com.cq.wiki.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/*
 * @Auther:cq
 * @Date: 2021/6/25
 * 身份验证拦截器
*/

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
        // 打印请求信息
        LOG.info("------------- LoginInterceptor 开始 -------------");
        long startTime = System.currentTimeMillis();

        request.setAttribute("requestStartTime", startTime);
        String token = request.getHeader("token");
        LOG.info("从请求头里获取token：{}",token);

        if(!(handle instanceof HandlerMethod)){
            LOG.info("未映射到方法，跳过token验证");
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handle;
        Method method=handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                LOG.info("有passtoken注解，跳过token验证");
                return true;
            }
        }
        if (token == null) {
            throw new BusinessException(BusinessExceptionCode.TOKEN_NULL);
        }

        try {
            if (TokenUtil.verifyToken(token)){
                LOG.info("token：{}验证通过",token);
            }
            else {
                throw new BusinessException(BusinessExceptionCode.TOKEN_VERIFY_ERROR);
            }
        }catch (Exception e){
            throw new BusinessException(BusinessExceptionCode.TOKEN_VERIFY_ERROR);
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("requestStartTime");
        LOG.info("------------- LoginInterceptor 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    }

}
