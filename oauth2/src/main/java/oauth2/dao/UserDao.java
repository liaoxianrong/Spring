package oauth2.dao;

import java.util.List;

import oauth2.vo.UserInfo;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserInfo, Long> {

    UserInfo findByLogin(String login);
    
    List<UserInfo> findByName(String name);

	List<UserInfo> findByPoliceStationId(long l);
	
    long countByPoliceStationId(long l);

}
