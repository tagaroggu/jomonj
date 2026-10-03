package junipyr.jomonj;

public record Circle(int x, int y, int radius) implements Comparable<Circle> {
    public int compareTo(Circle o) {
        return Integer.compare(this.radius, o.radius);
    }
}
