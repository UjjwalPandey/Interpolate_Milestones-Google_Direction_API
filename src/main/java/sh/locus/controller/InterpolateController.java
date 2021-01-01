package sh.locus.controller;

import sh.locus.client.GoogleDirectionAPI;
import sh.locus.pojo.Point;
import sh.locus.utils.DecoderUtils;
import sh.locus.utils.DistanceUtils;

import java.io.IOException;
import java.util.ArrayList;

public class InterpolateController {
    /**
     *
     * @param point1 Source
     * @param point2 Destination
     * @return  ArrayList of 50-m intermediate points
     * @throws IOException raised from googleDirectionAPI
     */
    public ArrayList<Point> getConnectingPoints(Point point1, Point point2) throws IOException {
        ArrayList<Point> pointList = new ArrayList<>();

        // Fetch Google Direction API Response
        GoogleDirectionAPI googleDirectionAPI = new GoogleDirectionAPI();
        String polyline = googleDirectionAPI.getResponse(point1, point2);

        // If API Response = 200_OK and valid response
        if(!polyline.equals("")){
            pointList = DecoderUtils.decode(polyline);      // Decode the polyline points
            ArrayList<Point> intermediatePoints = new ArrayList<>();
            
            // If distance between two consecutive points > 50 then add an intermediate point. 
            for(int i=0; i < pointList.size() -1; i++){
                Point tempStart = pointList.get(i);
                Point polylineEnd =  pointList.get(i+1);
                double intermediateDistance = DistanceUtils.getDistanceFromLatLonInMeter(tempStart, polylineEnd);

                while (intermediateDistance > 50){
                    double hypotenuseLength = Math.sqrt(Math.pow(polylineEnd.getLon()- tempStart.getLon(), 2) +
                            Math.pow(polylineEnd.getLat()- tempStart.getLat(), 2));

                    // Ratio links Distance(in m) with LatLon coordinates
                    double ratio_slant_meter = (hypotenuseLength/intermediateDistance);   
                    
                    double x = tempStart.getLat() + ((50*ratio_slant_meter) * (polylineEnd.getLat() - tempStart.getLat())/hypotenuseLength);
                    double y = tempStart.getLon() + ((50*ratio_slant_meter) * (polylineEnd.getLon() - tempStart.getLon())/hypotenuseLength);
                    
                    Point intermediatePoint = new Point(x, y);
                    intermediatePoints.add(intermediatePoint);

                    // Update the startPoint of path with newly created point.
                    tempStart = intermediatePoint;
                    
                    // Update intermediateDistance to distance between newly created point and polylineEnd, for while loop.
                    intermediateDistance = DistanceUtils.getDistanceFromLatLonInMeter(intermediatePoint, polylineEnd);   
                }
            }
            pointList.addAll(intermediatePoints);
        }
        return pointList;
    }
}
