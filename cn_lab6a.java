package com.smvitm.cnlab;
import java.net.*;
import java.io.*;
public class SocketServer {
        public static void main(String[] args) throws Exception
        {
            ServerSocket servSocket=new ServerSocket(4000);
            System.out.println("***Server side***");
            System.out.println("Server ready for connection");
            Socket connSock=servSocket.accept();
            InputStream istream=connSock.getInputStream();
            BufferedReader fileRead=new BufferedReader(new InputStreamReader(istream));
            String fname=fileRead.readLine();
            File fileName=new File(fname);
            OutputStream ostream=connSock.getOutputStream();
            PrintWriter pwrite=new PrintWriter(ostream,true);
            if(fileName.exists())
            {
                BufferedReader contentRead=new BufferedReader(new FileReader(fname));
                
         System.out.println("writing file contents to the socket");
         String str;
         while((str=contentRead.readLine())!=null){
             pwrite.println(str);
            }
            contentRead.close();
        }
        else
        {
             System.out.println("request file does not exist");
            String msg="request file does not exist at server side";
            pwrite.println(msg);
        }
connSock.close();
servSocket.close();
pwrite.close();
fileRead.close();
pwrite.close();
}
}

            
