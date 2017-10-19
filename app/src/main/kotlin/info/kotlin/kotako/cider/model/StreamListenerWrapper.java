package info.kotlin.kotako.cider.model;

import twitter4j.StatusListener;
import twitter4j.TwitterStream;

// twitter4j.StatusListenerの実装するとこが、kotlinの実装元のキャストによってしぬ

public class StreamListenerWrapper {
    static void addStatusListener(TwitterStream stream, StatusListener listener) {
        stream.addListener(listener);
    }
}
