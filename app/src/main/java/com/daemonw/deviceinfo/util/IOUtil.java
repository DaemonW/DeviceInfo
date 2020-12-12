package com.daemonw.deviceinfo.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by daemonw on 16-8-2.
 */

public class IOUtil {

    public static byte[] readAll(InputStream in) {
        byte[] data = null;
        byte[] buffer = new byte[512];
        int nRead = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while ((nRead = in.read(buffer)) >= 0) {
                bos.write(buffer, 0, nRead);
            }
            bos.flush();
            data = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            data = null;
        } finally {
            closeStream(bos);
            closeStream(in);
        }
        return data;
    }


    public static byte[] readN(InputStream in, int num) {
        byte[] data = null;
        int buff_size = 1024;
        byte[] buffer = new byte[buff_size];
        int nRead = 0;
        int leftSize = num;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while (leftSize >= buff_size && (nRead = in.read(buffer)) >= 0) {
                bos.write(buffer, 0, nRead);
                leftSize -= nRead;
            }

            while (leftSize > 0 && (nRead = in.read(buffer, 0, leftSize)) >= 0) {
                bos.write(buffer, 0, nRead);
                leftSize -= nRead;
            }
            bos.flush();
            data = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            data = null;
        } finally {
            closeStream(bos);
        }
        return data;
    }


    public static void save(byte[] data, OutputStream os) {
        try {
            os.write(data);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeStream(os);
        }
    }


    public static boolean copy(InputStream in, OutputStream out, OnProcessListener listener) {
        boolean success = false;
        try {
            byte[] buffer = new byte[4096];
            int nRead = 0;
            while ((nRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, nRead);
                if (listener != null) {
                    listener.onProcess(out, buffer, 0, nRead);
                }
            }
            out.flush();
            if (out instanceof FileOutputStream) {
                ((FileOutputStream) out).getFD().sync();
            }
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            IOUtil.closeStream(in);
            IOUtil.closeStream(out);
        }
        return success;
    }


    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static abstract class OnProcessListener {
        protected long size;
        private long processed;
        protected int progress;
        protected long triggered;
        protected final long interval = 100;

        public OnProcessListener() {
        }

        public void setSize(long size) {
            this.size = size;
        }

        public void setProcessed(long processed) {
            this.processed = processed;
        }

        void onProcess(OutputStream out, byte[] buff, int offset, int len) throws IOException {
            processed += len;
            progress = (int) (processed * 100 / size);
            long now = System.currentTimeMillis();
            if (now - triggered >= interval) {
                triggered = now;
                onUpdate(progress);
            } else {
                if (size - processed <= 8192) {
                    onUpdate(progress);
                }
            }
        }

        public abstract void onUpdate(int progress);

        protected void onFinish() {

        }

        protected void onFail(Exception e) {

        }
    }
}
