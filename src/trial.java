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
        
        // read figure 3
          Mat figure3 = imageCodecs.imread("A1I/Q1I2.jpg");
        // flip figure 3
        core.flip(figure3,figure3,1);
        //write the flipped image 
        imageCodecs.imwrite("flipped.png",figure3);
//        System.out.println("mat = " + figure3.height());
       
        //read figure1
        Mat figure1 = imageCodecs.imread("A1I/Q1I1.png");
        //brightness and contrast
        double alpha = 10;
        double beta = 100;
        Mat destination = new Mat(figure1.rows(),figure1.cols(),figure1.type());
        figure1.convertTo(destination, -1, alpha, beta);
        imageCodecs.imwrite("test2.png",destination);
        
        //create final mat with figure 1 size as it is larger
        Mat finalMat = new Mat(figure1.rows(),figure1.cols(),figure1.type());

        // blending 0.5 of each figure
        for(int row=0;row<figure1.rows();row++){
        	for(int col=0;col<figure1.cols();col++){
        		double[]fig1val= figure1.get(row, col);
        		double[]fig3val= figure3.get(row, col);
        		finalMat.put(row, col, new double[]{(fig3val[0]*0.5)+(fig1val[0]*0.5),(fig3val[1]*0.5)+(fig1val[1]*0.5),(fig3val[2]*0.5)+(fig1val[2]*0.5)});
        	}
        }
        //write final image
        imageCodecs.imwrite("blend.png",finalMat);

}
    
    
    
}
