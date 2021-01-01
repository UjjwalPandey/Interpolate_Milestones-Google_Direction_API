package sh.locus.utils;

import sh.locus.pojo.Point;

public class DistanceUtils {
    /**
     *
     * @param p1 Source
     * @param p2 Destination
     * @return distance between the provided points
     */
    public static double getDistanceFromLatLonInMeter(Point p1, Point p2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(p2.getLat()-p1.getLat());  // deg2rad below
        double dLon = deg2rad(p2.getLon()-p1.getLon());
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(deg2rad(p1.getLat())) * Math.cos(deg2rad(p2.getLat())) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (R * c)*1000;
    }

    /**
     *
     * @param deg degree
     * @return  radian equivalent
     */
    static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
}
