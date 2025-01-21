/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/**
 * @test
 * @bug 5106550
 * @summary Merge a comment using the standard metdata format
 *          and only a minimal set of attributes
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

public class MergeStdCommentTest {

    public static void main(String[] args) throws Exception {
        String format = "javax_imageio_1.0";
        BufferedImage img =
            new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        ImageWriter iw = ImageIO.getImageWritersByMIMEType("image/png").next();
        IIOMetadata meta =
            iw.getDefaultImageMetadata(new ImageTypeSpecifier(img), null);
        DOMImplementationRegistry registry;
        registry = DOMImplementationRegistry.newInstance();
        DOMImplementation impl = registry.getDOMImplementation("XML 3.0");
        Document doc = impl.createDocument(null, format, null);
        Element root, text, entry;
        root = doc.getDocumentElement();
        root.appendChild(text = doc.createElement("Text"));
        text.appendChild(entry = doc.createElement("TextEntry"));
        // keyword isn't #REQUIRED by the standard metadata format.
        // However, it is required by the PNG format, so we include it here.
        entry.setAttribute("keyword", "Comment");
        entry.setAttribute("value", "Some demo comment");
        meta.mergeTree(format, root);
    }
}
