package com.allever.allsample.imageloader;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by allever on 17-10-13.
 */

public class CloseUtil {
    public static void closeQuickly(Closeable closeable){
        if (closeable != null){
            try {
                closeable.close();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

    }
}
