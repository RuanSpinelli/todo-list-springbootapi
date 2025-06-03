package com.todo.config;

import com.todo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Serve para configurar a autenticação
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
    "Hey Spring, quando alguém tentar logar, usa o meu UsuarioService pra buscar o usuário no banco.
     E compara a senha usando BCrypt"
     */
        auth.userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder);
    }


    // Definindo a segurança da aplicação
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Libera acesso sem login para certas rotas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2-console/**"
                        ,"/usuarios/cadastrar")
                        .permitAll()
                        .requestMatchers("usuarios/listar").hasRole("ADMIN")
                        //"Qualquer outra rota, deve estar autenticado para entrar"
                        .anyRequest().authenticated()
                )
                // Proteção contra requisições falsas. É ignorado nas rotas abaixo
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/h2-console/**"
                                ,"/usuarios/cadastrar"
                        )
                )
                /*O console H2 roda dentro de um iframe. Por padrão, o Spring bloqueia iframes por segurança,
                então aqui você desativa isso só pro H2 funcionar.*/
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )

                /*Formulário para login, aonde ir após o sucesso, e voltar para login caso falhe.
                * Todos podem entrar nesses endpoints*/

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // Condigura o output
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )


                .httpBasic(httpBasic -> {});

        return http.build();
    }

    // Gerenciador de autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }




}
