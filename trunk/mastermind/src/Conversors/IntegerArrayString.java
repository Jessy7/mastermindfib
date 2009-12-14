package Conversors;

/**
 * This class is specialized in convert Array of Integers to String and reverse.
 * It only offers this two converters methods
 *
 * @author Oriol Bellet
 */
public class IntegerArrayString {

    /**
    * This method converts a String to Integer array
    * @param input String to be converted
    * @return String converted to Integer Array
    */
   public static Integer[] toIntegerArray (String input) {

       Integer[] output = new Integer[input.length()];

       for (int i = 0; i < input.length(); i++)
            output[i] = Integer.valueOf(input.substring(i,i+1));

       return output;

   }

    /**
    * This method converts an Integer array to String
    * @param input Integer array to be converted
    * @return Integer array converted to String
    */
   public static String toString (Integer[] input) {

       String output = "";

       for (int i = 0; i < input.length; i++)
           output += input[i];

       return output;
   }
}
