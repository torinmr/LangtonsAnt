import java.awt.Color;

public class LangtonsBoard
{
    private Ant ant;
    private int[][] board; // 0 = dead; 1 = alive
    private int x; // x size
    private int y; // y size
    private int animationDelay; // millisecond delay for animation.
    private int[] instructions; // instructions for the ant: 1 = R, 0 = L.
    private Color[] colors; // the colors used for successive states.
    
    // code shared by all constructors
    private void constructHelper(int delay, Ant anAnt, int[] instructions)
    {
        // set up animation space
        this.animationDelay = delay;
        StdDraw.setXscale(-.5, x-.5);
        StdDraw.setYscale(-.5, y-.5);
        StdDraw.show(animationDelay);
        
        // import ant and instructions
        this.ant = new Ant(anAnt.i, anAnt.j, anAnt.direction);
        this.instructions = new int[instructions.length];
        for (int i = 0; i < instructions.length; i++)
            this.instructions[i] = instructions[i];
        
        // create color matrix
        if (instructions.length < 2)
            throw new RuntimeException("Instructions must be at least "
                                           + "of length 2.");
        colors = new Color[instructions.length - 1];
        for (int i = 0; i < colors.length; i++)
        {
            Color temp = new Color((int) (Math.random()*256),
                                   (int) (Math.random()*256),
                                   (int) (Math.random()*256));
            colors[i] = temp;
        }
        
    }
    
    // create a blank board of specified dimension, with the given Ant.
    public LangtonsBoard(int xDim, int yDim, int delay, Ant ant,
                         int[] instructions)
    {
        this.x = xDim;
        this.y = yDim;
        board = new int[y][x];
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                board[i][j] = 0;
            }
        }
        constructHelper(delay, ant, instructions);
    }
    
    // create a board from the given matrix.
    public LangtonsBoard(int[][] start, int delay, Ant ant, int[] instructions)
    {
        this.x = start[0].length;
        this.y = start.length;
        board = new int[y][x];
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++) board[i][j] = start[i][j];
        }
        constructHelper(delay, ant, instructions);
    }
    
    // create a board from the given matrix, padding with empty cells
    // to the specified dimensions.
    public LangtonsBoard(int[][] start, int xDim, int yDim, int delay, Ant ant,
                         int[] instructions)
    {
        this.x = xDim;
        this.y = yDim;
        int width = start[0].length;
        int height = start.length;
        if (x < width || y < height)
            throw new RuntimeException("X and Y dimensions must be at least"
                                           +" as large as the input matrix.");
        int startX = (x - width)/2;
        int startY = (y - height)/2;
        board = new int[y][x];
        
        // initialize board to zero
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                board[i][j] = 0;
            }
        }
        // initialize center squares to contents of matrix
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
                board[i + startY][j + startX] = start[i][j];
        }
        constructHelper(delay, ant, instructions);
    }
    
    public void step()
    {
        int state = board[ant.i][ant.j];
        if (instructions[state] == 1) ant.right();
        else ant.left();
        
        if (state + 1 == instructions.length)
            board[ant.i][ant.j] = 0;
        else board[ant.i][ant.j]++;
        
        ant.move();
    }
    
    public void show()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(x/2 - .5, y/2 - .5, x/2, y/2);
        
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if (board[i][j] > 0)
                {
                    StdDraw.setPenColor(colors[board[i][j] - 1]);
                    StdDraw.filledSquare(j, y - 1 - i, .5);
                }
            }
        }
        
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(ant.j, y - 1 - ant.i, .5);
        StdDraw.show(animationDelay);
    }
    
    public static void main(String[] args)
    {   
	System.out.println("Please input the desired (integer) board size.");
	System.out.println("    (try 100 to start with):");
        int size = StdIn.readInt();
        Ant ant = new Ant(size/2, size/2, 2);
        System.out.println("Please input the desired 'DNA' for you ant, "); 
	System.out.println("as a series of the letters 'R' and 'L':");
	System.out.println("   'RL' produces the classic Langton's Ant.");
	System.out.println("   'RLR', 'LLRR', 'LRRRRRLLR', 'LLRRRLRLRLLR', and 'RRLLLRLLLRRR'");
	System.out.println("        all produce interesting patterns.");
	System.out.println("   - Source: Wikipedia");
        String dna = StdIn.readString();
        int length = dna.length();
        int[] instructions = new int[length];
        for (int i = 0; i < length; i++)
        {
            if (dna.charAt(i) == 'R') instructions[i] = 1;
            else if (dna.charAt(i) == 'L') instructions[i] = 0;
            else throw new RuntimeException("Instructions must consist only of "
                                                + "the characters R and L.");
        }
        System.out.println("Please input the desired step size.");
	System.out.println("    '1' animates every step, larger numbers " + 
			   "go faster)");
        int stepAmount = StdIn.readInt();
        LangtonsBoard board = new LangtonsBoard(size, size, 1, ant, instructions);
        while (true)
        {
            board.show();
            for (int i = 0; i < stepAmount; i++) { board.step(); }
        }
    }
}