/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Conversors;

/**
 *
 * @author Ori
 */
public class StringBooleanArray {

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

    public static String toString(Boolean[] patternVisibility)
    {
        String data = "";
        for (int i = 0; i < patternVisibility.length; i++)
        {
            if (patternVisibility[i] == Boolean.TRUE)
                data += "1";
            else
                data += "0";
        }
        return data;
    }


}
