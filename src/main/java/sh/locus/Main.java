package sh.locus;

import sh.locus.controller.InterpolateController;
import sh.locus.pojo.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        try {
            System.out.println("Enter Lat of Location 1: ");
            double lat1 = Double.parseDouble(sc.nextLine().split(" ")[0]);
            System.out.println("Enter Long of Location 1: ");
            double long1 = Double.parseDouble(sc.nextLine().split(" ")[0]);

            System.out.println("Enter Lat of Location 2: ");
            double lat2 = Double.parseDouble(sc.nextLine().split(" ")[0]);
            System.out.println("Enter Long of Location 2: ");
            double long2 = Double.parseDouble(sc.nextLine().split(" ")[0]);

            Point point1 = new Point(lat1, long1);
            Point point2 = new Point(lat2, long2);

            InterpolateController interpolator = new InterpolateController();
            ArrayList<Point> pointList = interpolator.getConnectingPoints(point1, point2);
            for (Point point : pointList) {
                System.out.println(point.toString());
            }
        }catch (NumberFormatException | IOException e){
            System.out.println("Number Format Exception. "+e.toString());
        }
    }
}
