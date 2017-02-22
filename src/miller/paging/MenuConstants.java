package miller.paging;

public class MenuConstants {
  public static final String ERROR_COMMAND = "\nInvalid selection...";
  public static final String ERROR_EMPTY_INPUT = "\nInput can't be empty";
  public static final String ERROR_INFO = "\nError: %s";
  public static final String ERROR_NO_REF_STRING = "\nError: you must define a reference string before continuing";
    
  public static final String INPUT_REF_STRING = "Input reference string (space delimited): ";
  public static final String INPUT_REF_STRING_LENGTH = "Input reference string length: ";

  public static final String HEADER = "\n-- Paging Algorithm Menu --";
  public static final String MENU = "0 - Exit\n"
                                  + "1 - Read reference string\n"
                                  + "2 - Generate random reference string\n"
                                  + "3 - Show reference string\n"
                                  + "4 - Simulate FIFO\n"
                                  + "5 - Simulate OPT\n"
                                  + "6 - Simulate LRU\n"
                                  + "7 - Simulate LFU\n"
                                  + "Select action: ";
}
