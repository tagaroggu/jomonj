package junipyr.jomonj;

import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JomonJ {
    public static void main(String[] args) {
        Args arg = Args.getArgs(args);

        int circleCount = arg.rng.nextInt(arg.minDensity, arg.maxDensity);
        if (arg.verbose) {
            System.out.println("Circle count: " + circleCount);
        }

        ArrayList<Circle> circleList = new ArrayList<Circle>(circleCount);

        for (int idx = 0; idx < circleCount; idx++) {
            int x = arg.rng.nextInt(0, arg.width);
            int y = arg.rng.nextInt(0, arg.height);
            int radius = arg.rng.nextInt(arg.minRadius, arg.maxRadius);

            if (radius % 2 == 0) {
                if (radius + 1 > arg.maxRadius) {
                    radius -= 1;
                } else if (radius - 1 < arg.minRadius) {
                    radius += 1;
                } else {
                    radius += idx % 2 == 1 ? 1 : -1;
                }
            }

            if (arg.verbose) {
                System.out.printf("Circle #%d: (%d, %d) R: %d\n", idx, x, y, radius);
            }

            circleList.add(new Circle(x, y, radius));
        }

        circleList.sort(null);
        byte[] imageBuffer = new byte[arg.width * arg.height * 3];

        for (int x = 0; x < arg.width; x++) {
            for (int y = 0; y < arg.height; y++) {
                Color color = pixelFunction(x, y, arg, circleList);
                //imageBuffer.put((byte)(color.r() & 0xFF));
                //imageBuffer.put((byte)(color.g() & 0xFF));
                //imageBuffer.put((byte)(color.b() & 0xFF));
                imageBuffer[(x * 3) + (y * arg.width * 3) + 0] = (byte)(color.r() & 0xFF);
                imageBuffer[(x * 3) + (y * arg.width * 3) + 1] = (byte)(color.g() & 0xFF);
                imageBuffer[(x * 3) + (y * arg.width * 3) + 2] = (byte)(color.b() & 0xFF);
            }
        }

        if (arg.toStdout) {
            // TODO: implement stdout
            System.exit(127);
        } else {
            try {
                File outfile = new File(arg.outfile);
                outfile.createNewFile();
                
                FileOutputStream outfileStream = new FileOutputStream(outfile);
                outfileStream.write("P6\n".getBytes());
                outfileStream.write(("" + arg.width + " " + arg.height + "\n").getBytes());
                outfileStream.write("255\n".getBytes());

                //byte[] byteArray = new byte[imageBuffer.remaining()];
                //imageBuffer.get(byteArray);

                //outfileStream.write(byteArray);
                outfileStream.write(imageBuffer);
                outfileStream.close();

                System.out.println(outfile.toString());
            } catch (IOException e) {
                // panic or dont idk
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    public static Color pixelFunction(int x, int y, Args args, ArrayList<Circle> circleList) {
        int bd = borderDistance(x, y, args);
        if (bd < (args.borderCount * args.strokeWidth)) {
            return distanceColor(bd, args);
        }

        for (int i = 0; i < circleList.size(); i++) {
            Circle circle = circleList.get(i);
            double dist = distance(x, y, circle.x(), circle.y());
            if (dist <= circle.radius() * args.strokeWidth) {
                return distanceColor(dist, args);
            } 
        }

        return columnColor(x, y, args);
    }

    public static int borderDistance(int x, int y, Args args) {
        int topDist = y;
        int bottomDist = args.height - 1 - y;
        int leftDist = x;
        int rightDist = args.width - 1 - x;

        int smallestDist = topDist;

        smallestDist = smallestDist < bottomDist ? smallestDist : bottomDist;
        smallestDist = smallestDist < leftDist ? smallestDist : leftDist;
        smallestDist = smallestDist < rightDist ? smallestDist : rightDist;

        return smallestDist;
    }

    public static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static Color distanceColor(double distance, Args args) {
        return (int)(distance / args.strokeWidth) % 2 == 1 ? args.lightColor : args.darkColor;
    }

    public static Color columnColor(int x, int y, Args args) {
        if (args.waveWidth > 0 && args.waveHeight > 0) {
            x += Math.round((double)args.waveWidth * Math.sin(((double)y / args.waveHeight) * Math.PI));
        }

        return Math.round((double)x / args.strokeWidth) % 2 == 1 ? args.lightColor : args.darkColor;
    }

}