

import java.text.DecimalFormat;
import java.text.MessageFormat;

import com.sendsafely.ProgressInterface;

public class ProgressCallback implements ProgressInterface {

	@Override
	public void updateProgress(String fileId, double progress) {
		System.out.println(MessageFormat.format("{0,number,#.##%}", progress));
	}

	@Override
	public void gotFileId(String fileId) {
		System.out.println("Got File Id: " + fileId);	
	}
}
