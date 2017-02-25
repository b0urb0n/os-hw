package miller.paging;

public class Paging {
  private static final String HELP = "Usage:\tjava FinalProject <N frames>\n\n"
      + "\t* N frames will control how much paging will happen in order to solve the problem.\n";

  public static void main(String[] args) {
    if (args.length == 1) {
      Integer physicalFrames = Integer.valueOf(args[0]);
      
      Menu m = new Menu(physicalFrames);
      while (true) {
        m.display();
      }
    } else {
      System.out.println(HELP);
    }
  }
}