var exec = require('cordova/exec');

const MAIN_PROGRAN = 'FlirOneCamera';

const START_DISCOVERY = 'startDiscovery';
const STOP_DISCOVERY = 'stopDiscovery';
const CONNECT = 'connect';
const DISCONNECT = 'disconnect';
const START_STREAM = 'startStream';

exports.startDiscovery = function (success, error) {
    exec(success, error, MAIN_PROGRAN, START_DISCOVERY, []);
};

exports.stopDiscovery = function (success, error) {
    exec(success, error, MAIN_PROGRAN, STOP_DISCOVERY, []);
};

exports.connect = function (identity, success, error) {
    exec(success, error, MAIN_PROGRAN, CONNECT, [
        indentity.deviceId,
        identity.cameraType,
        identity.communicationInterface,
    ]);
};

exports.disconnect = function (success, error) {
    exec(success, error, MAIN_PROGRAN, DISCONNECT, []);
}

exports.startStream = function (success, error) {
    exec(success, error, MAIN_PROGRAN, START_STREAM, []);
}
