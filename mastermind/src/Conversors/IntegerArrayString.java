/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Conversors;

/**
 *
 * @author Oriol Bellet
 */
public class IntegerArrayString {

      /**
    * This method transforms a String to an Integer array
    * @param input String to be converted
    * @return String converted in array
    */
   public static Integer[] toIntegerArray (String input) {

       Integer[] output = new Integer[input.length()];

       for (int i = 0; i < input.length(); i++)
            output[i] = Integer.valueOf(input.substring(i,i+1));

       return output;

   }

    /**
    * This method transforms an Integer array to String
    * @param input Integer array to convert
    * @return Integer array converted to String
    */
   public static String toString (Integer[] input) {

       String output = "";

       for (int i = 0; i < input.length; i++)
           output += input[i];

       return output;
   }



}
