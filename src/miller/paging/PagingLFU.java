package miller.paging;

import java.util.ArrayList;
import java.util.HashMap;

public class PagingLFU extends PagingAlgorithm implements PagingAlgorithmInterface {
  
  public PagingLFU (ArrayList<Integer> refString) {
    super(refString);
  }

  @Override
  public void simulate(int numOfFrames) {
    Integer victimFrame;
    Integer victimIndex;
    
    setup(numOfFrames);
    
    HashMap<Integer, Integer> pageUsage = new HashMap<Integer, Integer>();
    for (Integer i: refString) {
      pageUsage.put(i, 0);
    }
    
    for (int i=0; i<refString.size(); i++) {
      if (i != 0){ // copy previous column to current
        table.add(new ArrayList<Integer>(table.get(i - 1)));
      }
      
      ArrayList<Integer> currentFrames = table.get(i);
      Integer page = refString.get(i);
      
      System.out.println(String.format(REFERENCED_MSG, page));
      
      // increment page usage by 1
      pageUsage.put(page, pageUsage.get(page) + 1);
      
      if (currentFrames.contains(page)) {
        // already loaded
        System.out.println(String.format(PAGE_HIT_MSG, page));
        pageFaultList.add(-1);
        victimFrameList.add(-1);
      } else {
        // page fault
        System.out.println(String.format(PAGE_MISS_MSG, page));
        System.out.println(PAGE_FAULT_MSG);
                        
        // determine if we need to swap a frame
        if (framesAreFull(currentFrames)) {
          // swap frame
          // get victim frame
          victimFrame = getVictimFrame(currentFrames, pageUsage);
          victimIndex = currentFrames.indexOf(victimFrame);
          
          System.out.println(String.format(SWAP_MSG, page, currentFrames.get(victimIndex)));
          System.out.println(String.format(VICTIM_FRAME_MSG, victimFrame));          
          pageFaultList.add(1);
          victimFrameList.add(victimFrame);
          currentFrames.remove(victimFrame);
          currentFrames.add(victimIndex, page);
        } else {
          // load frame directly
          System.out.println(String.format(LOAD_MSG, page, 0));
          pageFaultList.add(1);
          victimFrameList.add(-1); 
          currentFrames.add(getFirstEmptyFrame(currentFrames), page);
          currentFrames.remove(currentFrames.size() - 1);
        }
      }
    }
  }

  private Integer getVictimFrame(ArrayList<Integer> currentFrames, HashMap<Integer, Integer> pageUsage) {
    Integer victim = 0;
    Integer lowest = Integer.MAX_VALUE;
    for (Integer frame: currentFrames) {
      if (pageUsage.get(frame) < lowest) {
        lowest = pageUsage.get(frame);
        victim = frame;
      }
    }
    return victim;
  }
}
