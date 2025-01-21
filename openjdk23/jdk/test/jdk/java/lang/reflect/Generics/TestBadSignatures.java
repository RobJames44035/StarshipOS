/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6832374 7052898
 * @summary Test bad signatures get a GenericSignatureFormatError thrown.
 * @author Joseph D. Darcy
 * @modules java.base/sun.reflect.generics.parser
 */

import java.lang.reflect.*;
import sun.reflect.generics.parser.SignatureParser;

public class TestBadSignatures {
    public static void main(String[] args) {
        String[] badSignatures = {
            // Missing ":" after first type bound
            "<T:Lfoo/tools/nsc/symtab/Names;Lfoo/tools/nsc/symtab/Symbols;",

            // Arrays improperly indicated for exception information
            "<E:Ljava/lang/Exception;>(TE;[Ljava/lang/RuntimeException;)V^[TE;",
        };

        for(String badSig : badSignatures) {
            try {
                SignatureParser.make().parseMethodSig(badSig);
                throw new RuntimeException("Expected GenericSignatureFormatError for " +
                                           badSig);
            } catch(GenericSignatureFormatError gsfe) {
                System.out.println(gsfe.toString()); // Expected
            }
        }
    }
}
