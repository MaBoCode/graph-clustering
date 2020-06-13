package utils;

public class Logs {

    public static void debug(String msg) {
        Thread currentThread = Thread.currentThread();

        if (currentThread.isAlive()) {
            int lastStackTraceId = Thread.currentThread().getStackTrace().length - 1;
            String className = Thread.currentThread().getStackTrace()[lastStackTraceId].getClassName();
            String methodName = Thread.currentThread().getStackTrace()[lastStackTraceId].getMethodName();

            String message = String.format("[DEBUG]\\ %s.%s(): %s", className, methodName, msg);

            System.out.println(message);
        }
    }
}
