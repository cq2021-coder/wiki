# JAVA过滤器和拦截器的区别
## 什么是拦截器
```text
在AOP中用于在某个方法或字段被访问之前，进行拦截然后再之前货之后加入某些操作。拦截是AOP的一种实现策略。
拦截器是动态拦截Action调用的对象。它提供了一种机制可以使开发者定义一个action前后执行的代码，也可以在一个action执行阻止其执行，同时也是提供了一种可以提取action可重用部分的方式。
实现原理：大部分是通过代理的方式来调用的。
```
### 自定义拦截器步骤：
```text
自定义一个实现了Interceptor接口的类，或者继承抽象类AbstractInterceptor。
在配置文件中注册定义的拦截器。
在需要使用Action中引入定义的拦截器，为了方便也可以把拦截器定义为默认的拦截器。所有的Action都会被这个拦截器拦截
```
## 什么是过滤器
```text
过滤器是一个程序，他先于与之相关的servlet或jsp也可以运行在服务器上。过滤器可附加到一个或多个servlet或jsp页面上，并且可以检查进入这些资源的请求信息。
以常规的方式调用资源
利用修改过的请求信息调用资源
调用资源，但在发送响应到客户机前对其进行修改
阻止该资源调用，代之以转到其他的资源，返回一个特定的状态代码或生成替换输出。
过滤器的基本原理：在Servlet作为过滤器使用时，他可以对客户的请求进行处理。处理完成后，他会交给下一个过滤器处理，这样，客户的请求在过滤器链中逐个处理，直到请求发送到目标为止。
```
## 拦截器与过滤器的区别
```text
拦截器是基于java的反射机制的，而过滤器是基本函数回调。
拦截器不依赖于servlet容器，过滤器依赖于servlet容器
拦截器只能对action请求起作用，过滤器可以对几乎所有的请求起作用
拦截器可以访问action上下文、值栈里的对象，而过滤器不能访问
在action的生命周期中，拦截器可以被多次调用，而过滤器只能在容器初始化时被调用过一次。
```
1. 使用范围和规范不同
filter是servlet规范规定的,只能用在web程序中.
拦截器即可以用在web程序中, 也可以用于application, swing程序中, 是Spring容器内的, 是Spring框架支持的
2. 触发时机不同
顺序: Filter-->Servlet-->Interceptor-->Controller
```text
过滤器是在请求进入容器后，但请求进入servlet之前进行预处理的。请求结束返回也是，是在servlet处理完后，返回给前端之前过滤器处理。
拦截器是方法到达Controller层之前生效的
```
3. 过滤器的实现基于回调函数。而拦截器（代理模式）的实现基于反射，代理分静态代理和动态代理，动态代理是拦截器的简单实现。
何时使用拦截器？何时使用过滤器？
```text
    如果是非spring项目，那么拦截器不能用，只能使用过滤器。
    如果是处理controller前后，既可以使用拦截器也可以使用过滤器。
    如果是处理dispaterServlet前后，只能使用过滤器。
```
4. 在action的生命周期中，拦截器可以多次被调用，而过滤器只能在容器初始化时被调用一次。
5. 拦截器可以访问action上下文、值栈里的对象，而过滤器不能访问。
6. 拦截器只能对action请求起作用，而过滤器则可以对几乎所有的请求起作用。
7. 拦截器可以获取IOC容器中的各个bean，而过滤器就不行，在拦截器里注入一个service，可以调用业务逻辑。
```text
过滤器（Filter）：所谓过滤器顾名思义是用来过滤的，Java的过滤器能够为我们提供系统级别的过滤，也就是说，能过滤所有的web请求，这一点，是拦截器无法做到的。在Java Web中，你传入request,response提前过滤掉一些信息，或者提前设置一些参数，然后再传入servlet或者struts的action进行业务逻辑，比如过滤掉非法url（不是login.do的地址请求，如果用户没有登陆都过滤掉）,或者在传入servlet或者struts的action前统一设置字符集，或者去除掉一些非法字符（聊天室经常用到的，一些骂人的话）。filter 流程是线性的，url传来之后，检查之后，可保持原来的流程继续向下执行，被下一个filter, servlet接收。

监听器（Listener）：Java的监听器，也是系统级别的监听。监听器随web应用的启动而启动。Java的监听器在c/s模式里面经常用到，它会对特定的事件产生产生一个处理。监听在很多模式下用到，比如说观察者模式，就是一个使用监听器来实现的，在比如统计网站的在线人数。又比如struts2可以用监听来启动。Servlet监听器用于监听一些重要事件的发生，监听器对象可以在事情发生前、发生后可以做一些必要的处理。

拦截器（Interceptor）：java里的拦截器提供的是非系统级别的拦截，也就是说，就覆盖面来说，拦截器不如过滤器强大，但是更有针对性。Java中的拦截器是基于Java反射机制实现的，更准确的划分，应该是基于JDK实现的动态代理。它依赖于具体的接口，在运行期间动态生成字节码。拦截器是动态拦截Action调用的对象，它提供了一种机制可以使开发者在一个Action执行的前后执行一段代码，也可以在一个Action执行前阻止其执行，同时也提供了一种可以提取Action中可重用部分代码的方式。在AOP中，拦截器用于在某个方法或者字段被访问之前，进行拦截然后再之前或者之后加入某些操作。java的拦截器主要是用在插件上，扩展件上比如 Hibernate Spring Struts2等，有点类似面向切片的技术，在用之前先要在配置文件即xml，文件里声明一段的那个东西。
```
# SpringBoot使用过滤器
## 封装Filter

