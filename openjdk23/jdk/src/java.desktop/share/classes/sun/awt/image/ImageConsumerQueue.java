/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package sun.awt.image;

import java.awt.image.ImageConsumer;

class ImageConsumerQueue {
    ImageConsumerQueue next;

    ImageConsumer consumer;
    boolean interested;


    static ImageConsumerQueue removeConsumer(ImageConsumerQueue cqbase,
                                             ImageConsumer ic,
                                             boolean stillinterested)
    {
        ImageConsumerQueue cqprev = null;
        for (ImageConsumerQueue cq = cqbase; cq != null; cq = cq.next) {
            if (cq.consumer == ic) {
                if (cqprev == null) {
                    cqbase = cq.next;
                } else {
                    cqprev.next = cq.next;
                }
                cq.interested = stillinterested;
                break;
            }
            cqprev = cq;
        }
        return cqbase;
    }

    static boolean isConsumer(ImageConsumerQueue cqbase, ImageConsumer ic) {
        for (ImageConsumerQueue cq = cqbase; cq != null; cq = cq.next) {
            if (cq.consumer == ic) {
                return true;
            }
        }
        return false;
    }

    ImageConsumerQueue(InputStreamImageSource src, ImageConsumer ic) {
        consumer = ic;
        interested = true;
        // Leaving this code throwing SecurityException for compatibility
        if (ic instanceof ImageRepresentation) {
            ImageRepresentation ir = (ImageRepresentation) ic;
            if (ir.image.source != src) {
                throw new SecurityException("ImageRep added to wrong image source");
            }
        }
    }

    public String toString() {
        return ("[" + consumer +
                ", " + (interested ? "" : "not ") + "interested" +
                "]");
    }
}
