package com.thinglogix.thermal.model;

import com.flir.thermalsdk.live.CameraType;
import com.flir.thermalsdk.live.CommunicationInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class ThermalIdentity {

    private CommunicationInterface communicationInterface;
    private CameraType cameraType;
    private String deviceId;

    public ThermalIdentity(CommunicationInterface communicationInterface, CameraType cameraType, String deviceId) {
        this.communicationInterface = communicationInterface;
        this.cameraType = cameraType;
        this.deviceId = deviceId;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("communicationInterface", communicationInterface);
            json.put("cameraType", cameraType);
            json.put("deviceId", deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
