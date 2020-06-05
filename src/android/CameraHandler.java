package com.thinglogix.thermal;

import com.flir.thermalsdk.live.CommunicationInterface;
import com.flir.thermalsdk.live.Identity;
import com.flir.thermalsdk.live.discovery.DiscoveryEventListener;
import com.flir.thermalsdk.live.discovery.DiscoveryFactory;

import java.util.LinkedList;

public class CameraHandler {
    LinkedList<Identity> foundCameraIdentities = new LinkedList<>();

    public CameraHandler() {
    }

    public void add(Identity identity) {
        foundCameraIdentities.add(identity);
    }

    /**
     * Start discovery of USB
     */
    public void startDiscovery(DiscoveryEventListener discoveryEventListener) {
        DiscoveryFactory.getInstance().scan(discoveryEventListener, CommunicationInterface.USB);
    }

    /**
     * Stop discovery of USB and Emulators
     */
    public void stopDiscovery() {
        DiscoveryFactory.getInstance().stop(CommunicationInterface.USB);
    }

}