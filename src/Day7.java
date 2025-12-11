import util.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day7 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./input/Day7.txt"));
        char[][] manifoldDiagram = lines.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        
        int startX = lines.getFirst().indexOf("S");
        Set<Coordinate> splitCoordinates = new HashSet<>();

        Map<Coordinate, Long> memo =  new HashMap<>();
        long result = moveTachyonBeam(manifoldDiagram, splitCoordinates, memo, startX, 0);
        System.out.println(splitCoordinates.size());
        //Part 2
        System.out.println(result);
    }
    
    private static long moveTachyonBeam(char[][] manifoldDiagram, Set<Coordinate> splitCoordinates, Map<Coordinate, Long> memo, int x, int y){
        if(y == manifoldDiagram.length)
            return 1;
        Coordinate coordinate = new Coordinate(x, y);
        if(memo.containsKey(coordinate))
            return memo.get(coordinate);
        
        long result;
        if(manifoldDiagram[y][x] == '^'){
            splitCoordinates.add(new Coordinate(x, y));
            result = moveTachyonBeam(manifoldDiagram, splitCoordinates, memo,x-1, y+1) + moveTachyonBeam(manifoldDiagram, splitCoordinates, memo, x+1, y+1);
        } else {
            result = moveTachyonBeam(manifoldDiagram, splitCoordinates, memo, x, y+1);
        }
        memo.put(coordinate, result);
        return result;
    }
}

