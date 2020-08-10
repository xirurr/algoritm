package Percolation;

public class Percolation {

    private final int[] map;
    private final int rows;
    private final int max;
    private final int[] mincol;
    private final int[] maxcol;
    private int numberOfOpenSites = 0;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        rows = n;
        map = new int[n * n];
        max = (n * n) - 1;
        for (int i = 0; i < map.length; i++) {
            map[i] = -1;
        }
        mincol = new int[n];
        maxcol = new int[n];
        for (int i = 0; i < mincol.length; i++) {
            mincol[i] = i;
        }
        for (int i = 0; i < maxcol.length; i++) {
            maxcol[i] = max - i;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= Integer.MAX_VALUE || col >= Integer.MAX_VALUE || row > rows || col > rows) {
            throw new IllegalArgumentException();
        }

        int decValue;
        if (row == 1) decValue = col - 1;
        else decValue = rows * (row - 1) + (col - 1);

        if (map[decValue] != -1) {
            return;
        }
        numberOfOpenSites++;
        int[] nearRoot = checkNear(decValue);

        if (nearRoot.length == 0) {
            map[decValue] = decValue;
            return;
        }

        int minRoot = getMIn(nearRoot);
        if (minRoot <= decValue) {
            for (int i = 0; i < nearRoot.length; i++) {
                int tmpMinValue = map[nearRoot[i]];
                for (int i1 = 0; i1 < map.length; i1++) {
                    if (map[i1] == tmpMinValue && map[i1] != minRoot) {
                        map[i1] = minRoot;
                    }
                }

                //      map[nearRoot[i]] = minRoot;
            }
            map[decValue] = minRoot;
        } else {
            for (int i = 0; i < nearRoot.length; i++) {
                int tmpMinValue = map[nearRoot[i]];
                //    map[nearRoot[i]] = decValue;
                for (int i1 = 0; i1 < map.length; i1++) {
                    if (map[i1] == tmpMinValue) {
                        map[i1] = decValue;
                    }
                }
            }
            map[decValue] = decValue;
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= Integer.MAX_VALUE || col >= Integer.MAX_VALUE || row > rows || col > rows) {
            throw new IllegalArgumentException();
        }

        int decValue;
        if (row == 1) decValue = col - 1;
        else decValue = rows * (row - 1) + (col - 1);
        return map[decValue] != -1;

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= Integer.MAX_VALUE || col >= Integer.MAX_VALUE || row > rows || col > rows) {
            throw new IllegalArgumentException();
        }

        int decValue;
        if (row == 1) decValue = col - 1;
        else decValue = rows * (row - 1) + (col - 1);
        return isFull(decValue);
    }

    private boolean isFull(int decValue) {
        if (map[decValue] == -1) {

            return false;
        }

        if (map[decValue] <= mincol[mincol.length - 1]) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }


    // does the system percolate?
    public boolean percolates() {
        for (int decValue : maxcol) {
            if (map[decValue] != -1 && isFull(decValue)) {
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);

        percolation.open(2, 2);
        percolation.open(2, 1);
    }

    private int[] checkNear(int a) {
        int n = a - rows;
        int e = a + 1;
        double w = a - 1;
        int s = a + rows;
        int[] integers = new int[4];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = -2;
        }

        int size = 0;

        if (n >= 0) integers[0] = n;
        // проверка, что не вылезаем за границы диапазона и не берем число из следующей строки
        if (e <= max && (((double) e / rows) % 1 != 0)) integers[1] = e;
        if (w >= 0 && (((double) a / rows) % 1 != 0)) integers[2] = (int) w;
        if (s <= max) integers[3] = s;


        for (int i = 0; i < integers.length; i++) {
            if (integers[i] != -2) {
                if (map[integers[i]] != -1) {
                    size++;
                }
            }
        }

        int[] tmpintegers = new int[size];

        int counter = 0;
        for (int i = 0; i < integers.length; i++) {
            if (integers[i] != -2) {
                if (map[integers[i]] != -1) {
                    tmpintegers[counter] = integers[i];
                    counter++;
                }
            }
        }
        return tmpintegers;
    }


    private int getMIn(int[] array) {
        int minValue = map[array[0]];
        for (int i = 0; i < array.length; i++) {
            if (map[array[i]] < minValue) {
                minValue = map[array[i]];
            }
        }
        return minValue;
    }
}