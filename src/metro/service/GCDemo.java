package metro.service;

import metro.model.Station;

// CONCEPT: Garbage Collection & Object Reachability
public class GCDemo {

    public static void runDemo() {
        System.out.println("\n--- RUNNING GARBAGE COLLECTION & REACHABILITY DEMO ---");
        Runtime runtime = Runtime.getRuntime();

        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("1. Memory used before creating objects: %,d bytes\n", memBefore);

        // Create temporary station objects
        for (int i = 0; i < 50000; i++) {
            Station temp = new Station("Temp Station " + i, "Yellow", false, i * 0.5);
            // By the end of loop, 'temp' is dereferenced and eligible for Garbage Collection
        }

        long memAfterAlloc = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("2. Memory used after creating temporary objects: %,d bytes\n", memAfterAlloc);

        // CONCEPT: Requesting JVM Garbage Collection
        System.out.println("3. Invoking System.gc() to request JVM to clean unreferenced objects...");
        System.gc();

        try {
            Thread.sleep(500); // Small pause to allow GC to run
        } catch (InterruptedException ignored) {}

        long memAfterGC = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("4. Memory used after System.gc(): %,d bytes\n", memAfterGC);
        System.out.println("Garbage Collection Concept: When an object has no references pointing to it");
        System.out.println("(like temp in our loop), it becomes eligible for cleanup by the JVM Garbage Collector.");
        System.out.println("--- GARBAGE COLLECTION DEMO COMPLETED ---\n");
    }
}
