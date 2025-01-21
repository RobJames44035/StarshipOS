/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary The characters FFFE and FFFF should not be in a UnicodeBlock. They
 *          should be in a null block.
 * @bug 4404588
 * @author John O'Conner
 */

 public class Bug4404588 {

    public Bug4404588() {
        // do nothing
    }

    public static void main(String[] args) {
        Bug4404588 test = new Bug4404588();
        test.run();
    }

    /**
     * Test the correct data against what Character reports.
     */
    void run() {
        Character ch;
        Character.UnicodeBlock block;

        for(int x=0; x < charData.length; x++) {
            ch = (Character)charData[x][0];
            block = (Character.UnicodeBlock)charData[x][1];

            if (Character.UnicodeBlock.of(ch.charValue()) != block) {
                System.err.println("Error: block = " + block);
                System.err.println("Character.UnicodeBlock.of(" +
                    Integer.toHexString(ch.charValue()) +") = " +
                    Character.UnicodeBlock.of(ch.charValue()));
                throw new RuntimeException("Blocks aren't equal.");
            }
        }
        System.out.println("Passed.");
    }

    /**
     * Contains the character data to test. The first object is the character.
     * The next object is the UnicodeBlock to which it should belong.
     */
    Object[][] charData = {
        { new Character('\uFFFE'), Character.UnicodeBlock.SPECIALS },
        { new Character('\uFFFF'), Character.UnicodeBlock.SPECIALS },
    };
 }
