import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Day3 {
    public static void main(String[] args) throws IOException {
        List<List<Integer>> values;
        try (Stream<String> lines = Files.lines(Paths.get("./input/Day3.txt"))) {
            values = lines.map(line -> line.chars()
                    .map(c -> c - '0')
                    .boxed()
                    .toList()
            ).toList();
        }

        long result = 0;
        int digitLength = 2;
        for(List<Integer> list : values)
            result += findMax(list, digitLength);
        System.out.println(result);

        //Part 2
        result = 0;
        digitLength = 12;
        for(List<Integer> list : values)
            result += findMax(list, digitLength);
        System.out.println(result);
    }

    private static Long findMax(List<Integer> values, int digitLength){
        int n = values.size();
        Long[][] memo = new Long[n + 1][digitLength + 1];
        return findMaxRec(values, 0, digitLength, memo);
    }

    private static Long findMaxRec(List<Integer> values, int index, int remaining, Long[][] memo){
        if(remaining == 0) return 0L;
        if(index == values.size()) return -1L;

        if(memo[index][remaining] != null) return memo[index][remaining];

        Long skip = findMaxRec(values, index+1, remaining, memo);
        Long takeNext = findMaxRec(values, index+1, remaining-1, memo);
        Long take = 0L;
        if(takeNext != -1L)
            take = Long.parseLong(values.get(index) + (takeNext == 0 ? "" : String.valueOf(takeNext)));

        Long best = skip > take ? skip : take;
        memo[index][remaining] = best;
        return best;
    }
}
