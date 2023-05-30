package pl.nanocoder.testserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nanocoder.testserver.persistence.dao.LoginRepository;
import pl.nanocoder.testserver.persistence.model.Login;
import pl.nanocoder.testserver.persistence.model.Permission;
import pl.nanocoder.testserver.persistence.model.Role;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Login login = loginRepository.findByLogin(s);
        if(login == null || login.getPerson() == null  || login.getPerson().isDeleted())
            throw new UsernameNotFoundException("No user found with name: " + s);

        return new User(login.getLogin(),
                login.getPassword(),
                true,
                true,
                true,
                true,
                getGrantedAuthorities(getPermissions(login.getRole())));
    }

    //UTILS

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> permissions)
    {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(String permission : permissions)
            grantedAuthorities.add(new SimpleGrantedAuthority(permission));
        return grantedAuthorities;
    }

    public List<String> getPermissions(final Role role)
    {
        final List<String> permissions = new ArrayList<>();

        //permissions.add(role.getName());

        for(Permission permission : role.getPermissions())
            permissions.add(permission.getPermission());
        return permissions;
    }
}
