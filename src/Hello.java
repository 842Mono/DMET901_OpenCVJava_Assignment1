import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
//import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;

public class Hello
{
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat mat = Mat.eye(3,  3, CvType.CV_8UC1);
		System.out.println("mat = " + mat.dump());
		
//		Mat inputImage = Highgui.imread("", Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		Mat inputImage = Imgcodecs.imread("C:\\\\Users\\mina_\\Desktop\\CV project\\Assignment_1\\A1I\\Q1I1.png");
		Imgcodecs.imwrite("C:\\Users\\mina_\\Desktop\\CV project\\Assignment_1\\A1I\\test.png", inputImage);
	}
}
