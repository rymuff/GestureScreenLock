package com.kweisa.gesturescreenlock;


import java.util.ArrayList;

class GestureChecker {
    @SuppressWarnings("FieldCanBeLocal")
    private static float threshold = 180;

    private static ArrayList<Point> normalize(ArrayList<Point> data) {
        ArrayList<Point> pointList = new ArrayList<>();
        pointList.addAll(data);

        int frameWidth = 1920;
        int frameHeight = 1080;

        Point start = pointList.get(0);
        float dx = start.getX();
        float dy = start.getY();

        float maxX = -1;
        float minX = frameWidth + 1;
        float maxY = -1;
        float minY = frameHeight + 1;

        for (Point point : pointList) {
            point.setX(point.getX() - dx);
            point.setY(point.getY() - dy);

            float x = point.getX();
            float y = point.getY();

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

        float gestureWidth = Math.abs(maxX - minX);
        float gestureHeight = Math.abs(maxY - minY);
        float widthRatio = 1;
        float heightRatio = 1;
        if (gestureWidth > 0) {
            widthRatio = frameWidth / gestureWidth;
        }
        if (gestureHeight > 0) {
            heightRatio = frameHeight / gestureHeight;
        }
        float resizeRatio = Math.min(widthRatio, heightRatio);

        ArrayList<Point> normalizedPointList = new ArrayList<>();

        normalizedPointList.add(new Point(0, 0));
        for (Point p : pointList) {
            normalizedPointList.add(new Point(resizeRatio * p.getX(), resizeRatio * p.getY()));
        }

        return normalizedPointList;
    }

    static boolean check(ArrayList<Point> gesture1, ArrayList<Point> gesture2) {
        ArrayList<Point> savedGesture = normalize(gesture1);
        ArrayList<Point> currentGesture = normalize(gesture2);

        float distanceA = 0;
        float distanceB = 0;

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
        float averageDistance = (distanceA + distanceB) / (savedGesture.size() + currentGesture.size());
        return averageDistance < threshold;
    }

    private static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}
