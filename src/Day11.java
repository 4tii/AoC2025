import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./input/Day11.txt"));
        Map<String, Set<String>> map;
        map = lines.stream().map(line -> line.split(": "))
                .collect(Collectors.toMap(
                        arr -> arr[0],
                        arr -> Arrays.stream(arr[1].split(" "))
                                .collect(Collectors.toSet())
                ));

        Map<String, Long> memo = new HashMap<>();
        System.out.println(findPaths(map, "you", "out", memo));
        
        //Part 2        
        memo.clear();
        Long svrToFft = findPaths(map, "svr", "fft",  memo);
        memo.clear();
        Long fftToDac = findPaths(map, "fft", "dac",  memo);
        memo.clear();
        Long dacToOut = findPaths(map, "dac", "out",  memo);
        memo.clear();
        System.out.println(svrToFft*fftToDac*dacToOut);
        
    }
    
    private static long findPaths(Map<String, Set<String>> map, String current, String target, Map<String, Long> memo) {
        if(memo.containsKey(current))
            return memo.get(current);
        
        if (current.equals(target)) {
            return 1;
        } 
        
        Set<String> nextSet = map.get(current);
        if(nextSet == null || nextSet.isEmpty()){
            memo.put(current, 0L);
            return 0;
        }
        
        long total = 0;
        for (String next : nextSet) {
            total +=  findPaths(map, next, target, memo);
        }
        
        memo.put(current, total);
        return total;
    }
}
