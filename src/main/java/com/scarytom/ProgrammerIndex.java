package com.scarytom;

import java.util.HashMap;

import com.google.common.collect.Maps;
import com.scarytom.pronet.Programmer;

public class ProgrammerIndex {

    private Network _network;

    public ProgrammerIndex withNetwork(final Network network) {
        _network = network;
        return this;
    }

    public HashMap<Programmer, Integer> build() {
        HashMap<Programmer, Integer> programmerIndex = Maps.newHashMap();
        int index = 0;
        for (Programmer programmer : _network.programmers()) {
            programmerIndex.put(programmer, index++);
        }
        return programmerIndex;
    }

}
