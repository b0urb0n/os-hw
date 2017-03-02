package miller.paging;

public class Paging {
  private static Integer physicalFrames;
  private static final String HELP = "Usage:\tjava FinalProject <N frames>\n\n"
      + "\t* N frames will control how much paging will happen in order to solve the problem.\n";

  public static void main(String[] args) {
    if (args.length == 1) {
      try {
        physicalFrames = Integer.valueOf(args[0]);
      } catch (Exception e) {
        System.out.println(HELP);
        System.exit(1);
      }
      
      Menu m = new Menu(physicalFrames);
      while (true) {
        m.display();
      }
    } else {
      System.out.println(HELP);
    }
  }
}