package oauth2.service;


import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Resource;

import oauth2.dao.RoleDao;
import oauth2.dao.UserDao;
import oauth2.vo.RoleInfo;
import oauth2.vo.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
//	public static boolean haslogin = false;
    @Autowired
    private UserDao userRepository;
    
   /* @Autowired
    private OauthAccessTokenDao oauthAccessTokenDao;*/


    @Autowired
    public UserDetailsServiceImpl(RoleDao roleRepository) {
        UserRepositoryOAuthUserDetails.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByLogin(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException(String.format("OAuth UserInfo %s does not exist!", username));
        }
        return new UserRepositoryOAuthUserDetails(userInfo);
    }

    private final static class UserRepositoryOAuthUserDetails extends UserInfo implements UserDetails {

        private static final long serialVersionUID = 1L;

        public static RoleDao roleRepository;

        private UserRepositoryOAuthUserDetails(UserInfo userInfo) {
            super(userInfo);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            String[] roleIds = this.getRoleIds().split(",");
            Collection<RoleInfo> authorities = new HashSet<RoleInfo>();
            for (int i = 0; i < roleIds.length; i++) {
                if ("".equals(roleIds[i])) {
                    continue;
                }
                long roleId = Long.valueOf(roleIds[i]);
                RoleInfo roleInfo = roleRepository.findById(roleId).get();
                authorities.add(roleInfo);
            }
//            return getRoles();
            return authorities;
        }

        @Override
        public String getUsername() {
            return getLogin();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

    }

}