```java
package com.theeternity.common.baseFilter;

import javax.servlet.Filter;

/**
 * @program: ApiBoot
 * @description: 封装Filter
 * @author: TheEternity Zhang
 * @create: 2019-02-17 13:08
 */
public interface MappingFilter extends Filter {
    String[] addUrlPatterns();

    int order();
}

```

## 自定义Filter

```java
package com.theeternity.beans.filterConfig;

import com.theeternity.common.baseFilter.MappingFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import java.io.IOException;

/**
 * @program: ApiBoot
 * @description: 权限过滤器
 * @author: TheEternity Zhang
 * @create: 2019-02-17 13:14
 */
public class AuthFilter implements MappingFilter {

    @Override
    public String[] addUrlPatterns() {
        return new String[]{"/*"};
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

}
```

## 注册过滤器

```java
package com.theeternity.beans.filterConfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @program: ApiBoot
 * @description: 注册过滤器
 * @author: TheEternity Zhang
 * @create: 2019-02-17 13:10
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        AuthFilter authFilter=new AuthFilter();
        registration.setFilter(authFilter);
        registration.addUrlPatterns(authFilter.addUrlPatterns());
        registration.setOrder(authFilter.order());
        registration.setName("AuthFilter");
        return registration;
    }
}
```

# SpringBoot使用拦截器

## 封装Interceptor

```java
package com.theeternity.common.baseInterceptor;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @program: ApiBoot
 * @description: 封装Interceptor
 * @author: TheEternity Zhang
 * @create: 2019-02-15 17:49
 */
public interface MappingInterceptor extends HandlerInterceptor {
    String[] addPathPatterns();

    String[] excludePathPatterns();

    int order();
}
```

## 自定义Interceptor

```java
package com.theeternity.beans.interceptorConfig;

import com.theeternity.common.baseInterceptor.MappingInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: BoxApi
 * @description: 跨域拦截器
 * @author: tonyzhang
 * @create: 2018-12-21 14:44
 */
@Component
public class CrossOriginInterceptor implements MappingInterceptor {

    @Override
    public String[] addPathPatterns() {
        return new String[]{"/**"};
    }

    @Override
    public String[] excludePathPatterns() {
        return new String[0];
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("允许的头信息"+request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        //是否允许浏览器携带用户身份信息（cookie）
        response.setHeader("Access-Control-Allow-Credentials","true");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) throws Exception {

    }
}
```

## 注册Interceptor

```java
package com.theeternity.beans.interceptorConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: ApiBoot
 * @description: 拦截器注册
 * @author: TheEternity Zhang
 * @create: 2019-02-15 17:55
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CrossOriginInterceptor crossOriginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
registry.addInterceptor(crossOriginInterceptor).addPathPatterns(crossOriginInterceptor.addPathPatterns());

    }
}
```

