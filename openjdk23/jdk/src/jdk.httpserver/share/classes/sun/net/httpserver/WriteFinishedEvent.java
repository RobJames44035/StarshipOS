/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package sun.net.httpserver;

class WriteFinishedEvent extends Event {
    WriteFinishedEvent (ExchangeImpl t) {
        super (t);
        assert !t.writefinished;
        t.writefinished = true;
    }
}
