package db;

import javax.persistence.EntityManager;
import java.util.List;

public class DbOperations {
    private static EntityManager entityManager;

    public DbOperations() {
        entityManager = ManagerFactory.getManagerFactory().createEntityManager();
    }

    public boolean insertUser(String username, String password) {
        List<ChessUsersEntity> users = entityManager.createNamedQuery("checkUsernameAlreadyExists").setParameter("username", username).getResultList();
        if (users.size() > 0) return false;
        ChessUsersEntity user = new ChessUsersEntity();
        Long id = (Long) entityManager.createNamedQuery("getMaxId").getSingleResult();
        user.setId(id);
        user.setUsername(username);
        user.setPass(password);
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean login(String username, String password) {

        List<ChessUsersEntity> users = entityManager.createNamedQuery("checkLoginCredentials").setParameter("password", password).setParameter("username", username).getResultList();
        return users.size() != 0;
    }
}
