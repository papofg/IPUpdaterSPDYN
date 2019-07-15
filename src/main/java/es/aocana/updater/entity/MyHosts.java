package es.aocana.updater.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "myhosts" )
public class MyHosts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;
	
	@Column(name = "ipaddress", length = 255, nullable = false)
    private String ipAddress;
	
	@Column(name = "hostname", length = 255, nullable = false)
    private String hostname;
	
	@Column(name = "encodedlogindata", length = 255, nullable = false)
    private String encodedLoginData;
	
	@Transient
	private String username;
	@Transient
	private String password;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getEncodedLoginData() {
		return encodedLoginData;
	}

	public void setEncodedLoginData(String encodedLoginData) {
		this.encodedLoginData = encodedLoginData;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
