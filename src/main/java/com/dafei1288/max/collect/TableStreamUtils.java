package com.dafei1288.max.collect;

public class TableStreamUtils {
    static Runnable closeAll(AutoCloseable... closeables) {
        return () -> {
            Throwable t = null;

            for (AutoCloseable closeable : closeables) {
                try {
                    closeable.close();
                }
                catch (Throwable t1) {

                    if (t == null)
                        t = t1;
                    else
                        t.addSuppressed(t1);
                }
            }


        };
    }
}
