package Enum;

/**
 * <p>
 *  This class works as a "define", to specify the different types of key peg.
 * </p>
 * <ul>
 *  <li>
 *   A red key peg indicates that a codepeg matches on both color and position.
 *  </li>
 *  <li>
 *   A white key peg indicates that a codepeg matches on color,
 *   but not on position.
 *  </li>
 * </ul>
 *
 * @author Samuel Gómez
 */
public enum KeyPeg {
    RED, WHITE, VOID;
}
