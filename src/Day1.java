import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day1 {
    public static void main(String[] args) throws IOException {
        List<String> instructions = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get("./input/Day1.txt"))) {
            lines.forEach(instructions::add);
        }
        int current = 50;
        int password = 0;
        for (String instruction : instructions) {
            int steps = Integer.parseInt(instruction.substring(1));
            if(instruction.charAt(0) == 'L')
                current = (((current - steps) % 100) + 100) % 100;
            else
                current = (current + steps)%100;
            if(current == 0)
                password++;
        }
        System.out.println(password);

        //Part 2
        current = 50;
        password = 0;
        for (String instruction : instructions) {
            int steps = Integer.parseInt(instruction.substring(1));
            if(instruction.charAt(0) == 'L') {
                password += (steps+(100-current)%100)/100;
                current = (((current - steps) % 100) + 100) % 100;
            }else {
                password += (steps+current)/100;
                current = (current + steps) % 100;
            }
        }
        System.out.println(password);
    }
}
