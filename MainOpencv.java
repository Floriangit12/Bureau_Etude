import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.videoio.VideoCapture;

public class MainOpencv {
    private static int INPUT_WIDTH = 640;
    private static int INPUT_HEIGHT = 640;

    public static void main(String[] args) {
        System.loadLibrary("lib/opencv_java455");
        boolean cuda = false;
        Mat image = new Mat();
        String fileName = "yolov5s.onnx";
        VideoCapture video = new VideoCapture(0);

        if (!video.isOpened())
            System.out.println("Error video load");

        Net net = Dnn.readNet(fileName);
        /*
         * if(cuda == true){
         * net.setPreferableBackend(Dnn.DNN_BACKEND_CUDA);
         * net.setPreferableTarget(Dnn.DNN_TARGET_CUDA_FP16);
         * System.out.println("run on GPU");
         * }
         * else {
         * net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
         * net.setPreferableTarget(Dnn.DNN_TARGET_CPU);
         * System.out.println("run on CPU");
         * }
         */
        double x_factor = 0;
        double y_factor = 0;
        Mat blob = new Mat();
        while (true) {
            video.read(image);
            x_factor = image.width() / INPUT_WIDTH;
            y_factor = image.height() / INPUT_HEIGHT;
            Mat outputImage = detect(format(image), net);
            //wrap_detection(format(image), outputImage);

        }
    }

    public static Mat format(Mat image) {
        int rows = image.rows();
        int cols = image.cols();
        int max = rows;
        if (cols > rows)
            max = cols;
        Mat new_image=image.reshape(max, max);
        Mat output_image=Mat.zeros(new Size(max, max),CvType.CV_8U);
        Core.add(new_image, output_image, output_image);
        return output_image;
    }

    public static Mat detect(Mat image, Net net) {
        Mat blob = Dnn.blobFromImage(image, 1 / 255, new Size(640, 640), new Scalar(0.65), true, false);
        net.setInput(blob);
        return net.forward();
    }

    public static void wrap_detection(Mat input, Mat output){
        int rows = output.rows();
        int image_width = input.width();
        int image_height = input.height();
        int x_factor = output.width() / INPUT_WIDTH;
        int y_factor =  output.height()/ INPUT_HEIGHT;
        System.out.println("number of object : "+rows);
        for(int i=0;i<rows;i++){
            double confidance = output.get(i, 4)[0];
            //System.out.println("confidance : "+confidance);
        }
    }
    /*
     * public void process(){
     * Mat layer_outputs = net.forward("model");
     * int width = layer_outputs.width();
     * int height = layer_outputs.height();
     * //net.forward(outputs,net.getUnconnectedOutLayersNames());
     * 
     * int classes=5;
     * //net.forward(net.getUnconnectedOutLayersNames());
     * 
     * float x_factor = input_image.cols / INPUT_WIDTH;
     * float y_factor = input_image.rows / INPUT_HEIGHT;
     * int rows = height;
     * for(int i=0;i<rows;i++){
     * int x = layer_outputs.ele
     * }
     * }
     */
}