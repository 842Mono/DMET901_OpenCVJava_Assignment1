//import java.awt.Point;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FittingFrames
{
	final static String pathToImages = "C:\\Users\\mina_\\Desktop\\eclipse-workspace\\TestOpencv\\A1I\\";
	final static String pathToWriteLocation = "C:\\Users\\mina_\\Desktop\\CV project\\";
	
	public static void main(String [] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat Q2I1 = Imgcodecs.imread(pathToImages + "Q2I1.jpg");
		Mat Q2I2 = Imgcodecs.imread(pathToImages + "Q2I2.jpg");
		
		// resize sherlock
		Imgproc.resize(Q2I1, Q2I1, new Size(90, 140));
		
		// overlay sherlock over first background
		Mat out = new Mat();
		overlayImage(Q2I2, Q2I1, out, new Point(1218, 377));
		
		Imgcodecs.imwrite(pathToWriteLocation + "output.png", out);
	}
	
	public static void overlayImage(Mat background, Mat foreground, Mat output, Point location)
	{
		background.copyTo(output); // copy the background to the output
		
		for(int y = (int) Math.max(location.y , 0); y < background.rows(); ++y) //looping over the rows of the background
		{
			int fY = (int) (y - location.y); // I dunno what this is XD
	
			if(fY >= foreground.rows()) // break if we're done with overlaying the foreground
				break;
	
			for(int x = (int) Math.max(location.x, 0); x < background.cols(); ++x) // looping over the columns of the background
			{
				int fX = (int) (x - location.x);
				
				if(fX >= foreground.cols()) // break if we're done with overlaying the foreground
					break;
				
				output.put(y, x, new double[] {foreground.get(fY, fX)[0], foreground.get(fY, fX)[1], foreground.get(fY, fX)[2]});
			}
		}
	}
}
