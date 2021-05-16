package db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CHESS_USERS", schema = "STUDENT")
@NamedQueries({
        @NamedQuery(name = "getMaxId", query = "SELECT max(id)+1 FROM ChessUsersEntity m"),
        @NamedQuery(name = "checkLoginCredentials", query = "SELECT user from ChessUsersEntity user WHERE user.username =: username AND user.pass=: password"),
        @NamedQuery(name = "checkUsernameAlreadyExists", query = "SELECT user from ChessUsersEntity user WHERE user.username =: username")
})
public class ChessUsersEntity {
    private Long id;
    private String username;
    private String pass;

    @Id
//    @GeneratedValue
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "PASS")
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessUsersEntity that = (ChessUsersEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(pass, that.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, pass);
    }
}
