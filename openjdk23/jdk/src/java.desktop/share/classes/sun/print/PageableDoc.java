/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.print;

import java.awt.print.Pageable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;

public class PageableDoc implements Doc {

    private Pageable pageable;

    public PageableDoc(Pageable pageable) {
       this.pageable = pageable;
    }

   public DocFlavor getDocFlavor() {
       return DocFlavor.SERVICE_FORMATTED.PAGEABLE;
   }

   public DocAttributeSet getAttributes() {
       return new HashDocAttributeSet();
   }

   public Object getPrintData() throws IOException {
      return pageable;
   }

   public Reader getReaderForText() throws IOException {
      return null;
   }

   public InputStream getStreamForBytes() throws IOException {
      return null;
   }
}
