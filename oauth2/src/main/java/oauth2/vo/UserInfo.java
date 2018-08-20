package oauth2.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Entity
@Table(name = "t_user", schema = "db_oauth2")
public class UserInfo implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	// 用户姓名
	@NotEmpty
	private String name;

	// 账号
	@NotEmpty
	@Column(unique = true, nullable = false)
	private String login;

	// 密码
	@NotEmpty
	private String password;

	// 性别（1：男；2：女）
	@Column(name = "gender", nullable = false, columnDefinition = "int(1) default '0'")
	private int gender = 0;

	// 手机号
	@Column(name = "mobile", nullable = false, columnDefinition = "varchar(12) default '000'")
	private String mobile;

	// 年龄
	@Column(name = "age", nullable = false, columnDefinition = "int(3) default '0'")
	private int age;

	// 职务
	@Column(name = "post", nullable = false, columnDefinition = "varchar(255) default '?'")
	private String post;

	// 所属派出所ID
	@Column(name = "police_station_id", nullable = false, columnDefinition = "int(25) default '-1'")
	private Long policeStationId;

	@Transient
	// 所在单位及祖先单位的名字，从根开始
	private List<String> policeStationNames;

	// 所在单位名字
	@Transient
	private String policeStationName;


	// 摄像头权限（cameraId,cameraId,cameraId）
	@Column(name = "camera_rights", nullable = false, columnDefinition = "varchar(255) default ''")
	private String cameraRights;
	// 关联人脸
	@Column(name = "face_id", nullable = false, columnDefinition = "bigint(20) default -1")
	@JsonSerialize(using = ToStringSerializer.class)
	private long faceId = -1;

	// 重复登录判断 v1.1.2
	@JsonIgnore
	@Transient
	private boolean haslogin;

	// v1.2.0用户访问时限功能
	// 访问限制的开始时间
	@Column(name = "starttime", nullable = false, columnDefinition = "datetime default '1970-01-01 00:00:00'")
	private Date startTime;
	// 访问限制的节航速时间
	@Column(name = "endtime", nullable = false, columnDefinition = "datetime default '2050-01-01 00:00:00'")
	private Date endTime;

	@Transient
	private String areaIds;
	@Transient
	private String cameraIds;
	@Transient
	private Boolean opened;
	// 功能权限id列表
	@Transient
	private String resIds;
	//角色类型名
	@Transient
	private String roleTypeName;

    //全区域搜索特殊人群标志 0 非特殊人员 1 普通全区域搜索人员 2特殊全区域搜索人员（联络员）
	private int specialSign;
	
	private String cTypeIds;//  摄像头类型过滤 为空时不过滤
	
	// 特殊账号（0：无特殊；1：红名单例外）
	private Integer specialUser;
	// 用户提醒方式（1：气泡，2：数字，3：气泡加数字）配置
	private int remindingType;

	// 请求方的应用系统或客户端名称
    private String requester;

	@Transient
	private Map<String, String> zone;

	

	public Boolean getOpened() {
		return opened;
	}

	public void setOpened(Boolean opened) {
	    if (null == opened) {
	        this.opened = false;
	    } else {
	        this.opened = opened;
	    }
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getCameraIds() {
		return cameraIds;
	}

	public void setCameraIds(String cameraIds) {
		this.cameraIds = cameraIds;
	}

	public Date getStartTime() {
		return startTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}



	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isHaslogin() {
		return haslogin;
	}

	public void setHaslogin(boolean haslogin) {
		this.haslogin = haslogin;
	}

	// 功能权限
	// @JsonIgnore
	// @ManyToMany(fetch = FetchType.EAGER)
	// @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name =
	// "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
	// private Set<RoleInfo> roles = new HashSet<RoleInfo>();
	@Column(name = "role_ids", nullable = false, columnDefinition = "text(65534)")
	private String roleIds = "";

	public UserInfo() {
	}

	public int getSpecialSign() {
        return specialSign;
    }

    public void setSpecialSign(int specialSign) {
        this.specialSign = specialSign;
    }
	public UserInfo(UserInfo userInfo) {
		super();
		this.id = userInfo.getId();
		this.name = userInfo.getName();
		this.login = userInfo.getLogin();
		this.password = userInfo.getPassword();
		this.gender = userInfo.getGender();
		this.mobile = userInfo.getMobile();
		this.age = userInfo.getAge();
		this.post = userInfo.getPost();
		this.policeStationId = userInfo.getPoliceStationId();
		this.cameraRights = userInfo.getCameraRights();

		this.roleIds = userInfo.getRoleIds();
		this.faceId = userInfo.getFaceId();
		this.startTime = userInfo.getStartTime();
		this.endTime = userInfo.getEndTime();
		this.specialSign = userInfo.getSpecialSign();
		this.cTypeIds = userInfo.getcTypeIds();
		this.specialUser = userInfo.getSpecialUser();
		this.requester = userInfo.getRequester();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// public Set<RoleInfo> getRoles() {
	// return roles;
	// }
	//
	// public void setRoles(Set<RoleInfo> roles) {
	// this.roles = roles;
	// }

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Long getPoliceStationId() {
		return policeStationId;
	}

	public void setPoliceStationId(Long policeStationId) {
		this.policeStationId = policeStationId;
	}

	public String getCameraRights() {
		return cameraRights;
	}

	public void setCameraRights(String cameraRights) {
		this.cameraRights = cameraRights;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getcTypeIds() {
        return cTypeIds;
    }

    public void setcTypeIds(String cTypeIds) {
        this.cTypeIds = cTypeIds;
    }

    @Override
    public String toString() {
        return "UserInfo [id=" + id + ", name=" + name + ", login=" + login + ", password=" + password + ", gender=" + gender + ", mobile=" + mobile + ", age="
                + age + ", post=" + post + ", policeStationId=" + policeStationId + ", policeStationNames=" + policeStationNames + ", policeStationName="
                + policeStationName + ", cameraRights=" + cameraRights + ", faceId=" + faceId + ", haslogin=" + haslogin + ", startTime=" + startTime
                + ", endTime=" + endTime + ", areaIds=" + areaIds + ", cameraIds=" + cameraIds + ", opened=" + opened + ", resIds=" + resIds
                + ", roleTypeName=" + roleTypeName + ", specialSign=" + specialSign + ", cTypeIds=" + cTypeIds + ", specialUser=" + specialUser
                + ", requester=" + requester + ", zone=" + zone + ", roleIds=" + roleIds + "]";
    }


	public long getFaceId() {
		return faceId;
	}

	public void setFaceId(long faceId) {
		this.faceId = faceId;
	}


	@JsonIgnore
	public Long getRoleId() {
		String roleIds = this.getRoleIds();
		Long roleId = 0L;
		if (roleIds == null || roleIds.equals("")) {
			return roleId;
		}

		if (!roleIds.contains(",")) {
			roleId = Long.parseLong(roleIds);
		}

		return roleId;
	}

	public String getResIds() {
		return resIds;
	}

	public void setResIds(String resIds) {
		this.resIds = resIds;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

    public Integer getSpecialUser() {
        if(specialUser==null){
            this.specialUser = 0;
        }
        return specialUser;               
    }

    public void setSpecialUser(Integer specialUser) {
       if(specialUser==null){
           this.specialUser = 0;
       }else{
        this.specialUser = specialUser;
       }
    }

    public Map<String, String> getZone() {
		return zone;
	}

	public void setZone(Map<String, String> zone) {
		this.zone = zone;
	}

	@Override
	public UserInfo clone() {
		try {
			return (UserInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public List<String> getPoliceStationNames() {
		return policeStationNames;
	}

	public void setPoliceStationNames(List<String> policeStationNames) {
		this.policeStationNames = policeStationNames;
	}

	public String getPoliceStationName() {
		return policeStationName;
	}

	public void setPoliceStationName(String policeStationName) {
		this.policeStationName = policeStationName;
	}

    public int getRemindingType() {
        return remindingType;
    }

    public void setRemindingType(int remindingType) {
        this.remindingType = remindingType;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }


}

