package com.spring.free.config;

import com.spring.free.utils.shirosession.CacheSessionDAOImpl;
import com.spring.free.util.constraints.Global;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置类
 * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 * @author Memory
 * @date 2017/6/16
 * @version 1.0
 * @Inc 恒谱科技
 */
@Configuration
public class ShiroConfiguration {

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     * 是个factorybean，为了生成ShiroFilter。它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        filterChainDefinitionMap.put("/admin/test/getU", "anon");
        filterChainDefinitionMap.put("/admin/test/insert", "anon");

        //<!-- 过滤链定义，从上向下顺序执行，一般将 *放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问; user:配置记住我或认证通过可以访问-->
        filterChainDefinitionMap.put("/", "user");
        filterChainDefinitionMap.put("/index", "user");
        filterChainDefinitionMap.put("/admin/front/member/registSimple", "anon");
        filterChainDefinitionMap.put("/admin/manage/member/exportMemberFile", "anon");
        filterChainDefinitionMap.put("/admin/manage/order/exportOrderFile", "anon");
        filterChainDefinitionMap.put(Global.ADMIN_PATH + "/**", "user");
        filterChainDefinitionMap.put("/**", "anon");


        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthenticationFilter authFile(){
        return new AuthenticationFilter();
    }

    /**
     * 这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
            * @return
            */
    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * 权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setSessionDAO(sessionDAO());
        manager.setSessionIdCookie(simpleCookie());
        manager.setSessionIdCookieEnabled(true);
        manager.setSessionValidationSchedulerEnabled(true);
        manager.setSessionValidationInterval(120000);
        manager.setGlobalSessionTimeout(1800000);
        return manager;
    }

    public SimpleCookie simpleCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("HP_BS");
        return cookie;
    }

    @Bean
    public SessionDAO sessionDAO(){
        SessionDAO sessionDAO =  new CacheSessionDAOImpl();
        return sessionDAO;
    }

    /**
     * 身份认证realm;
     * (这个需要自己写，账号密码校验；权限等)
     * 这是个自定义的认证类，继承自AuthorizingRealm，负责用户的认证和权限的处理，可以参考JdbcRealm的实现。
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public AuthenticationRealm myShiroRealm(){
        AuthenticationRealm myShiroRealm = new AuthenticationRealm();
        return myShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * 这个类是为了对密码进行编码的，防止密码在数据库里明码保存，当然在登陆认证的生活，这个类也负责对form里输入的密码进行编码。
     * @return
     */
    /*@Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }*/

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     *  shiro里实现的Advisor类，内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     *  老实说，这里注入securityManager，我不知道有啥用，从source上看不出它在什么地方会被调用。
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor aasa  = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa ;
    }

}
