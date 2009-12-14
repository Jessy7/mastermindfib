package Conversors;

/**
 * This class is specialized in convert Array of Integers to String and reverse.
 * It only offers this two converters methods
 * This class is used to save and load games
 *
 * @author Oriol Bellet
 */
public class IntegerArrayString {

    /**
    * This method converts a String to Integer's array
    * @param input String to be converted
    * @return String converted to Integer's Array
    */
   public static Integer[] toIntegerArray (String input) {

       Integer[] output = new Integer[input.length()];

       for (int i = 0; i < input.length(); i++)
            output[i] = Integer.valueOf(input.substring(i,i+1));

       return output;

   }

    /**
    * This method converts an Integer's array to String
    * @param input Integer's array to be converted
    * @return Integer's array converted to String
    */
   public static String toString (Integer[] input) {

       String output = "";

       for (int i = 0; i < input.length; i++)
           output += input[i];

       return output;
   }
}
