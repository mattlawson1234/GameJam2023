import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class StdDrawPractice {
	public static void main (String[]arg) {
		double x=0.05;
		double y=0.5;
		int done =0;
		StdDraw.enableDoubleBuffering();
		while (done==0) {		
		StdDraw.setPenRadius(0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.line(0.01,0.01,0.8,0.01);
		StdDraw.line(0.99,0.01,0.99,0.99);
		StdDraw.line(0.99,0.99,0.01,0.99);
		StdDraw.line(0.01,0.99,0.01,0.6);
		StdDraw.line(0.01, 0.4, 0.01, 0.01);
		StdDraw.line(0.2, 1, 0.2, 0.6);
		StdDraw.line(0, 0.4, 0.4, 0.4);
		StdDraw.line(0.4, 0.4, 0.4, 0.6);
		StdDraw.line(0.4, 0.8, 0.4, 1);
		StdDraw.line(0.4, 0.8, 0.6, 0.8);
		StdDraw.line(0.6, 0.8, 0.6, 0.4);
		StdDraw.line(0.2, 0.2, 0.8, 0.2);
		StdDraw.line(0.8, 0, 0.8, 0.8);
		
			if(StdDraw.isKeyPressed(37)) {
				x=x-0.05;
			}
			if (StdDraw.isKeyPressed(38)) {
				y=y+0.05;
			}
			if (StdDraw.isKeyPressed(39)) {
				x=x+0.05;
			}
			if (StdDraw.isKeyPressed(40)) {
				y=y-0.05;
			}
			if (StdDraw.isKeyPressed(13)) {
				done++;
			}
		StdDraw.picture(x, y, "Mario",0.125,0.125);
		StdDraw.show();
		StdDraw.pause(60);
		StdDraw.clear();
		}
	}
}