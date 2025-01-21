/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 *  The classfile for this class will be used to define a hidden interface.
 *  This class will fail to be created as a hidden class because hidden classes
 *  cannot user their name in field signatures.
 */
public interface HiddenInterface {
    default void test() {
        record R() {}
    }
}
