package oauth2.dao;

import java.util.List;

import oauth2.vo.RoleInfo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleDao extends CrudRepository<RoleInfo, Long> {
    @Query(value = "select r.* from "+ "db_oauth" + "." +"t_role" + " r, "
            + "db_oauth" + "." +"t_user"
            + " u where u.id = :userId and find_in_set(r.id, u.role_ids)", nativeQuery = true)
    List<RoleInfo> queryRoleInfoByUserId(@Param("userId") long userId);
}
