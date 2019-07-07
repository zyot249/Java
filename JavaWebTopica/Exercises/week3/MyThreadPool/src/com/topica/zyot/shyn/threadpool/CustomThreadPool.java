package com.topica.zyot.shyn.threadpool;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class CustomThreadPool {
    //Thread pool size
    private int corePoolSize;
    private int maxPoolSize;
    private AtomicInteger activeThreads;

    //Internally pool is an array
    private ArrayList<WorkerThread> workers;

    // FIFO ordering
    private final LinkedBlockingQueue<Runnable> queue;

    public CustomThreadPool(int corePoolSize, int maxPoolSize, int queueSize) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        queue = new LinkedBlockingQueue<>(queueSize);
        workers = new ArrayList<>();

        for (int i = 0; i < corePoolSize; i++) {
            workers.add(new WorkerThread("Thread" + i, true));
            workers.get(i).start();
        }
        this.activeThreads = new AtomicInteger(corePoolSize);
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            while (queue.remainingCapacity() == 0) {
                if (activeThreads.get() < maxPoolSize && activeThreads.get() < queue.size()) {
                    workers.add(new WorkerThread("Thread" + activeThreads.get(), false));
                    workers.get(activeThreads.get()).start();
                    activeThreads.set(workers.size());
                }
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(task);
            queue.notifyAll();
        }
    }

    private class WorkerThread extends Thread {
        private boolean core;
        private String name;
        private boolean active;

        public boolean isActive() {
            return active;
        }

        WorkerThread(String name, boolean isCore) {
            active = true;
            this.name = name;
            core = isCore;
        }

        public void run() {

            Runnable task = null;

            while (true) {
                synchronized (queue) {
                    queue.notifyAll();
                    task = queue.poll();
                }
                try {
                    if (task != null) {
                        System.out.println("Ready to run task by " + name + ". Number of active threads: " + activeThreads.get());
                        task.run();
                    } else {
                        if (!isActive()) {
                            System.out.println(name + " main break");
                            workers.remove(this);
                            activeThreads.set(workers.size());
                            System.out.println("Number of active threads: " + activeThreads.get());
                            break;
                        } else {
                            synchronized (queue) {
                                while (queue.isEmpty()) {
                                    try {
                                        if (core)
                                            queue.wait();
                                        else {
                                            if (active) {
                                                queue.wait(6000);
                                                if (queue.isEmpty()) {
                                                    System.out.println("Ready to stop");
                                                    active = false;
                                                }
                                            }
                                            break;
                                        }
                                        if (!isActive()) {
                                            System.out.println(name + " semi break");
                                            break;
                                        }
                                        System.out.println(name + " - empty");
                                    } catch (InterruptedException e) {
                                        System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }

        public void close() {
            active = false;
        }
    }

    public void shutdown() {
        for (WorkerThread worker : workers) {
            worker.close();
        }
        synchronized (queue){
            queue.notifyAll();
        }
    }
}
