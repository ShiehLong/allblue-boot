package com.allblue.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * @Description: security配置类
 * @Author: Xone
 * @Date: 2018/12/11 15:28
 **/
@Configuration
@EnableWebSecurity
public class BlueSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BlueUserDetailsService blueUserDetailsService;

    @Autowired
    BlueFilterSecurityInterceptor blueFilterSecurityInterceptor;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置不拦截请求
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/fonts/**");
        web.ignoring().antMatchers("/img/**");
        web.ignoring().antMatchers("/js");
        web.ignoring().antMatchers("/view/login");
        web.ignoring().antMatchers("/view/register");
        web.ignoring().antMatchers("/view/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();

        http.authorizeRequests().anyRequest().fullyAuthenticated();
        //  定义当需要用户登录时候，转到的登录页面。
        http.formLogin().
                loginPage("/view/login").
                loginProcessingUrl("/user/login")
                .usernameParameter("username")
                .passwordParameter("password");

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/view/login");

        http.exceptionHandling().authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/view/login"))
                .accessDeniedPage("/view/error");

        //开启记住我功能
        http.rememberMe().rememberMeParameter("remember");
        // 关闭csrf
        http.csrf().disable();

        //session管理 session失效后跳转
        http.sessionManagement().invalidSessionUrl("/view/login");
        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/view/login");
        //在适当的地方加入
        http.addFilterAt(blueFilterSecurityInterceptor, FilterSecurityInterceptor.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // 自定义UserDetailsService,设置加密算法
        auth.userDetailsService(blueUserDetailsService)
                .passwordEncoder(passwordEncoder());
        //不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}