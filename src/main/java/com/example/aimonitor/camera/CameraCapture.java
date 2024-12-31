package com.example.aimonitor.camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class CameraCapture {
    static {
        // 加载OpenCV库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * 拍摄一张照片并保存为 now.jpg，确保格式为 RGB
     * @throws Exception 如果拍摄失败或无法保存图片
     */
    public static void captureImage() throws Exception {
        VideoCapture camera = new VideoCapture(0); // 打开摄像头
        if (!camera.isOpened()) {
            throw new Exception("无法打开摄像头");
        }

        Mat frame = new Mat();
        if (!camera.read(frame)) { // 捕获一帧
            camera.release();
            throw new Exception("无法捕获画面");
        }

        // 转换为 RGB 格式
        Mat rgbMat = new Mat();
        Imgproc.cvtColor(frame, rgbMat, Imgproc.COLOR_BGR2RGB);

        // 保存为 now.jpg
        boolean result = Imgcodecs.imwrite("now.jpg", rgbMat);
        if (!result) {
            camera.release();
            throw new Exception("保存图片失败");
        }

        camera.release(); // 释放摄像头资源
    }

//    public static void main(String[] args) {
//        try {
//            captureImage();
//            System.out.println("图片已成功保存为 now.jpg，确保格式为 RGB");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
