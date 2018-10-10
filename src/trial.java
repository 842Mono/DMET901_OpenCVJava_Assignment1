import java.awt.BufferCapabilities.FlipContents;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class trial {
	
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Imgcodecs imageCodecs = new Imgcodecs();
        Mat figure1 = imageCodecs.imread("A1I/Q1I1.png");
        Mat figure3 = imageCodecs.imread("A1I/Q1I2.jpg");
        blend(figure1,figure3);
       




}
    
    public static void blend(Mat figure1, Mat figure3){
        Imgcodecs imageCodecs = new Imgcodecs();
      // flip figure 3 amd resize
      Core.flip(figure3,figure3,1);
      Imgproc.resize(figure3, figure3, new Size(1121,788 ));
      //write the flipped image 
      imageCodecs.imwrite("flipped.png",figure3);
      
      //brightness and contrast
      double alpha = 8;
//      double beta = 100;
      Mat destination = new Mat(figure1.rows(),figure1.cols(),figure1.type());
//      figure1.convertTo(destination, -1, alpha, beta);
      for(int col=0;col<figure1.cols();col++){
      for(int row=0;row<figure1.rows();row++){
      		double[]fig1val= figure1.get(row, col);
      		if(row==figure1.rows()/2)
      		{   
//	        		if(alpha>1) 
	        			alpha-=0.0075;
//	        		if(beta>50)beta;
	        		System.out.println(alpha);
//	        		System.out.println(beta);
	        	}
      		destination.put(row, col, new double[]{(fig1val[0]*alpha),(fig1val[1]*alpha),
      				(fig1val[2]*alpha)});
      	
      	}
      	}
    imageCodecs.imwrite("bright.png",destination);
      
      
      //create final mat with figure 1 size as it is larger
      
      Mat finalMat = new Mat(figure1.rows(),figure1.cols(),figure1.type());
//		figure1.copyTo(finalMat);
//      for(int row=0;row<figure1.rows();row++){
//      	for(int col=0;col<300;col++){
//      		double[]fig1val= figure1.get(row, col);
//      		double[]fig3val= figure3.get(row, col);
//      		finalMat.put(row, col, new double[]{(fig3val[0]*0.15)+(fig1val[0]*0.85),(fig3val[1]*0.15)+(fig1val[1]*0.85),(fig3val[2]*0.15)+(fig1val[2]*0.85)});
//      	}
//      }
      // blending 0.5 of each figure
      for(int row=0;row<destination.rows();row++){
      	for(int col=0;col<destination.cols();col++){
      		double[]fig1val= destination.get(row, col);
      		double[]fig3val= figure3.get(row, col);
      		if(col>300){
      			fig3val= figure3.get(row, col-300);
      		finalMat.put(row, col, new double[]{(fig3val[0]*0.15)+(fig1val[0]*0.85),(fig3val[1]*0.15)+(fig1val[1]*0.85)
      				,(fig3val[2]*0.15)+(fig1val[2]*0.85)});}
      		else{
      			finalMat.put(row, col, new double[]{(fig3val[0])+(fig1val[0]),(fig3val[1])+(fig1val[1])
          				,(fig3val[2])+(fig1val[2])});
      		}
      	}
      }
      //write final image
      imageCodecs.imwrite("blend.png",finalMat);
      
    }
    
}
