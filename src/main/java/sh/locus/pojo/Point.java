package sh.locus.pojo;

public class Point {
    double lat, lon;

    public Point(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return this.lat+","+this.lon;
    }
}
