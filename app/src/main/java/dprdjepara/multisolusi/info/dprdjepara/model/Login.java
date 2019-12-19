package dprdjepara.multisolusi.info.dprdjepara.model;

public class Login {
	private Integer id;
	private String username;
	private String password;
	private String hp;
	private String role;
	private String barisperhalaman;
	
	
	public Login() {
		super();
	}

	public Login(String username, String password, String hp,String role, String barisperhalaman) {
		super();
		this.username = username;
		this.password = password;
		this.hp = hp;
		this.role = role;
		this.barisperhalaman = barisperhalaman;

	}

	
	public String getUsername() {return username; }
	public void setUser(String username) {this.username = username;}

	public String getPassword() {return password; }
	public void setPass(String password) {this.password = password;}

	public String getRole() {return role;}
	public void setLevel(String role) {this.role = role;}
	
	public String getHp() {return hp; }
	public void setHp(String hp) {this.hp = hp;}

	public String getBarisperhalaman() {return barisperhalaman;}
	public void setBarisperhalaman(String barisperhalaman) {this.barisperhalaman = barisperhalaman;}
	
}