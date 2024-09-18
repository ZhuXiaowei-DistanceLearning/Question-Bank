package com.zxw.utils;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class IDPhotoProcessor {

    static {
        // 加载 OpenCV 库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        String inputImagePath = "C:\\Users\\youyu\\Desktop\\20240918145058_2.jpg";  // 输入图片路径
        String outputImagePath = "C:\\Users\\youyu\\Desktop\\output.jpg"; // 输出图片路径
        Scalar backgroundColor = new Scalar(255, 255, 255);   // 白色背景
        Mat image = Imgcodecs.imread(inputImagePath);

        if (image.empty()) {
            System.out.println("Error: Cannot read the image. Please check the file path or file format.");
            return;
        }
        int width = 300;  // 自定义尺寸宽度
        int height = 400; // 自定义尺寸高度

        try {
            // 使用 grabCut 分割前景和背景，并替换背景颜色
            Mat result = replaceBackground(image, backgroundColor);

            // 调整输出图片尺寸
            Mat resized = new Mat();
            Imgproc.resize(result, resized, new Size(width, height));

            // 保存最终处理的图片
            Imgcodecs.imwrite(outputImagePath, resized);

            System.out.println("Image processing completed. Output saved to: " + outputImagePath);
        } catch (CvException e) {
            e.printStackTrace();
            System.out.println("Error during GrabCut processing.");
        }
    }

    /**
     * 使用 GrabCut 算法分割前景和背景，并替换背景颜色
     */
    private static Mat replaceBackground(Mat src, Scalar backgroundColor) throws CvException {
        // 创建掩码，大小与图像一致
        Mat mask = new Mat(src.size(), CvType.CV_8UC1, new Scalar(Imgproc.GC_BGD));
        Mat bgModel = new Mat();
        Mat fgModel = new Mat();

        // 设定初始的前景区域矩形，通常应包含人像主体
        Rect rect = new Rect(10, 10, src.cols() - 20, src.rows() - 20);

        // 执行 GrabCut 算法，初始矩形框选择，迭代 5 次
        Imgproc.grabCut(src, mask, rect, bgModel, fgModel, 5, Imgproc.GC_INIT_WITH_RECT);

        // 将掩码中的可能前景和确定前景部分标记为 1，其余部分标记为 0
        Core.compare(mask, new Scalar(Imgproc.GC_PR_FGD), mask, Core.CMP_EQ);

        // 创建白色背景图像
        Mat foreground = new Mat(src.size(), src.type(), backgroundColor);

        // 将前景部分复制到白色背景中
        src.copyTo(foreground, mask);

        return foreground;
    }
}