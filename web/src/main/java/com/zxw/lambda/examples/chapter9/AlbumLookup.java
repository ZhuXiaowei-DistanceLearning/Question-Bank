package com.zxw.lambda.examples.chapter9;

import com.zxw.lambda.examples.chapter1.Album;

public interface AlbumLookup {
    Album lookupByName(String albumName);
}
