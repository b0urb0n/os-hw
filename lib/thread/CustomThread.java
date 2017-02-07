package lib.thread;

import java.util.concurrent.Semaphore;

class CustomThread implements Runnable {
  private static final String PRINT_INFO = "Thread %d: Iteration %d";

  private Integer id;
  private Integer iteration = 1;
  private Semaphore semaphore;

  public CustomThread(Integer id, Semaphore s){
    this.semaphore = s;
    this.id = id;
  }

  @Override
  public void run() {
    while (iteration <= ThreadSync.MAX_ITERATION) {
      if (semaphore.availablePermits() == id) {
        try {
          semaphore.acquire(id);
          System.out.println(String.format(PRINT_INFO, id, iteration++));

          if (id == ThreadSync.NUM_THREADS){
            semaphore.release(1);
          } else {
            semaphore.release(id + 1);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
