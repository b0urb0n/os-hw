package miller.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Menu {  
  private Scanner input = new Scanner(System.in);
  private Path cwd = null;

  public void display() {
    System.out.println(MenuConstants.HEADER);
    System.out.print(MenuConstants.MENU);

    try{
      int cmd = Integer.parseInt(input.nextLine());
      parse(cmd);
    } catch (NumberFormatException e) {
      System.out.println(MenuConstants.ERROR_COMMAND);
      return;
    } 
  }
  
  private void parse(int command) {
    if (cwd == null && command != 1 && command != 0) {
      System.out.println(MenuConstants.ERROR_NO_CWD);
      return;
    }
    switch(command){
    case 0:
      System.exit(0);
      break;
    case 1:
      selectDirectory();
      break;
    case 2:
      listDirectory(cwd, false);
      break;
    case 3:
      listDirectory(cwd, true);
      break;
    case 4:
      deleteFile();
      break;
    case 5:
      displayFile();
      break;
    case 6:
      cryptFile();
      break;
    case 7:
      cryptFile();
      break;
    default:
      System.out.println(MenuConstants.ERROR_COMMAND);
      break;
    }
  }
  
  private Path getPathFromInput() {
    return getPathFromInput(true);
  }
  
  private Path getPathFromInput(boolean mustExist) {
    return getPathFromInput(mustExist, MenuConstants.INPUT_FILE);
  }
  
  private Path getPathFromInput(boolean mustExist, String formatString) {
    Path p = null;
        
    while (true){
      System.out.print(formatString);
      p = Paths.get(input.nextLine());

      if (! p.isAbsolute()) {
        p = Paths.get(cwd.toString(), p.toString());
      }
      
      if (mustExist && Files.isRegularFile(p)) {
        return p;
      } else if (! mustExist) {
        return p;
      }
      
      System.out.println(String.format(MenuConstants.ERROR_NOT_FOUND, p.toString()));
    }
  }
  
  private String getPasswordFromInput() {
    String in = null;
    while (true){
      System.out.print(MenuConstants.INPUT_PASSWORD);
      in = input.nextLine();
      
      if (in != null){
        return in;
      } else {
        System.out.println(MenuConstants.ERROR_EMPTY_PASS);
      }
    }
  }

  private void cryptFile() {
    Path fin = getPathFromInput();
    String password = getPasswordFromInput();
    Path fout = getPathFromInput(false, MenuConstants.INPUT_OUTPUT_FILE);

    byte[] finBytes = null;
    byte[] foutBytes = null;
    byte[] passBytes = password.getBytes();
    
    try {
      finBytes = Files.readAllBytes(fin);
    } catch (IOException e) {
      System.out.println(String.format(MenuConstants.ERROR_CANT_OPEN, fin.toString()));
    }
    
    foutBytes = new byte[finBytes.length];
    for (int i=0; i<finBytes.length; i++) {
      foutBytes[i] = (byte) (finBytes[i] ^ passBytes[i % passBytes.length]);
    }
    
    try {
      Files.write(fout, foutBytes);
    } catch (IOException e) {
      System.out.println(String.format(MenuConstants.ERROR_CANT_OPEN, fout.toString()));
    }
  }

  private void displayFile() {
    int offset = 0;
    StringBuilder sb = new StringBuilder();
    Path f = getPathFromInput();
    sb.append(String.format(MenuConstants.HEX_OFFSET, offset));
    
    try {
      byte[] bytes = Files.readAllBytes(f);
      for (byte b : bytes){
        sb.append(String.format(MenuConstants.HEX_BYTE, b));
        offset++;
        
        if (offset % MenuConstants.BYTE_COLS == 0) {
          sb.append(String.format(MenuConstants.HEX_OFFSET, offset));
        } else {
          sb.append(" ");
        }
      }
    } catch (IOException e) {
      System.out.println(String.format(MenuConstants.ERROR_CANT_OPEN, f.toString()));
    } finally {
      System.out.println(sb.toString());
    }
  }

  private void deleteFile() {
    Path f = getPathFromInput();
    
    try{
      Files.delete(f);
    } catch (IOException e) {
      System.out.println(String.format(MenuConstants.ERROR_INFO, e.toString()));
    }
  }

  private void listDirectory(Path path, boolean recurse) {
    Stream<Path> children = null;
    try {
      if (recurse) {
        children = Files.walk(path);
      } else {
        children = Files.walk(path, 1);
      }
    } catch (IOException e) {
      System.out.println(String.format(MenuConstants.ERROR_INFO, e.toString()));
    } finally {
      children.forEach(this::printFileInfo);
    }
  }
  
  private void printFileInfo(Path path) {
    String type = "?";
    String info = cwd.relativize(path).toString();
    
    if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
      type = "D";
    } else if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)){
      type = "F";
    } else if (Files.isSymbolicLink(path)) {
      type = "L";
      try {
        info = info + " -> " + Files.readSymbolicLink(path).toString();
      } catch (IOException e) {
        info = info + " -> Error reading link";
      }
    }
    
    if (info.length() > 0)  // omit "." (cwd)
      System.out.println(String.format(MenuConstants.FILE_INFO, type, info));
  }

  private void selectDirectory() {
    System.out.print(MenuConstants.INPUT_DIR);
    Path d = Paths.get(input.next());
    
    if (! d.isAbsolute() && cwd != null){
      d = Paths.get(cwd.toString(), d.toString());
    } 
    
    if (Files.isDirectory(d)) {
      this.cwd = d;
    } else {
      System.out.println(String.format(MenuConstants.ERROR_NOT_FOUND, d));
    }
  }
}
