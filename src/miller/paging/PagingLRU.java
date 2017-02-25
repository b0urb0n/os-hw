package miller.paging;

import java.util.ArrayList;
import java.util.HashMap;

public class PagingLRU extends PagingAlgorithm implements PagingAlgorithmInterface {

  public PagingLRU (ArrayList<Integer> refString) {
    super(refString);
  }

  @Override
  public void simulate(int numOfFrames) {
    Integer victimFrame;
    Integer victimIndex;
    Integer iteration = 0;
    
    setup(numOfFrames);
    
    HashMap<Integer, Integer> frameUsage = new HashMap<Integer, Integer>();
    for (Integer i: refString) {
      frameUsage.put(i, 0);
    }
    
    for (int i=0; i<refString.size(); i++) {
      if (i != 0){ // copy previous column to current
        table.add(new ArrayList<Integer>(table.get(i - 1)));
      }
      
      ArrayList<Integer> currentFrames = table.get(i);
      Integer newFrame = refString.get(i);
      
      System.out.println(String.format(REFERENCED_MSG, newFrame));
      
      // set iteration
      frameUsage.put(newFrame, iteration++);
      
      if (currentFrames.contains(newFrame)) {
        // already loaded
        System.out.println(String.format(PAGE_HIT_MSG, newFrame));
        pageFaultList.add(-1);
        victimFrameList.add(-1);
      } else {
        // page fault
        System.out.println(String.format(PAGE_MISS_MSG, newFrame));
        System.out.println(PAGE_FAULT_MSG);
                        
        // determine if we need to swap a frame
        if (framesAreFull(currentFrames)) {
          // swap frame
          // get victim frame
          victimFrame = getVictimFrame(currentFrames, frameUsage);
          victimIndex = currentFrames.indexOf(victimFrame);
          
          System.out.println(String.format(SWAP_MSG, newFrame, currentFrames.get(victimIndex)));
          System.out.println(String.format(VICTIM_FRAME_MSG, victimFrame));          
          pageFaultList.add(1);
          victimFrameList.add(victimFrame);
          currentFrames.remove(victimFrame);
          currentFrames.add(victimIndex, newFrame);
        } else {
          // load frame directly
          System.out.println(String.format(LOAD_MSG, newFrame, 0));
          pageFaultList.add(1);
          victimFrameList.add(-1); 
          currentFrames.add(getFirstEmptyFrame(currentFrames), newFrame);
          currentFrames.remove(currentFrames.size() - 1);
        }
      }
    }
  }

  private Integer getVictimFrame(ArrayList<Integer> currentFrames, HashMap<Integer, Integer> frameUsage) {
    Integer victim = 0;
    Integer lowest = Integer.MAX_VALUE;
    for (Integer frame: currentFrames) {
      if (frameUsage.get(frame) < lowest) {
        lowest = frameUsage.get(frame);
        victim = frame;
      }
    }
    return victim;
  }
}
