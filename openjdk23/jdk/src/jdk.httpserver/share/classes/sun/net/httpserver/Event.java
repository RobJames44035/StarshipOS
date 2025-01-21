/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package sun.net.httpserver;

import com.sun.net.httpserver.*;
import com.sun.net.httpserver.spi.*;

class Event {

    ExchangeImpl exchange;

    protected Event (ExchangeImpl t) {
        this.exchange = t;
    }
}
