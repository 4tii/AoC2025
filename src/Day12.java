import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day12 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./input/Day12.txt")).stream().filter(line -> line.contains("x")).toList();
        List<Region> regions = lines.stream().map(line -> new Region(line.split(": "))).toList();
        System.out.println(regions.stream().filter(r -> r.x* r.y >= Arrays.stream(r.presents).map(p -> p*9).sum()).count());
    }
    
    public static class Region{
        long x;
        long y;
        long[] presents;
        Region(String[] parts){
            String[] area = parts[0].split("x");
            this.x = Long.parseLong(area[0]);
            this.y = Long.parseLong(area[1]);
            String[] amount = parts[1].split(" ");
            this.presents = Arrays.stream(amount).mapToLong(Long::parseLong).toArray();
        }
    }
}
