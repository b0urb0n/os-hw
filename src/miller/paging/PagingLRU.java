package miller.paging;

import java.util.ArrayList;
import java.util.HashMap;

public class PagingLRU extends PagingAlgorithm implements PagingAlgorithmInterface {

  public PagingLRU (ArrayList<Integer> refString) {
    super(refString);
  }

  @Override
  public void simulate(int numOfFrames) {
    ArrayList<Integer> currentFrames;
    Integer newFrame;
    Integer victimFrame;
    Integer victimIndex;
    Integer iteration = 0;
    
    setupFrameTable(numOfFrames);
    
    HashMap<Integer, Integer> frameUsage = new HashMap<Integer, Integer>();
    for (Integer i: refString) {
      frameUsage.put(i, 0);
    }
    
    for (int i=0; i<refString.size(); i++) {
      if (i != 0){ // copy previous column to current
        frameTable.add(new ArrayList<Integer>(frameTable.get(i - 1)));
      }
      
      currentFrames = frameTable.get(i);
      newFrame = refString.get(i);
      
      System.out.println(String.format(REFERENCED_MSG, newFrame));
      
      // set iteration
      frameUsage.put(newFrame, iteration++);
      
      if (currentFrames.contains(newFrame)) { // hit
        System.out.println(String.format(PAGE_HIT_MSG, newFrame));
        pageFaultList.add(-1);
        victimFrameList.add(-1);
      } else { // page fault
        System.out.println(String.format(PAGE_MISS_MSG, newFrame));
        System.out.println(PAGE_FAULT_MSG);
                        
        if (framesAreFull(currentFrames)) { // swap or load
          victimFrame = getVictimFrame(currentFrames, frameUsage);
          victimIndex = currentFrames.indexOf(victimFrame);
          
          System.out.println(String.format(SWAP_MSG, newFrame, currentFrames.get(victimIndex)));
          System.out.println(String.format(VICTIM_FRAME_MSG, victimFrame));          
          pageFaultList.add(1);
          victimFrameList.add(victimFrame);
          currentFrames.remove(victimFrame);
          currentFrames.add(victimIndex, newFrame);
        } else {
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
    // for LRU this should get the least recently used frame
    Integer victim = null;
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
