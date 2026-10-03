package junipyr.jomonj;

import java.time.Instant;
import java.util.Random;

public class Args {
    private static Args instance = null;
    
    public int height = 1080;
    public int width = 1920;
    public int minDensity = 25;
    public int maxDensity = 100;
    public int minRadius = 5;
    public int maxRadius = 75;
    public int waveWidth = 3;
    public int waveHeight = 50;
    public int strokeWidth = 3;
    public Color darkColor = new Color(0x54, 0x44, 0x2B);
    public Color lightColor = new Color(0xA9, 0x71, 0x4B);
    public long seed;
    public String outfile;
    public boolean verbose = false;
    public boolean toStdout = false;
    public boolean randomSort = false;
    public int borderCount = 0;
    public Random rng;

    private Args(String[] args) {
        this.seed = Instant.now().toEpochMilli();

        for (int idx = 0; idx < args.length; idx++) {
            switch (args[idx]) {
                case "-x":
                    this.width = Integer.parseInt(args[++idx]);
                    break;
                case "-y":
                    this.height = Integer.parseInt(args[++idx]);
                    break;
                case "-d":
                    this.minDensity = Integer.parseInt(args[++idx]);
                    break;
                case "-D":
                    this.maxDensity = Integer.parseInt(args[++idx]);
                    break;
                case "-r":
                    this.minRadius = Integer.parseInt(args[++idx]);
                    break;
                case "-R":
                    this.maxRadius = Integer.parseInt(args[++idx]);
                    break;
                case "-s":
                    this.seed = Long.parseLong(args[++idx]);
                    break;
                case "-S":
                    this.strokeWidth = Integer.parseInt(args[++idx]);
                    break;
                case "-c":
                    this.darkColor = Color.fromString(args[++idx]);
                    break;
                case "-C":
                    this.lightColor = Color.fromString(args[++idx]);
                    break;
                case "-o":
                    this.outfile = args[++idx];
                    if (this.outfile.compareTo("-") == 0) {
                        this.toStdout = true;
                    }
                    break;
                case "-v":
                    this.verbose = true;
                    break;
                case "-W":
                    this.waveHeight = Integer.parseInt(args[++idx]);
                    break;
                case "-w":
                    this.waveWidth = Integer.parseInt(args[++idx]);
                    break;
                case "-b":
                    this.borderCount = Integer.parseInt(args[++idx]);
                    break;
                case "-1":
                    this.randomSort = true;
                    break;
                case "-h":
                    System.out.println("JomonJ: a port of a CLI program to generate jomon pottery-/BOTW-/TOTK-inspired patterns\n");

                    System.out.println("Flags:");
                    System.out.println("\t-x: Width of output image in pixels, default is 1920");
                    System.out.println("\t-y: Height of output image in pixels, default is 1080");
                    System.out.println("\t-d: Minimum density (amount) of circles in image, default is 25");
                    System.out.println("\t-D: Maximum density (amount of circles in image, default is 100");
                    System.out.println("\t-r: Minimum radius (size) of circles, rounds to an odd number, default is 5");
                    System.out.println("\t-R: Maximum radius (size) of circles, rounds to an odd number, default is 75");
                    System.out.println("\t-s: Sets the seed of the random generator, default is the current time in milliseconds");
                    System.out.println("\t-S: Stroke width (line width) in pixels, default is 3");
                    System.out.println("\t-c: The darker, \"background\" color, default is 0x54442B");
                    System.out.println("\t-C: The lighter, \"foreground\" color, default is 0xA9714B");
                    System.out.println("\t-o: Outfile, where to put the resulting image, default is the seed with \".ppm\" at the end. Pass just \"-\" to output to stdout");
                    System.out.println("\t-v: Verbose, gives addition inforrmation during generation");
                    System.out.println("\t-h: Prints this help info and exits");
                    System.out.println("\t-b: Adds a border around the edges of the image, 0 disables it, default is 0");
                    System.out.println("\t-W: Wave height, how long a wave cycle is, default is 50");
                    System.out.println("\t-w: Wave width, how far horizontally a wave travels, default is 3");
                    System.out.println("\t-1: Enables the random sorter. By default, circles are sorted by size");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid options");
                    System.exit(1);
                    break;

            }
        }

        if (this.outfile == null) {
            this.outfile = this.seed + ".ppm";
        }

        this.rng = new Random(this.seed);
    }

    public static Args getArgs(String[] args) {
        if (instance == null) {
            instance = new Args(args);
        }

        return instance;
    }

    
}
