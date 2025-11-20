
package com.smvitm.cnlab;
import java.util.Arrays;
public class SlidingWindowProtocol {
    private int windowsSize;
    private int[] frames;
    private boolean[] ack;
    public SlidingWindowProtocol(int windowSize,int frameCount)
    {
       this.windowsSize=windowSize;
       this.frames=new int[frameCount];
       this.ack=new boolean[frameCount];
       for(int i=0;i<frameCount;i++)
       {
           frames[i]=i;
           ack[i]=false;
       }
    }
    public void sendFrames()
    {
        int sendIndex=0;
        while(sendIndex<frames.length)
        {
            for(int i=0;i<windowsSize &&(sendIndex+i)<frames.length;i++)
            {
                System.out.println("Sending frame:"+frames[sendIndex+i]);
                
            }
            for(int i=0;i<windowsSize && (sendIndex+i)<frames.length;i++)
            {
                ack[sendIndex+i]=recieveAck(sendIndex+i);
            }
            while(sendIndex<frames.length && ack[sendIndex])
            {
                sendIndex++;
            }
       }
    }
    private boolean recieveAck(int frame)
    {
        System.out.println("Recieving ack for frame:"+frame);
        return true;
    }
    public static void main(String[] args)
    {
        int windowsSize=4;
        int frameCount=10;
        SlidingWindowProtocol swp=new SlidingWindowProtocol(windowsSize,frameCount);
        swp.sendFrames();
    }
}
   
