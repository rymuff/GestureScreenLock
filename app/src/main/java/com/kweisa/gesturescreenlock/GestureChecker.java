package com.kweisa.gesturescreenlock;


import java.util.ArrayList;

class GestureChecker {
    private static double threshold = 180;

    private static ArrayList<Point> normalize(ArrayList<Point> data) {
        ArrayList<Point> pointList = new ArrayList<>();
        pointList.addAll(data);

        int frameWidth = 1920;
        int frameHeight = 1080;

        Point firstPoint = pointList.get(0);
        double dx = firstPoint.getX();
        double dy = firstPoint.getY();

        double maxX = -1;
        double minX = frameWidth + 1;
        double maxY = -1;
        double minY = frameHeight + 1;

        for (Point point : pointList) {
            point.setX(point.getX() - dx);
            point.setY(point.getY() - dy);

            double x = point.getX();
            double y = point.getY();

            if (x > maxX) {
                maxX = x;
            } else if (x < minX) {
                minX = x;
            }
            if (y > maxY) {
                maxY = y;
            } else if (y < minY) {
                minY = y;
            }
        }

        pointList.remove(0);

        double gestureWidth = Math.abs(maxX - minX);
        double gestureHeight = Math.abs(maxY - minY);
        double widthRatio = 1;
        double heightRatio = 1;
        if (gestureWidth > 0) {
            widthRatio = frameWidth / gestureWidth;
        }
        if (gestureHeight > 0) {
            heightRatio = frameHeight / gestureHeight;
        }
        double resizeRatio = Math.min(widthRatio, heightRatio);

        ArrayList<Point> normalizedPointList = new ArrayList<Point>();

        normalizedPointList.add(new Point(0, 0));
        for (Point p : pointList) {
            normalizedPointList.add(new Point(resizeRatio * p.getX(), resizeRatio * p.getY()));
        }

        return normalizedPointList;
    }

    static boolean check(ArrayList<Point> gesture1, ArrayList<Point> gesture2) {
        ArrayList<Point> savedGesture = normalize(gesture1);
        ArrayList<Point> currentGesture = normalize(gesture2);

        double distanceA = 0;
        double distanceB = 0;

        for (Point a : savedGesture) {
            double minimumDistance = 9999;
            for (Point b : currentGesture) {
                double distance = distance(a, b);
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                }
            }
            distanceA += minimumDistance;
        }
        for (Point b : currentGesture) {
            double minimumDistance = 9999;
            for (Point a : savedGesture) {
                double distance = distance(a, b);
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                }
            }
            distanceB += minimumDistance;
        }
        double averageDistance = (distanceA + distanceB) / (savedGesture.size() + currentGesture.size());
        return averageDistance < threshold;
    }

    private static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}
