/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Conversors;

/**
 *
 * @author Oriol Bellet
 */
public class IntegerMatrixStringConverter {

        /**
     * This method converts a String in an Integer matrix. The matrix
     * represents in each row the red and white keyPegs
     * @param input String to be converted in a Integer matrix
     * @return String converted in integer matrix
     */
    public static Integer[][] toIntegerMatrix(String input, int rows, int columns) {

       Integer[][] output = new Integer[rows][columns];

       int k = 0;
       for(int i = 0; i < rows; i++)
           for (int j = 0; j < columns; j++, k++)
           {
               output[i][j] = Integer.valueOf(input.substring(k, k+1));

           }
       return output;
    }


    public static String toString(Integer[][] input, int rows, int columns) {

       String output = "";

       for (int i = 0; i < rows; i++)
           for (int j = 0; j < columns; j++)
                output += input[i][j];

       return output;
    }


}
