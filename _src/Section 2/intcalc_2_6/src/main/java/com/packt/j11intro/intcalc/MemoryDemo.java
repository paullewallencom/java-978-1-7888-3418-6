package com.packt.j11intro.intcalc;

public class MemoryDemo {
    public static void main(String[] args) {
        final int numContainers = 80_000;
        Container[] containers = new Container[numContainers];
        for (int i = 0; i < numContainers; i++) {
            Container c = new Container();
            c.setD(i);
            containers[i] = c;
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int idx = i % numContainers;
            Container c = containers[idx];
            c.setD(c.getD() + 0.1);

            if (i % 1_000_000 == 0) {
//                System.out.println("Will call gc! i = " + i);
                System.gc();
            }
        }
        System.out.println("Took " + (System.currentTimeMillis() - start) + "ms");
    }
}

class Container {
    private double d;

    public void setD(double d) {
        this.d = d;
    }

    public double getD() {
        return this.d;
    }
}
