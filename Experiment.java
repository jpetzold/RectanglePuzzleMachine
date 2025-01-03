import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Experiment extends JPanel {
    private static final Rectangle bigRectangle = new Rectangle(50, 50, 400, 600);
    private  List<MyRect> rectangles;
    private Random random = new Random();

    private int last_score = 9999999;

    private  int lowestY = 9999999;
    private  int lastOverlap = 9999999;

    public  static  int yToBeat = 9999999;

    public int expnum;

    public  boolean visualize;

  private  JFrame frame;





    public Experiment(int expnum, int[][] rectanglesDimensions)
    {
        this.expnum=expnum;
        if(expnum>=1)
        {visualize=false;}
        else {
            visualize=true;
        }


if(visualize) {
    frame = new JFrame(expnum + " Experiment " + lowestY);
    frame.add(this);

    int line = expnum / 4;
    int pos = expnum % 4;

    frame.setSize(500, 700);
    frame.setLocation(500 * pos, 5 + (line * 700));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
}
        populate(rectanglesDimensions);


    }

    public Experiment performTask() {

        int i = 0;
        while (i < 2000000) {
            moveRectangles();
            i++;

            if(visualize && i % 1000 ==0){
                frame.setTitle("Rectangle Visualizer: " + lowestY);
                frame.repaint();
            }

        }
        System.out.println("completed Experiment expnum " +expnum+ " with result: "+lowestY);
        return this;
    }


    private void moveRectangles() {
        List<MyRect> clonedRectangles = new ArrayList<>();
        for (MyRect rectangle : rectangles) {
            clonedRectangles.add(rectangle.clone());
        }


        int candidate = random.nextInt(clonedRectangles.size());
        Rectangle rect = clonedRectangles.get(candidate);
        int[] scores = moveAndCalculate(rect, clonedRectangles);

        //if improvement then
        if (scores[0] <= last_score) {
            rectangles = clonedRectangles; //todo: assumed that the movement is more often worse than better, we should keep the original tucked away and manipulate rectangles directly, whenever a solution is discarded we can reset the tucked away to origin
            last_score = scores[0];
            lowestY = scores[1];
            lastOverlap = scores[3];
        }

    }

    private  int[] moveAndCalculate(Rectangle rect, List<MyRect> clonedRectangles) {
        moveRect(rect);
        //search for lowest y rectangle and calc sumy
        int localLowY = 0;
        int i = 0;
        int sumY = 0;
        for (Rectangle rect2 : clonedRectangles) {

            sumY += rect2.y + rect2.height;

            if (localLowY < rect2.y + rect2.height) localLowY = rect2.y + rect2.height;
            i++;
        }
        int overlap = calculateOverlapScore(clonedRectangles);
        return new int[]{(overlap * 100) + localLowY + sumY, localLowY, sumY, overlap};

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        // Draw the big rectangle
        g2d.setColor(Color.BLACK);
        g2d.drawRect(bigRectangle.x, bigRectangle.y, bigRectangle.width, bigRectangle.height);

        // Draw the smaller rectangles
        g2d.setColor(Color.BLUE);
        for (Rectangle rect : rectangles) {
            g2d.drawRect(rect.x, rect.y, rect.width, rect.height);


        }
    }


    private void moveRect(Rectangle rect) {
        // Generate random directions
        int dx = random.nextInt(150) - random.nextInt(300);
        int dy = random.nextInt(200) - random.nextInt(390);

        // Update rectangle position
        rect.x += dx;
        rect.y += dy;

        //rotate sometimes
        if(random.nextBoolean())
        {
            int tmp= rect.height;
            rect.height=rect.width;
            rect.width=tmp;
        }


        //Keep the rectangle within bounds
        if (rect.x < bigRectangle.x) rect.x = bigRectangle.x;
        if (rect.y < bigRectangle.y) rect.y = bigRectangle.y;
        if (rect.x + rect.width > bigRectangle.x + bigRectangle.width)
            rect.x = bigRectangle.x + bigRectangle.width - rect.width;
        if (rect.y + rect.height > bigRectangle.y + bigRectangle.height)
            rect.y = bigRectangle.y + bigRectangle.height - rect.height;

    }


    private int calculateOverlapScore(List<MyRect> rectangles) {
        int overlapScore = 0;
        // Compare every pair of rectangles
        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = i + 1; j < rectangles.size(); j++) {
                Rectangle r1 = rectangles.get(i);
                Rectangle r2 = rectangles.get(j);

                // Find the intersection
                Rectangle intersection = r1.intersection(r2);

                // Check if there is an overlap
                if (intersection.width > 0 && intersection.height > 0) {
                    int overlapArea = intersection.width * intersection.height;
                    overlapScore += overlapArea;
                }
            }
        }
        return overlapScore;
    }


    private void populate(int[][] rectanglesDimensions)
    {
        rectangles = new ArrayList<>();

        for (int[] dimensions : rectanglesDimensions) {

            if (random.nextBoolean()) {
                addRectangle(dimensions[0], dimensions[1]);
            } else {
                addRectangle(dimensions[1], dimensions[0]);
            }
        }

    }


    private void addRectangle(int w, int h) {
        rectangles.add(new MyRect(50 + random.nextInt(300), 50 + random.nextInt(500), w, h));

    }

    public int getScore(){
        return lowestY;
    }

    public void show(int xpos, int ypos){
        frame = new JFrame(expnum + " Experiment  Score:" + lowestY+", Overlap:"+lastOverlap);
        frame.add(this);

        int line = expnum / 4;
        int pos = expnum % 4;

        frame.setSize(500, 700);
        frame.setLocation(xpos, ypos);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


}
