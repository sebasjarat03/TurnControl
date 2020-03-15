package model;
import java.time.*;

public class SystemTime {
	private LocalDateTime inDateTime;
	
	public SystemTime() {
		this.inDateTime = LocalDateTime.now();
	}
	
	public String getTime() {
		String msg="" + inDateTime.toLocalDate() + " " + inDateTime.toLocalTime();
		return msg;
	
	}
	
	public LocalDateTime getTime2() {
		return this.inDateTime;
	}
	
	public void setTime(LocalDateTime newT) {
		this.inDateTime = newT;
	}
	
	
}
