package com.thinglogix.cordova.plugins;

import android.telecom.Call;
import android.util.Log;

import com.flir.thermalsdk.ErrorCode;
import com.flir.thermalsdk.androidsdk.ThermalSdkAndroid;
import com.flir.thermalsdk.live.CommunicationInterface;
import com.flir.thermalsdk.live.Identity;
import com.flir.thermalsdk.live.discovery.DiscoveryEventListener;
import com.flir.thermalsdk.log.ThermalLog;
import com.thinglogix.thermal.CameraHandler;
import com.thinglogix.thermal.model.ThermalIdentity;

import org.apache.cordova.BuildConfig;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class FlirOneCamera extends CordovaPlugin {
    private static final String TAG = "FlirOneCamera";

    private static final String START_DISCOVERY = "startDiscovery";
    private static final String STOP_DISCOVERY = "stopDiscovery";

    private CameraHandler cameraHandler;
    private CallbackContext startDiscoveryCallback;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.d(TAG, "initializing plugin");

        ThermalLog.LogLevel enableLoggingInDebug = BuildConfig.DEBUG ? ThermalLog.LogLevel.DEBUG :
                ThermalLog.LogLevel.NONE;

        ThermalSdkAndroid.init(cordova.getActivity().getApplicationContext(), enableLoggingInDebug);
        cameraHandler = new CameraHandler();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (START_DISCOVERY.equals(action)) {
            Log.d(TAG, "Started discovery");
            startDiscovery(callbackContext);
            return true;
        }
        if (STOP_DISCOVERY.equals(action)) {
            stopDiscovery(callbackContext);
            return true;
        }
        return false;
    }

    private void startDiscovery(CallbackContext context) {
        PluginResult res = new PluginResult(PluginResult.Status.OK, "started discovery");
        res.setKeepCallback(true);
        startDiscoveryCallback = context;
        startDiscoveryCallback.sendPluginResult(res);

        cameraHandler.startDiscovery(cameraDiscoveryListener);
    }

    private void stopDiscovery(CallbackContext context) {
        Log.d(TAG, "STOPPING DISCOVERY");
        cameraHandler.stopDiscovery();
        context.success();
    }

    /**
     * Camera Discovery thermalImageStreamListener, is notified if a new camera was found during a active discovery phase
     * <p>
     * Note that callbacks are received on a non-ui thread so have to eg use {@link (Runnable)} to interact view UI components
     */
    private DiscoveryEventListener cameraDiscoveryListener = new DiscoveryEventListener() {
        @Override
        public void onCameraFound(Identity identity) {
            Log.d(TAG, "FOUND A CAMERA");
            cameraHandler.add(identity);

            ThermalIdentity ident = new ThermalIdentity(identity.communicationInterface, identity.cameraType,
                    identity.deviceId);

            PluginResult res = new PluginResult(PluginResult.Status.OK, ident.toJson());
            res.setKeepCallback(true);
            startDiscoveryCallback.sendPluginResult(res);
        }

        @Override
        public void onDiscoveryError(CommunicationInterface communicationInterface, ErrorCode errorCode) {
            PluginResult res = new PluginResult(PluginResult.Status.ERROR,
                    "onDiscoveryError communicationInterface:" + communicationInterface + " errorCode:" + errorCode);
            res.setKeepCallback(true);
            startDiscoveryCallback.sendPluginResult(res);
            Log.d(TAG, "There was an error");
        }
    };
}
