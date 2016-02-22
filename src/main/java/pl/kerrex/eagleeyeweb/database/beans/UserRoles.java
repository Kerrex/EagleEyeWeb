package pl.kerrex.eagleeyeweb.database.beans;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by tomek on 22.02.16.
 */
@Entity
@Table(name = "RoleUzytkownikow")
public class UserRoles {
    private long idRole;
    private String roleName;
    private Set<User> users;

    public UserRoles() {
    }

    public UserRoles(long idRole, String roleName, Set<User> users) {
        this.idRole = idRole;
        this.roleName = roleName;
        this.users = users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRoleUzytkownikow")
    public long getIdRole() {
        return idRole;
    }

    public void setIdRole(long idRole) {
        this.idRole = idRole;
    }

    @Column(name = "Nazwa_roli", nullable = false, unique = true)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
