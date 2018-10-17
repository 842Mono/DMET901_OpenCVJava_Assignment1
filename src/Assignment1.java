import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

public class Assignment1
{
	final static String pathToImages = "C:\\Users\\mina_\\Desktop\\eclipse-workspace\\TestOpencv\\A1I\\";
	final static String pathToWriteLocation = "C:\\Users\\mina_\\Desktop\\CV project\\";
	
	public static void main(String [] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
        	Mat figure1 = Imgcodecs.imread(pathToImages + "Q1I1.png");
        	Mat figure3 = Imgcodecs.imread(pathToImages + "Q1I2.jpg");
        	Mat blended = blend(figure1,figure3); // output

		Mat Q2I1 = Imgcodecs.imread(pathToImages + "Q2I1.jpg");
		Mat Q2I2 = Imgcodecs.imread(pathToImages + "Q2I2.jpg"); // output
		Mat Q2I3 = Imgcodecs.imread(pathToImages + "Q2I3.jpg"); // output
		Mat Q3 = Imgcodecs.imread(pathToImages + "Q3I1.jpg"); // output
		
		// resize and overlay sherlock over first background
		Mat resizeForOut1 = new Mat();
		Imgproc.resize(Q2I1, resizeForOut1, new Size(90, 140));
		overlayImage(Q2I2, resizeForOut1, Q2I2, new Point(1218, 377));
		
		// resize, rotate and overlay sherlock over second background
		Mat rotationMatrix = Imgproc.getRotationMatrix2D(new Point(Q2I1.cols()/2, Q2I1.rows()/2), -5.5, 0.67);
		Mat resizeRotateForOut2 = new Mat();
		Imgproc.resize(Q2I1, resizeRotateForOut2, new Size(500, 650)); // fix vertical aspect ratio
		Imgproc.cvtColor(resizeRotateForOut2, resizeRotateForOut2, Imgproc.COLOR_RGB2RGBA); // add alpha
		Imgproc.cvtColor(Q2I3, Q2I3, Imgproc.COLOR_RGB2RGBA); //add alpha
		Imgproc.warpAffine(resizeRotateForOut2, resizeRotateForOut2, rotationMatrix, Q2I1.size()); // rotate and scale
		overlayImageWithAlpha(Q2I3, resizeRotateForOut2, Q2I3, new Point(265,-5)); // overlay. // note, bottom left pixel: 325, 525
		
		Imgproc.cvtColor(Q2I1, Q2I1, Imgproc.COLOR_RGB2RGBA); //add alpha
		Imgproc.cvtColor(Q3, Q3, Imgproc.COLOR_RGB2RGBA); //add alpha
		ArrayList<Point> initialTransform = new ArrayList<Point>(), finalTransform = new ArrayList<Point>();
		initialTransform.add(new Point(0, 0));
		initialTransform.add(new Point(Q2I1.cols(), 0));
		initialTransform.add(new Point(Q2I1.cols(), Q2I1.rows()));
		initialTransform.add(new Point(0, Q2I1.rows()));
		finalTransform.add(new Point(163, 35));
		finalTransform.add(new Point(469, 69));
		finalTransform.add(new Point(464, 353));
		finalTransform.add(new Point(157, 391));
		
		Mat transform = Imgproc.getPerspectiveTransform(Converters.vector_Point2f_to_Mat(initialTransform), Converters.vector_Point2f_to_Mat(finalTransform));
		Mat homographyTransformation = new Mat();
		
		
		Imgproc.warpPerspective(Q2I1, homographyTransformation, transform, new Size(612, 406));
		overlayImageWithAlpha(Q3, homographyTransformation, Q3, new Point(0,0));
		
		Imgcodecs.imwrite(pathToWriteLocation + "Output Question 1.png", blended);
		Imgcodecs.imwrite(pathToWriteLocation + "Output Question 2 Fig 8.png", Q2I2);
		Imgcodecs.imwrite(pathToWriteLocation + "Output Question 2 Fig 9.png", Q2I3);
		Imgcodecs.imwrite(pathToWriteLocation + "Output Question 3.png", Q3);
	}
	
	public static Mat blend(Mat figure1, Mat figure3){
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
      
      Mat finalMat = new Mat(destination.rows(),destination.cols(),destination.type());
		destination.copyTo(finalMat);
		
      for(int row=0;row<destination.rows();row++){
      	for(int col=0;col<300;col++){
      		double[]fig1val= destination.get(row, col);
      		double[]fig3val= figure3.get(row, col);
      		finalMat.put(row, col, new double[]{(fig3val[0]*0.15)+(fig1val[0]*0.85),(fig3val[1]*0.15)+(fig1val[1]*0.85),(fig3val[2]*0.15)+(fig1val[2]*0.85)});
      	}
      }
      // blending 0.5 of each figure
      for(int row=0;row<destination.rows();row++){
      	for(int col=300;col<destination.cols();col++){
      		double[]fig1val= destination.get(row, col);
      		double[]fig3val= figure3.get(row, col-300);
   
      		finalMat.put(row, col, new double[]{(fig3val[0]*0.15)+(fig1val[0]*0.85),(fig3val[1]*0.15)+(fig1val[1]*0.85)
      				,(fig3val[2]*0.15)+(fig1val[2]*0.85)});

      	}
      }
      // write final image
      //imageCodecs.imwrite("blend.png",finalMat);
      return finalMat;
    }
	
	public static void overlayImageWithAlpha(Mat background,Mat foreground,Mat output, Point location)
	{
		background.copyTo(output);

		for(int y = (int) Math.max(location.y , 0); y < background.rows(); ++y)
		{
			int fY = (int) (y - location.y);
	
			if(fY >= foreground.rows())
				break;
	
			for(int x = (int) Math.max(location.x, 0); x < background.cols(); ++x)
			{
				int fX = (int) (x - location.x);
				if(fX >= foreground.cols())
					break;
	
				double opacity;
				double[] finalPixelValue = new double[4];
	
				opacity = foreground.get(fY , fX)[3];
	
				finalPixelValue[0] = background.get(y, x)[0];
				finalPixelValue[1] = background.get(y, x)[1];
				finalPixelValue[2] = background.get(y, x)[2];
				finalPixelValue[3] = background.get(y, x)[3];

				for(int c = 0;  c < output.channels(); ++c)
				{
					if(opacity > 0)
					{
						double foregroundPx =  foreground.get(fY, fX)[c];
						double backgroundPx =  background.get(y, x)[c];

						float fOpacity = (float) (opacity / 255);
						finalPixelValue[c] = ((backgroundPx * ( 1.0 - fOpacity)) + (foregroundPx * fOpacity));
						if(c==3)
							finalPixelValue[c] = foreground.get(fY,fX)[3];
					}
				}
				output.put(y, x,finalPixelValue);
			}
		}
	}
	
	public static void overlayImage(Mat background, Mat foreground, Mat output, Point location)
	{
		background.copyTo(output); // copy the background to the output (if it's a different matrix)
		
		for(int y = (int) Math.max(location.y , 0); y < background.rows(); ++y) // looping over the rows of the background
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
