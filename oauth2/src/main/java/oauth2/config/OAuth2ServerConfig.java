package oauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class OAuth2ServerConfig {
	private static final String DEMO_RESOURCE_ID = "order";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
        	http.requestMatchers().antMatchers("/order/**")
            .and()
            .authorizeRequests()
            .antMatchers("/order/**").authenticated();

        }
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;
        @Autowired
        RedisConnectionFactory redisConnectionFactory;
        @Autowired
        private DataSource dataSource;
        
        @Bean
        public TokenStore tokenStore() {
            RedisTokenStore redis = new RedisTokenStore(redisConnectionFactory);
            return redis;
        }


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        	clients.jdbc(dataSource);
        /*	
//        password 方案一：明文存储，用于测试，不能用于生产
//        String finalSecret = "123456";
//        password 方案二：用 BCrypt 对密码编码
//        String finalSecret = new BCryptPasswordEncoder().encode("123456");
	        // password 方案三：支持多种编码，通过密码的前缀区分编码方式
	        String finalSecret = "{bcrypt}"+new BCryptPasswordEncoder().encode("123456");
	        //配置两个客户端,一个用于password认证一个用于client认证
	        clients.inMemory().withClient("client_1")
	                .resourceIds(DEMO_RESOURCE_ID)
	                .authorizedGrantTypes("client_credentials", "refresh_token")
	                .scopes("select")
	                .authorities("oauth2")
	                .secret(finalSecret)
	                .and().withClient("client_2")
	                .resourceIds(DEMO_RESOURCE_ID)
	                .authorizedGrantTypes("password", "refresh_token")
	                .scopes("select")
	                .authorities("oauth2")
	                .secret(finalSecret);*/
        	
        	/*clients.inMemory()
            .withClient("android")
            .scopes("xx") //此处的scopes是无用的，可以随意设置
            .secret("android")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
        .and()
            .withClient("webapp")
            .scopes("xx")
            .authorizedGrantTypes("implicit");*/
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints
            .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)//为了可以使用GET方式获取token
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
        }
        
        @Bean
        public ApprovalStore approvalStore() {
            TokenApprovalStore store = new TokenApprovalStore();
            store.setTokenStore(tokenStore());
            return store;
        }

    }

}
