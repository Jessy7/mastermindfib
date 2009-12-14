package Conversors;

/**
 * This class is specialized in convert Matrix of Integers to String and reverse.
 * It only offers this two converters methods
 * This class is used to save and load games
 *
 * @author Oriol Bellet
 */
public class IntegerMatrixStringConverter {

    /**
    * This method converts a String to Integer's matrix
    * @param input String to be converted
    * @param rows Number of rows of the matrix created
    * @param columns Number of columns of the matrix created
    * @return String converted to Integer's matrix
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

    /**
    * This method converts an Integer's matrix to String
    * @param input Integer's matrix to be converted
    * @param rows Number of Rows of the input matrix
    * @param columns Number of columns of the input matrix
    * @return Integer's matrix converted to String
    */
    public static String toString(Integer[][] input, int rows, int columns) {

       String output = "";

       for (int i = 0; i < rows; i++)
           for (int j = 0; j < columns; j++)
                output += input[i][j];

       return output;
    }


}
