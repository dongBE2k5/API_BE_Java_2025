package tdc.fit.bookingHotel.entity.Response;

public class UserReponse {
	private String token;
	private Long id;
	private String status;
	
	
	
	
	public UserReponse(String token, Long id, String status) {
		super();
		this.token = token;
		this.id = id;
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
