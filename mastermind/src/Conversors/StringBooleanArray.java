package Conversors;

/**
 * This class is specialized in convert Array of Booleans to String and reverse.
 * It only offers this two converters methods
 * This class is used to save and load games
 *
 * @author Oriol Bellet
 */
public class StringBooleanArray {

    /**
     * This method converts a String to Integer's array
     * @param input String to be converted
     * @return String converted to Boolean's Array
     */
    public static Boolean[] toBooleanArray(String input)
    {
        Boolean[] output = new Boolean[input.length()];

        for (int i = 0; i < input.length(); i++)
        {
            if (input.substring(i, i+1).compareTo("1") == 0)
                output[i] = Boolean.TRUE;
            else
                output[i] = Boolean.FALSE;
        }
        return output;
    }

    /**
     * This method converts an Booleans's array to String
     * @param input Booleans's array to be converted
     * @return Booleans's array converted to String
     */
    public static String toString(Boolean[] input)
    {
        String data = "";
        for (int i = 0; i < input.length; i++)
        {
            if (input[i] == Boolean.TRUE)
                data += "1";
            else
                data += "0";
        }
        return data;
    }


}
