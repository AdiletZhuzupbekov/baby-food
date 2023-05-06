package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.User;
import kg.mara.babyfood.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByRoles(Role driver);
}
