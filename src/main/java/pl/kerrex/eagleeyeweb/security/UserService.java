package pl.kerrex.eagleeyeweb.security;


import org.hibernate.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.kerrex.eagleeyeweb.database.HibernateUtil;
import pl.kerrex.eagleeyeweb.database.beans.User;
import pl.kerrex.eagleeyeweb.database.beans.UserRoles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tomek on 22.02.16.
 */

public class UserService implements UserDetailsService {

    private User getByUserName(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = session.createQuery("from User where user = :username")
                .setParameter("username", username)
                .list();
        if (users.isEmpty()) return null;
        else return users.get(0);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getByUserName(s);
        List<GrantedAuthority> authorities = getUserRoles(user.getRoles());
        return new org.springframework.security.core.userdetails.User(
                user.getUser(), user.getPassword(), true, true, true, true, authorities);

    }

    private List<GrantedAuthority> getUserRoles(Set<UserRoles> roles) {
        Set<GrantedAuthority> auths = new HashSet<>();
        roles.forEach((role) -> auths.add(new SimpleGrantedAuthority(role.getRoleName())));
        return new ArrayList<>(auths);
    }
}
