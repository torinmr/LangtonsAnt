public class Ant
    {
        int i; // the current coordinates of the ant.
        int j;
        int direction;
        // the direction of the ant: 1-4 equal N-W, going clockwise.
        
        public Ant(int i, int j, int d)
        {
            this.i = i;
            this.j = j;
            this.direction = d;
        }
        
        // move the ant based on its current direction.
        public void move()
        {
            if (direction == 1) i--;
            if (direction == 2) j++;
            if (direction == 3) i++;
            if (direction == 4) j--;
        }
        public void left()
        {
            if (direction == 1) direction = 4;
            else direction--;
        }
        
        public void right()
        {
            if (direction == 4) direction = 1;
            else direction++;
        }  
    }