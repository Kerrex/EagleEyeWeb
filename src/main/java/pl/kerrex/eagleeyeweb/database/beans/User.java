package pl.kerrex.eagleeyeweb.database.beans;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by tomek on 22.02.16.
 */
@Entity
@Table(name = "Uzytkownik")
public class User {
    private long idUser;
    private String user;
    private String password;
    private Set<UserRoles> roles;

    public User() {
    }

    public User(long idUser, String user, String password, Set<UserRoles> roles) {
        this.idUser = idUser;
        this.user = user;
        this.password = password;
        this.roles = roles;
    }

    @Column(name = "Uzytkownik", unique = true, nullable = false)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Column(name = "Haslo")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "UzytkownikRola", joinColumns = {
            @JoinColumn(name = "idUzytkownik", nullable = false)
    }, inverseJoinColumns = {@JoinColumn(name = "idRoleUzytkownikow", nullable = false)})
    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUzytkownik", nullable = false, unique = true)
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}
