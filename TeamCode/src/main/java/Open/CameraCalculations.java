package Open;

import org.ejml.simple.SimpleMatrix;

public class CameraCalculations {
    double thetaXDeg, thetaYDeg;
    int imageWidth, imageHeight;
    double cameraTiltDeg;
    double cameraHeight;
    double thetaX, thetaY, cameraTilt;
    double fx, fy;
    SimpleMatrix K;

    SimpleMatrix sampleCoords;
    public static double worldPositionX, worldPositionY;

    public CameraCalculations() {
        thetaXDeg = 54.5;
        thetaYDeg = 42.0;
        imageWidth = 640;
        imageHeight = 480;
        cameraTiltDeg = 55;
        cameraHeight = 6.918;

        thetaX = Math.toRadians(thetaXDeg);
        thetaY = Math.toRadians(thetaYDeg);
        cameraTilt = Math.toRadians(cameraTiltDeg);

        fx = imageWidth / (2 * Math.tan(thetaX / 2));
        fy = imageHeight / (2 * Math.tan(thetaY / 2));

        K = new SimpleMatrix(3, 3);
        K.set(0, 0, fx);
        K.set(0, 2, (double) imageWidth / 2);
        K.set(1, 1, fy);
        K.set(1, 2, (double) imageHeight / 2);
        K.set(2, 2, 1);
    }
    public void SampleToRealWorld(double sampleX, double sampleY) {
        sampleCoords = new SimpleMatrix(3, 1);
        sampleCoords.set(0, 0, sampleX);
        sampleCoords.set(1, 0, sampleY);
        sampleCoords.set(2, 0, 1);

        SimpleMatrix normalizedCoords = K.invert().mult(sampleCoords);

        SimpleMatrix Rx = SimpleMatrix.identity(3);
        Rx.set(0, 0, 1);
        Rx.set(1, 1, Math.cos(cameraTilt));
        Rx.set(1, 2, -Math.sin(cameraTilt));
        Rx.set(2, 1, Math.sin(cameraTilt));
        Rx.set(2, 2, Math.cos(cameraTilt));

        SimpleMatrix rotatedCoords = Rx.mult(normalizedCoords);

        double lambda = -cameraHeight / rotatedCoords.get(2, 0);
        double worldX = lambda * rotatedCoords.get(0, 0);
        double worldY = lambda * rotatedCoords.get(1, 0);

        worldPositionX = worldX;
        worldPositionY = worldY;
    }
}
