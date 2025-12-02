import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day2 {
    public static void main(String[] args) throws IOException {
        List<String> values;
        try (Stream<String> lines = Files.lines(Paths.get("./input/Day2.txt"))) {
            values = lines.flatMap(s -> Arrays.stream(s.split(",")))
                .flatMap(range -> {
                    String[] parts = range.split("-");
                    long start = Long.parseLong(parts[0]);
                    long end = Long.parseLong(parts[1]);
                    return LongStream.rangeClosed(start, end).mapToObj(Long::toString);
                }).toList();
        }

        long result = 0;
        Pattern pattern = Pattern.compile("^(\\d+)\\1$");
        for(String value : values) {
            Matcher matcher = pattern.matcher(value);
            while(matcher.find())
                result += Long.parseLong(value);
        }
        System.out.println(result);

        //Part 2
        result = 0;
        pattern = Pattern.compile("^(\\d+)\\1+$");
        for(String value : values) {
            Matcher matcher = pattern.matcher(value);
            while(matcher.find())
                result += Long.parseLong(value);
        }
        System.out.println(result);

    }
}
