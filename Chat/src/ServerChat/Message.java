package ServerChat;

import java.io.Serializable;
import java.util.Calendar;

public class Message implements Serializable, IMessage{
	
	private static final long serialVersionUID = 1L;
	
	private String from;
	private String msg;
	private Calendar date;	
	
	@Override
	public void setMessage(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

	@Override
	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public Calendar getDate() {
		return date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
