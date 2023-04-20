package de.idnow.sampleproject;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import de.idnow.sdk.CertificateProvider;

public class MyMtlsCertificateProvider extends CertificateProvider {
    private final Context context;

    public MyMtlsCertificateProvider(Context context) {
        this.context = context;
    }

    @Override
    public byte[] providePrivateKeyBytestream() {
        try(InputStream inputStream = context.getAssets().open("client_key.der")) {
            byte[] fileBytes=new byte[inputStream.available()];
            inputStream.read(fileBytes);
            return fileBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public byte[] provideCertificateBytestream() {
        try(InputStream inputStream = context.getAssets().open("client_cert.der")) {
            byte[] fileBytes=new byte[inputStream.available()];
            inputStream.read(fileBytes);
            return fileBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public byte[][] provideServerFingerPrintByteStreams() {
        return new byte[][] {
                str2bytes("90:3A:43:09:E3:34:6E:8F:EE:DA:03:11:5E:03:12:E0:3D:10:3D:19:67:40:70:A5:39:54:F5:75:CF:80:99:66")
        };
    }

    @Override
    public boolean verifyServerCertificate(byte[] serverCertificate) {
        try(InputStream inputStream = context.getAssets().open("server_cert.der")) {
            byte[] fileBytes=new byte[inputStream.available()];
            inputStream.read(fileBytes);
            return Arrays.equals(fileBytes, serverCertificate);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static byte[] str2bytes(String input) {
        String [] hex_list = input.split(":");
        byte[] output = new byte[hex_list.length];
        int i = 0;
        for (String hex: hex_list) {
            output[i] = (byte) Integer.parseInt(hex, 16);
            i ++;
        }
        return output;
    }
}
