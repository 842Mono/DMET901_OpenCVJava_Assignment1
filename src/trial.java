import java.awt.BufferCapabilities.FlipContents;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class trial {
	
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Imgcodecs imageCodecs = new Imgcodecs();
        Core core= new Core();
//        // read figure 3
//        Mat figure3 = imageCodecs.imread("A1I/Q1I2.jpg");
////        Imgcodecs.imwrite("test.png", );
//        // flip figure 3
//        core.flip(figure3,figure3,1);
//        //write the flipped image
//        imageCodecs.imwrite("test.png",figure3);
//        System.out.println("mat = " + figure3.height());
//        
        
        Mat figure1 = imageCodecs.imread("A1I/Q1I1.png");
        double alpha = 0.5;
        double beta = 20;
        Mat destination = new Mat(figure1.rows(),figure1.cols(),figure1.type());
        figure1.convertTo(destination, -1, alpha, beta);
        imageCodecs.imwrite("test2.png",destination);

        System.out.println("mat = " + figure1.height());


}
    
}
