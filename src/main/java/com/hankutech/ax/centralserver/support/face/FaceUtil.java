package com.hankutech.ax.centralserver.support.face;

import com.ar.face.faceenginesdk.enums.AR_COLOR_MODE;
import com.ar.face.faceenginesdk.enums.AliveDetectorMode;
import com.ar.face.faceenginesdk.enums.ExtractMode;
import com.ar.face.faceenginesdk.struct.model.FaceEnginePara;
import com.ar.face.faceenginesdk.struct.model.ImageInfo;
import com.ar.face.faceenginesdk.struct.model.register.FaceRegisterInParam;
import com.ar.face.faceenginesdk.struct.model.register.FaceRegisterOutParam;
import com.hankutech.ax.centralserver.support.BaseUtils;

import java.io.File;
import java.io.InvalidObjectException;

public class FaceUtil {

    static boolean initFaceEngine;

    public static String getFaceFtrArrayString(String imageBase64) throws InvalidObjectException {

        if (initFaceEngine == false) {

            FaceEngineConfigParam configParam = getFaceEngineConfigParam();
            FaceEngineWrapper.initFaceEngineWrapper(configParam);
            if (FaceEngineWrapper.getInstance().checkIfReady_Native() == false) {
                throw new InvalidObjectException("Native SDK初始化失败！");
            }
            initFaceEngine = true;

        }

        ImageInfo imgInfo = new ImageInfo();
        imgInfo.base64Data4RGB = imageBase64;

        FaceRegisterOutParam registerOutParam = FaceEngineWrapper.getInstance().getRegisterData_Native("", imgInfo);

        if (registerOutParam.ar_err_code == 0) {
            if (registerOutParam.faceRegInfoArray != null && registerOutParam.faceRegInfoArray.length > 0) {
                float[] faceFtrArray = registerOutParam.faceRegInfoArray[0].faceFtrArray;
                StringBuilder builder = new StringBuilder();
                for (float f :
                        faceFtrArray) {
                    if (builder.length() > 0) {
                        builder.append(",");
                    }
                    builder.append(f);
                }
                return builder.toString();

            }
        }

        return "";

    }

    public static FaceEngineConfigParam getFaceEngineConfigParam() {

        FaceEngineConfigParam configParam = new FaceEngineConfigParam();
        configParam.setConfigPath("");
        configParam.setSupportNativeFaceDetect(true);
        configParam.setSupportNativeFaceRecognize(true);

        String logFilePath = BaseUtils.getRootPath() + "/DebugLog";
        File f = new File(logFilePath);
        if (f.exists() == false) f.mkdirs();
        configParam.setLogFilePath(logFilePath);

        return configParam;
    }

    private static FaceRegisterInParam mockFaceRegisterInParam(ImageInfo imgInfo) {
        FaceRegisterInParam inParam = new FaceRegisterInParam();
        inParam.width = imgInfo.width4RGB;
        inParam.height = imgInfo.height4RGB;
        inParam.imgDataBuffer = imgInfo.base64Data4RGB;

        inParam.colorMode = AR_COLOR_MODE.AR_COLOR_BGR.getEnumValue();
        inParam.facePara = new FaceEnginePara();
        inParam.extractMode = ExtractMode.EXTRACT_LARGEST.getEnumValue();
        inParam.numFaces = 0;
        inParam.aliveDetectDataBuffer = null;
        inParam.aliveDetectWidth = 0;
        inParam.aliveDetectHeight = 0;
        inParam.aliveDetectColorMode = AR_COLOR_MODE.AR_COLOR_BGR.getEnumValue();
        if (imgInfo.base64Data4Depth == null || imgInfo.base64Data4Depth.length() == 0) {
            inParam.aliveDetectMode = AliveDetectorMode.ALIVE_DETECT_NULL.getEnumValue();
        } else {
            inParam.aliveDetectMode = AliveDetectorMode.ALIVE_DETECT_DEPTH.getEnumValue();
        }
        return inParam;
    }
}
