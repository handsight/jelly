package com.mall.jelly.config;

import com.mall.jelly.entity.Permission;
import com.mall.jelly.handler.MyAuthenticationFailureHandler;
import com.mall.jelly.handler.MyAuthenticationSuccessHandler;
import com.mall.jelly.mapper.PermissionMapper;
import com.mall.jelly.security.MyUserDetailsService;
import com.mall.jelly.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationFailureHandler failureHandler;
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    @Autowired
    private MyUserDetailsService myUserDetailsService;


    @Autowired
    private PermissionMapper permissionMapper;

    // 用户认证信息
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 设置admin账号和密码 具有crud权限
//        auth.inMemoryAuthentication().withUser("admin").password("123456").authorities("showOrder","addOrder","updateOrder","deleteOrder");
//        // 添加 useradd账号 只有查询和添加权限
//        auth.inMemoryAuthentication().withUser("user").password("123456")
//                .authorities("showOrder","addOrder");

        //数据库查询用户拥有的权限
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new PasswordEncoder() {
            // 加密的密码与数据库密码进行比对CharSequence rawPassword 表单字段 encodedPassword
            // 数据库加密字段
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                System.out.println("rawPassword:" + rawPassword + ",encodedPassword:" + encodedPassword);
                // 返回true 表示认证成功 返回fasle 认证失败
                return MD5Util.encode((String) rawPassword).equals(encodedPassword);
            }
            // 对表单密码进行加密
            @Override
            public String encode(CharSequence rawPassword) {
                System.out.println("rawPassword:" + rawPassword);
                return MD5Util.encode((String) rawPassword);
            }
        });
    }

    // 配置HttpSecurity 拦截资源
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                // 登录页面不需要认证
//                .antMatchers("/login").permitAll()
//                //拦截/showOrder路径  必须具有showOrder权限
//                .antMatchers("/showOrder").hasAnyAuthority("showOrder")
//                .antMatchers("/addOrder").hasAnyAuthority("addOrder")
//                .antMatchers("/updateOrder").hasAnyAuthority("updateOrder")
//                .antMatchers("/deleteOrder").hasAnyAuthority("deleteOrder")
//                //以表单模式进行认证
//                .antMatchers("/**").fullyAuthenticated().and().formLogin()
//                //自定义登录界面
//                .loginPage("/login").
//                //登录成功处理，登录失败处理
//                successHandler(successHandler).failureHandler(failureHandler)
//                //csrf关闭
//                .and().csrf().disable();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorize = http
                .authorizeRequests();
        // 1.读取数据库权限列表 设置权限
        List<Permission> listPermission = permissionMapper.findAllPermission();
        for (Permission permission : listPermission) {
            //拦截/showOrder路径  必须具有showOrder权限
            authorize.antMatchers(permission.getUrl()).hasAnyAuthority(permission.getPermTag());
        }
       // 放行登录页面login请求
        authorize.antMatchers("/login").permitAll()
                //以表单模式进行认证
                .antMatchers("/**").fullyAuthenticated().and().formLogin()
                //自定义登录界面
                .loginPage("/login")
                //登录成功处理，登录失败处理
                .successHandler(successHandler).failureHandler(failureHandler)
                .and().csrf().disable();

    }
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
