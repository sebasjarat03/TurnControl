package model;
import java.io.Serializable;
import java.time.*;

public class SystemTime implements Serializable{
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
