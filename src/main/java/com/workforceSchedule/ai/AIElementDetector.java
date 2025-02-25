package com.workforceSchedule.ai;

import java.io.File;
import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.CvType;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.opencv.core.Size;

public class AIElementDetector {
 Mat source;
	
    static {
        try {
        	 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            
          
        } catch (UnsatisfiedLinkError e) {
            System.err.println("❌ Failed to load OpenCV library. Ensure -Djava.library.path is set correctly.");
            e.printStackTrace();
        }
    }

    /**
     * Detects the element's location in a screenshot using template matching.
     * Assumes a template image exists at "src/test/resources/templates/{elementLabel}.png"
     * (e.g., "Login Button" becomes "login_button.png").
     *
     * @param screenshot   The screenshot file.
     * @param elementLabel A description of the element to locate.
     * @return A Rectangle with the element’s x, y, width, and height, or null if not found.
     */
    public Rectangle detectElement(File screenshot, String elementLabel) {
        // Check if the screenshot file exists.
        if (!screenshot.exists()) {
            System.err.println("❌ Screenshot file not found: " + screenshot.getAbsolutePath());
            return null;
        }

     
        Mat source;
        try {
            byte[] imageData = Files.readAllBytes(screenshot.toPath());
            MatOfByte mob = new MatOfByte(imageData);
            source = Imgcodecs.imdecode(mob, Imgcodecs.IMREAD_COLOR);
        } catch (IOException e) {
            System.err.println("Error reading screenshot file: " + e.getMessage());
            return null;
        }

        if (source.empty()) {
            System.err.println("❌ Could not decode screenshot image (file may be corrupted or unsupported): " + screenshot.getAbsolutePath());
            return null;
        }
       

        // Build the template image path (e.g., "login_button.png")
        String templateName = elementLabel.toLowerCase().replace(" ", "_") + ".png";
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
                + "test" + File.separator + "resources" + File.separator + "templates" + File.separator + templateName;
       
        
        Mat template = Imgcodecs.imread(templatePath);
        if (template.empty()) {
            System.err.println("❌ Template image not found or unreadable: " + templatePath);
            return null;
        }
       

        // Create a result matrix for template matching.
        int resultCols = source.cols() - template.cols() + 1;
        if (template.rows() > source.rows() || template.cols() > source.cols()) {
            double scaleFactor = Math.min((double)source.rows() / template.rows(), (double)source.cols() / template.cols());
         
            Mat resizedTemplate = new Mat();
            Imgproc.resize(template, resizedTemplate, new Size(template.cols() * scaleFactor, template.rows() * scaleFactor));
            template = resizedTemplate; // use the resized template for matching
        }
        int resultRows = source.rows() - template.rows() + 1;
       
        Mat result = new Mat(resultRows, resultCols, CvType.CV_32FC1);

        // Perform template matching using normalized cross-correlation.
        Imgproc.matchTemplate(source, template, result, Imgproc.TM_CCOEFF_NORMED);
        MinMaxLocResult mmr = Core.minMaxLoc(result);

        // Use a threshold (e.g., 0.8) to determine if the element is found.
        if (mmr.maxVal < 0.5) {
            System.out.println("⚠️ Element not found with sufficient confidence: " + elementLabel + " (Confidence: " + mmr.maxVal + ")");
            return null;
        }

        // Calculate the bounding box based on the match location.
        Point matchLoc = mmr.maxLoc;
        int x = (int) matchLoc.x;
        int y = (int) matchLoc.y;
        int width = template.cols();
        int height = template.rows();

      
        return new Rectangle(x, y, width, height);
    }
}