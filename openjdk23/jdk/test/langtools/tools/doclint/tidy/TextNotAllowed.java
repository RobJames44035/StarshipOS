/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

// tidy: Warning: plain text isn't allowed in <.*> elements

/**
 * <table> <caption> description </caption> abc </table>
 * <table> <caption> description </caption> <tbody> abc </tbody> </table>
 * <table> <caption> description </caption> <tr> abc </tr> </table>
 *
 * <dl> abc </dl>
 * <ol> abc </ol>
 * <ul> abc </ul>
 *
 * <ul>
 *     <li> item
 *     <li> item
 * </ul>
 */
public class TextNotAllowed { }
