package ru.kata.spring.boot_security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import ru.kata.spring.boot_security.demo.service.UserService;


/**
 * @Configuration - используется для классов, которые определяют bean-компоненты;
 * @EnableWebSecurity - маркерная аннотация для обеспечения работы аутентификации;
 * @Qualifier - используется для устранения случаев, когда необходимо автоматически подключить более одного bean-компонента одного типа;
 * @Autowired - отмечает конструктор, поле или метод как требующий автозаполнения инъекцией зависимости;
 * @Bean - идентификатор бина;
 * @Override - перед объявлением метода означает, что метод переопределяет объявление метода в базовом классе;
 * <p>
 * конфиги спринг-секьюрити
 */


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    public WebSecurityConfig(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    //конфиг спринг секьюрити: устанавливаем userDetailsService и passwordEncoder
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    // метод, который возвращает бин authenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    //настройка аутентификации
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    //конфиг спринг секьюрити:
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //стандартная форма логина
        http.formLogin()
                //устанавливаем в конфиги SuccessUserHandler, чтобы настроить логику при аутентификации
                .successHandler(new SuccessUserHandler())
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll();


        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //юрл при логауте
                .logoutSuccessUrl("/login")
                .and().csrf().disable();

        http
                .authorizeRequests()
                //разрешаем неавторизованным пользователям заходить только на /login
                .antMatchers("/login").anonymous()
                //разрешаем пользователям с роль ADMIN заходить на юрл, которые начинаются на admin
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                //разрешаем пользователям с ролью USER или ADMIN заходить на юрл, которые начинаются на user
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .and().csrf().disable();
    }

    @Autowired
    public void setUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}

