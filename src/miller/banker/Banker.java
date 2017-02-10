package miller.banker;

import java.util.ArrayList;

public class Banker {
  private static final String AVAILABLE = "Available resources: %s";
  private static final String SATISFIES = "%s satisfies the needs of %s";
  private static final String DONE = "%s done, releasing %s";
  private static final String SKIP = "Skipping %s. Lack of available resources";
  private static final String SAFE = "\n\nThis scheme is SAFE!\n\n";
  private static final String UNSAFE = "\n\nThis scheme is NOT SAFE!\n\n";
  private static final String HELP = "Usage:\tjava Homework4 (<process_num> <resourse_num> | <filename>)\n\n"
      + "\t* Providing a filename will test the provided banker's problem.\n"
      + "\t* Providing two integers will generate a random banker's problem (minus max resources) to stdout.";
    
  private static BankersProblem bankersProblem;
  
  
  public static void main(String[] args){    
    if (args.length == 2){
      Integer numOfProc = new Integer(args[0]);
      Integer numOfRes = new Integer(args[1]);

      bankersProblem = new BankersProblem(numOfProc, numOfRes);
      System.out.println(bankersProblem.toString());
    }else if (args.length == 1){
      bankersProblem = new BankersProblem(args[0]);
      System.out.println(bankersProblem.toString());
      
      solveBankersProblem(bankersProblem);
    }else{
      System.out.println(HELP);
    }
  }
  
  public static void solveBankersProblem(BankersProblem bp){
    boolean killSwitch = false;
    
    while (! bp.getAvailableResources().equals(bp.getMaxResources()) && ! killSwitch){
      killSwitch = true;

      for (BankersProblemProcess p: bp.getProcesses()){
        if (p.isDone())
          continue;
        
        System.out.println(String.format(AVAILABLE, bp.getAvailableResources()));
          
        if (satisfiesProcess(p.getNeed(), bp.getAvailableResources())){
          System.out.println(String.format(SATISFIES, bp.getAvailableResources(), p.getName()));
          
          for (int i=0; i<bp.getMaxResources().size(); i++){
            while (p.getNeed().get(i) != 0){
              p.allocationList.set(i, p.allocationList.get(i) + 1);
            }
          }
          
          System.out.println(String.format("\t* " + AVAILABLE, bp.getAvailableResources()));
          System.out.println(String.format("\t* " + DONE, p.getName(), p.getMaxClaimList()));

          p.done();
          for (int i=0; i<p.allocationList.size(); i++){
            p.allocationList.set(i, 0);
          }
          
          killSwitch = false;
        } else {
          System.out.println(String.format(SKIP, p.getName()));
        }
        System.out.println();
      }
    }
    
    if (killSwitch){
      System.out.println(UNSAFE);
    } else {
      System.out.println(SAFE);
    }
  }
  
  private static boolean satisfiesProcess(ArrayList<Integer> need, ArrayList<Integer> available){
    for (int i=0; i<need.size(); i++){
      if (need.get(i) > available.get(i))
        return false;
    }
    return true;
  }
}
