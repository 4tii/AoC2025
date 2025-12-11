import util.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day9 {
    public static void main(String[] args) throws IOException {
        List<Coordinate> coordinates = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get("./input/Day9.txt"))) {
            coordinates = lines.map(line -> line.split(","))
                    .map(parts -> 
                            new Coordinate(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())))
                    .toList();
        }
        
        long largestArea = Long.MIN_VALUE;
        for(Coordinate c1: coordinates) {
            for (Coordinate c2: coordinates) {
                if(c1!=c2 && getArea(c1, c2) > largestArea)
                    largestArea = getArea(c1, c2);
            }
        }
        System.out.println(largestArea);

        //Part 2
        largestArea = Long.MIN_VALUE;
        for(Coordinate c1: coordinates) {
            for (Coordinate c2: coordinates) {
                if(c1 != c2 && getArea(c1, c2) > largestArea && isRectangleEnclosed(coordinates, c1, c2))
                    largestArea = getArea(c1, c2);
            }
        }
        System.out.println(largestArea);
    }
    
    private static long getArea(Coordinate c1, Coordinate c2) {
        return (long) (Math.abs(c1.x - c2.x)+1) * (Math.abs(c1.y - c2.y)+1);
    }

    public static boolean isRectangleEnclosed(List<Coordinate> polygon, Coordinate c1, Coordinate c2) {
        int minX = Math.min(c1.x, c2.x);
        int maxX = Math.max(c1.x, c2.x);
        int minY = Math.min(c1.y, c2.y);
        int maxY = Math.max(c1.y, c2.y);
        
        Coordinate[] corners = new Coordinate[4];
        corners[0] = new Coordinate(minX, minY);
        corners[1] = new Coordinate(minX, maxY);
        corners[2] = new Coordinate(maxX, minY);
        corners[3] = new Coordinate(maxX, maxY);
        
        for (Coordinate corner : corners) {
            if (!isPointInPolygon(corner, polygon)) return false;
        }

        for (int i = 0; i < 4; i++) {
            Coordinate rc1 = corners[i];
            Coordinate rc2 =  corners[(i + 1) % 4];
            for (int j = 0; j < polygon.size(); j++) {
                Coordinate pc1 = polygon.get(j);
                Coordinate pc2 = polygon.get((j + 1) % polygon.size());
                if (doEdgesIntersect(rc1, rc2, pc1, pc2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPointInPolygon(Coordinate c, List<Coordinate> polygon) {
        boolean inside = false;
        for (int i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++) {
            Coordinate pc1 = polygon.get(i);
            Coordinate pc2 = polygon.get(j);

            if (isPointOnLineSegment(c, pc1, pc2)) return true;
            
            if (((pc1.y > c.y) != (pc2.y > c.y)) &&
                    (c.x < (pc2.x - pc1.x) * (c.y - pc1.y) / (pc2.y - pc1.y) + pc1.x)) {
                inside = !inside;
            }
        }
        return inside;
    }

    private static boolean isPointOnLineSegment(Coordinate p, Coordinate pc1, Coordinate pc2) {
        if (pc1.x == pc2.x) {
            return p.x == pc1.x && p.y >= Math.min(pc1.y, pc2.y) && p.y <= Math.max(pc1.y, pc2.y);
        } else if (pc1.y == pc2.y) {
            return p.y == pc1.y && p.x >= Math.min(pc1.x, pc2.x) && p.x <= Math.max(pc1.x, pc2.x);
        }
        return false;
    }

    private static boolean doEdgesIntersect(Coordinate rc1, Coordinate rc2, Coordinate pc1, Coordinate pc2) {
        boolean aVertical = rc1.x == rc2.x;
        boolean bVertical = pc1.x == pc2.x;
        
        if (aVertical && !bVertical) {
            int ax = rc1.x;
            int ay1 = Math.min(rc1.y, rc2.y);
            int ay2 = Math.max(rc1.y, rc2.y);

            int by = pc1.y;
            int bx1 = Math.min(pc1.x, pc2.x);
            int bx2 = Math.max(pc1.x, pc2.x);
            
            return (bx1 < ax && ax < bx2) && (ay1 < by && by < ay2);
        }

        if (!aVertical && bVertical) {
            int ay = rc1.y;
            int ax1 = Math.min(rc1.x, rc2.x);
            int ax2 = Math.max(rc1.x, rc2.x);

            int bx = pc1.x;
            int by1 = Math.min(pc1.y, pc2.y);
            int by2 = Math.max(pc1.y, pc2.y);
            
            return (by1 < ay && ay < by2) && (ax1 < bx && bx < ax2);
        }
        return false;
    }
}
