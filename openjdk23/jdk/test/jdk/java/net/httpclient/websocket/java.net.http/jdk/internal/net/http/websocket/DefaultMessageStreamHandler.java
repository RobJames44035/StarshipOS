/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.internal.net.http.websocket;

import java.nio.ByteBuffer;

/**
 * No implementation provided for onInit() because that must always be
 * implemented by user
 */
abstract class DefaultMessageStreamHandler implements MessageStreamHandler {

    public void onText(CharSequence data, boolean last) {}

    public void onBinary(ByteBuffer data, boolean last) {}

    public void onPing(ByteBuffer data) {}

    public void onPong(ByteBuffer data) {}

    public void onClose(int statusCode, CharSequence reason) {}

    public void onComplete() {}

    public void onError(Throwable e) {}
}
