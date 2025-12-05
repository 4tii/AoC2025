import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./input/Day5.txt"));

        List<Range> ranges = lines.subList(0, lines.indexOf("")).stream()
                .map(s -> s.split("-"))
                .map(arr -> new Range(Long.parseLong(arr[0]), Long.parseLong(arr[1])))
                .toList();

        Set<Long> ids = lines.subList(lines.indexOf("")+1, lines.size()).stream()
                        .map(Long::parseLong)
                        .collect(Collectors.toSet());

        int result = 0;
        for(long id: ids) {
            for(Range range: ranges) {
                if(id>=range.start && id<=range.end) {
                    result++;
                    break;
                }
            }
        }
        System.out.println(result);

        //Part 2
        List<Range> sorted = ranges.stream().sorted(Comparator.comparingLong(r -> r.start)).toList();
        List<Range> combined = new ArrayList<>();
        Range current = sorted.getFirst();

        for(Range next: sorted) {
            if(next.start<=current.end)
                current = new Range(current.start, Math.max(current.end, next.end));
            else{
                combined.add(current);
                current = next;
            }
        }
        combined.add(current);
        System.out.println(combined.stream().mapToLong(r-> r.end-r.start + 1).sum());
    }

    public static class Range {
        long start;
        long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

    }
}

