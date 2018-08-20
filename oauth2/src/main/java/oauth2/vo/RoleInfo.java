package oauth2.vo;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "t_role", schema = "db_oauth2")
public class RoleInfo implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;
    //    @ElementCollection(targetClass = String.class)
//    private Set<String> resourceIds = new HashSet<String>();
    private String resIds;//resource IDs.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String name;
//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//    private Set<UserInfo> users = new HashSet<UserInfo>();

    private String cnName;
    
    private String modules;

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    @Override
    public String toString() {
        return "RoleInfo,id:" + this.getId() + ",name:" + this.getName() + ",cnName:" + this.getCnName() + ",authority:" + this.getAuthority() + ",resIds:" + this.getResIds();
    }

    public String getResIds() {
        return resIds;
    }

    public void setResIds(String resIds) {
        this.resIds = resIds;
    }

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}
}
