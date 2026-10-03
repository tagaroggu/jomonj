package junipyr.jomonj;

public record Color(int r, int g, int b) {
    public static Color fromString(String input) {

        if (input.indexOf("0x") == 0) {
            input = input.substring(2);
        }

        return new Color(
            Integer.parseInt(input.substring(0, 2), 16),
            Integer.parseInt(input.substring(2, 4), 16),
            Integer.parseInt(input.substring(4), 16)
        );
    }
}
