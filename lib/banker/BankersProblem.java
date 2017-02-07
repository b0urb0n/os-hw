package lib.banker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class BankersProblem {
  private static final Integer RESOURCE_MAX = 5;
  private static final Integer RESOURCE_MIN = 0;
  
  private ArrayList<BankersProblemProcess> processes = new ArrayList<BankersProblemProcess>();
  private ArrayList<Integer> maxResources;
  
  
  private static Integer getRandomInteger(){
    return ThreadLocalRandom.current().nextInt(RESOURCE_MIN, RESOURCE_MAX + 1);
  }
  
  private static Integer getRandomInteger(Integer max){
    return ThreadLocalRandom.current().nextInt(RESOURCE_MIN, max + 1);
  }
  
  public BankersProblem(){}
  
  public BankersProblem(ArrayList<BankersProblemProcess> processes){
    this.processes = processes;
  }
  
  public BankersProblem(Integer numOfProc, Integer numOfRes){
    for (int i=0; i<numOfProc; i++){
      ArrayList<Integer> tempMaxClaimList = new ArrayList<Integer>();
      ArrayList<Integer> tempAllocationList = new ArrayList<Integer>();
      
      for (int j=0; j<numOfRes; j++){
        tempMaxClaimList.add(getRandomInteger());
        tempAllocationList.add(getRandomInteger(tempMaxClaimList.get(tempMaxClaimList.size() - 1)));
      }
      String tempName = String.format("P%d", i);
      
      processes.add(new BankersProblemProcess(tempName, tempMaxClaimList, tempAllocationList));
    }
  }
  
  public BankersProblem(String fileName){
    File file;
    Scanner input = null;

    String[] lineParts;
    String name;
    List<String> firstSet;
    List<String> secondSet;
    
    maxResources = new ArrayList<Integer>();
            
    try {
      file = new File(fileName);
      input = new Scanner(file);
    } catch (FileNotFoundException e) {
      System.out.println(String.format("%s: file not found.", fileName));
      System.exit(1);
    }

    while(input.hasNext()) {
      ArrayList<Integer> maxClaim = new ArrayList<Integer>();
      ArrayList<Integer> allocation = new ArrayList<Integer>();
      
      String nextLine = input.nextLine();
      if (nextLine.startsWith("#") || nextLine.isEmpty()){
        continue;
      }
      
      lineParts = nextLine.split("\t");
      name = lineParts[0].replace(":", "");
      
      firstSet = Arrays.asList(lineParts[1].replace("[", "").replace("]", "").split(", "));
      if (name.toLowerCase().startsWith("max res")){
        for(String s : firstSet) maxResources.add(Integer.valueOf(s));
        continue;
      } else if (lineParts.length > 2){
        secondSet = Arrays.asList(lineParts[2].replace("[", "").replace("]", "").split(", "));
        
        for(String s : firstSet) maxClaim.add(Integer.valueOf(s));
        for(String s : secondSet) allocation.add(Integer.valueOf(s));
        
        BankersProblemProcess bp = new BankersProblemProcess(name, maxClaim, allocation);
        processes.add(bp);
      }
    }
    input.close();
    
    if (maxResources == null){
      System.out.println("Missing parameters in file (Max Resources)");
      System.exit(1);
    }
  }
  
  public void addProcess(BankersProblemProcess bp){
    processes.add(bp);
  }
  
  public ArrayList<BankersProblemProcess> getProcesses(){
    return processes;
  }
  
  public ArrayList<Integer> getMaxResources(){
    return maxResources;
  }
  
  public ArrayList<Integer> getAvailableResources(){
    ArrayList<Integer> result = new ArrayList<Integer>(maxResources);
    for (BankersProblemProcess p: processes){
      for (int i=0; i<maxResources.size(); i++){
        result.set(i, result.get(i) - p.allocationList.get(i));
      }
    }
    return result;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("#NAME\tMax Claim\tAllocation\tNeed\n");
    sb.append("#----\t---------\t----------\t----\n");
    for (BankersProblemProcess proc: processes){
      sb.append(proc.toString() + "\n");
    }
    if (maxResources != null){
      sb.append("\nMax Resources: " + maxResources.toString() + "\n");
    }
    return sb.toString();
  }
}
