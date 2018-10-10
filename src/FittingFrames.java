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
		Mat Q2I2 = Imgcodecs.imread(pathToImages + "Q2I2.jpg"); // output
		Mat Q2I3 = Imgcodecs.imread(pathToImages + "Q2I3.jpg");
		
		// resize and overlay sherlock over first background
		Mat resizeForOut1 = new Mat();
		Imgproc.resize(Q2I1, resizeForOut1, new Size(90, 140));
		overlayImage(Q2I2, resizeForOut1, Q2I2, new Point(1218, 377));
		
		// resize, rotate and overlay sherlock over second background
		Mat rotationMatrix = Imgproc.getRotationMatrix2D(new Point(Q2I1.rows()/2, Q2I1.cols()/2), -5, 1.0);
		Mat resizeRotateForOut2 = new Mat();
		Imgproc.warpAffine(Q2I1, resizeRotateForOut2, rotationMatrix, Q2I1.size());
		
		Imgcodecs.imwrite(pathToWriteLocation + "output.png", Q2I3);
	}
	
	public static void overlayImage(Mat background, Mat foreground, Mat output, Point location)
	{
		background.copyTo(output); // copy the background to the output (if it's a different matrix)
		
		for(int y = (int) Math.max(location.y , 0); y < background.rows(); ++y) //looping over the rows of the background
		{
			int fY = (int) (y - location.y);
	
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
