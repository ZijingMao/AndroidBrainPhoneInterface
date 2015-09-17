package com.company;

import edu.ucsd.sccn.LSL;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Main {

    private static Socket socket;

    private static final int SERVERPORT = 12345;
    private static final String SERVER_IP = "172.24.2.103";

    private static Thread th;


    public static void main(String[] args) throws IOException, InterruptedException  {

        System.out.println("Resolving an EEG stream...");


//        th = new Thread(){
//            @Override
//            public void run() {
//                try {
//                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
//
//                    socket = new Socket(serverAddr, SERVERPORT);
//
//                } catch (UnknownHostException e1) {
//                    e1.printStackTrace();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//
//            }
//        };
//        th.start();

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            socket = new Socket(serverAddr, SERVERPORT);

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            // uncomment these for recoding true data
            /*
            // open an inlet
            LSL.StreamInfo[] results = LSL.resolve_stream("type","EEG");
            LSL.StreamInlet inlet = new LSL.StreamInlet(results[0]);
            float[] sample = new float[inlet.info().channel_count()];
            while (true) {
                inlet.pull_sample(sample);
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(Arrays.toString(sample));
                for (int k=0;k<sample.length;k++)
                    System.out.print("\t" + Double.toString(sample[k]));
                System.out.println();
            }
            */

            // uncomment these for recoding simulated data
            Double[] sample = new Double[5];
            int time = 500; // how long you want to test on simulated data
            for (int t=0;t<time;t++) {
                for (int k = 0; k < 5; k++) {
                    sample[k] = Math.random();
                }
                Thread.sleep(100);
                for (int k=0;k<sample.length;k++)
                    System.out.print("\t" + Double.toString(sample[k]));
                System.out.println();
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(Arrays.toString(sample));
            }

            // out.println(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        System.out.println("Creating a new StreamInfo...");
//        LSL.StreamInfo info = new LSL.StreamInfo("BioSemi","EEG",8,100,LSL.ChannelFormat.Double32,"myuid324457");
//
//        System.out.println("Creating an outlet...");
//        LSL.StreamOutlet outlet = new LSL.StreamOutlet(info);
//
//        System.out.println("Sending data...");
//        Double[] sample = new Double[8];
//        for (int t=0;t<100000;t++) {
//            for (int k=0;k<8;k++)
//                sample[k] = (Double)Math.random()*50-25;
//            outlet.push_sample(sample);
//            Thread.sleep(10);
//        }
//
//        outlet.close();
//        info.destroy();
    }

}
