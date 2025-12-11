import util.Coordinate3D;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day8 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./input/Day8.txt"));
        List<Coordinate3D> coordinates = lines.stream().map(line -> new Coordinate3D(line.split(","))).toList();

        double[][] dist = new double[coordinates.size()][coordinates.size()];
        for (double[] longs : dist)
            Arrays.fill(longs, Double.MAX_VALUE);
        
        for(int i = 0; i < coordinates.size(); i++) {
            for(int j = 0; j < coordinates.size(); j++)
                dist[i][j] = shortestPath(coordinates.get(i), coordinates.get(j));
        }
        
        for(int i = 0; i < coordinates.size(); i++) 
            dist[i][i] = Double.MAX_VALUE;
        
        int shortestI=0;
        int shortestJ=0;
        double shortestDist = Double.MAX_VALUE;
        List<Set<Coordinate3D>> list = initList(coordinates);
        
        int k=0;
        while(list.size()>1) {
            for (int i = 0; i < coordinates.size(); i++) {
                for (int j = 0; j < coordinates.size(); j++) {
                    if (dist[i][j] < shortestDist) {
                        shortestDist = dist[i][j];
                        shortestI = i;
                        shortestJ = j;
                    }
                }
            }
            shortestDist = Double.MAX_VALUE;
            dist[shortestI][shortestJ] = Double.MAX_VALUE;
            dist[shortestJ][shortestI] = Double.MAX_VALUE;
            int removeIndex=Integer.MAX_VALUE;
            int addIndex=Integer.MAX_VALUE;
            for(int i = 0; i<list.size(); i++) {
                if(list.get(i).contains(coordinates.get(shortestI)) && list.get(i).contains(coordinates.get(shortestJ)))
                    break;
                if(list.get(i).contains(coordinates.get(shortestI)))
                    removeIndex=i;
                if(list.get(i).contains(coordinates.get(shortestJ)))
                    addIndex=i;
            }
            if(removeIndex!=Integer.MAX_VALUE && addIndex!=Integer.MAX_VALUE) {
                //Part 2
                if(list.size()==2)
                    System.out.println(coordinates.get(shortestI).x*coordinates.get(shortestJ).x);
                
                list.get(addIndex).addAll(list.get(removeIndex));
                list.remove(removeIndex);
            }
            //Part 1
            if(k==999){
                List<Set<Coordinate3D>> sorted = list.stream().sorted(Comparator.comparingInt(Set::size)).toList().reversed();
                System.out.println(sorted.stream().limit(3)
                        .mapToInt(Set::size)
                        .reduce(1, (a,b) -> a*b));
            }
            k++;
        }
    }

    private static double shortestPath(Coordinate3D c1, Coordinate3D c2){
        return Math.sqrt(Math.pow((double) c1.x-c2.x, 2) + Math.pow((double) c1.y-c2.y, 2) + Math.pow((double) c1.z-c2.z,2));
    }
    
    private static List<Set<Coordinate3D>> initList(List<Coordinate3D> coordinates){
        List<Set<Coordinate3D>> list = new ArrayList<>();
        for(Coordinate3D c : coordinates){
            Set<Coordinate3D> set = new HashSet<>();
            set.add(c);
            list.add(set);
        }
        return list;
    }
}
