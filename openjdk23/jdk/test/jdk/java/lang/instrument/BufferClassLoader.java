/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import  java.security.*;

public class BufferClassLoader extends SecureClassLoader
{
    private final NamedBuffer[] fBuffers;

    public
    BufferClassLoader(  ClassLoader     parent,
                        NamedBuffer[]   buffers)
        {
        super(parent);
        fBuffers = buffers;     // maybe should copy
        }


    protected Class
    findClass(String name)
        throws ClassNotFoundException
        {
        for ( int x = 0; x < fBuffers.length; x++ )
            {
            if ( fBuffers[x].getName().equals(name) )
                {
                byte[] buffer = fBuffers[x].getBuffer();
                return defineClass( name,
                                    buffer,
                                    0,
                                    buffer.length,
                                    (CodeSource) null);
                }
            }

        throw new ClassNotFoundException(name);
        }


}
