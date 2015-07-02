

import com.sendsafely.ProgressInterface;

public class ProgressCallback implements ProgressInterface {

	@Override
	public void updateProgress(double progress) {
		System.out.println("Progress: " + progress);
	}

}
