package sh.locus.utils;

import sh.locus.pojo.Point;

import java.util.ArrayList;

public class DecoderUtils {
    /**
     *
     * @param encodedPath   a polyline String
     * @return  ArrayList of LatLon after decoding polyline
     */
    public static ArrayList<Point> decode(final String encodedPath) {
        int len = encodedPath.length();
        final ArrayList<Point> path = new ArrayList<>(len / 2);
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new Point(lat * 1e-5, lng * 1e-5));
        }
        return path;
    }

}
