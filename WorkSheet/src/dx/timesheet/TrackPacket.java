/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

/**
 *
 * @author Me
 */
public class TrackPacket {
    static boolean stop_track=false;
    ReadXml rd=new ReadXml();
    String dstIp,srcIp,dstMac,srcMac,host,url,referer,connection,requestVersion,user_agent,content_type,authorization,proxy_connection;
     Runnable trackNet = new Runnable() {
        @Override
        public void run() {
            if(!Thread.currentThread().isInterrupted()){
                listenPacket();
            }
            
        }
    };
    public void filterPacket(Pcap pcap){
        PcapBpfProgram program = new PcapBpfProgram();   
        String expression = "192.168.1.1";  
        int optimize = 0;         // 0 = false  
        int netmask = 0xFFFFFF00; // 255.255.255.0 
        if (pcap.compile(program, expression, optimize, netmask) != Pcap.OK) {  
            System.err.println(pcap.getErr());  
            return;  
            }  
          
            if (pcap.setFilter(program) != Pcap.OK) {  
             System.err.println(pcap.getErr());  
             return;         
}  
     }
    public void listenPacket(){
        try {
            // Will be filled with NICs
            List<PcapIf> alldevs = new ArrayList<PcapIf>();
            // For any error msgs
            StringBuilder errbuf = new StringBuilder();

            //Getting a list of devices
            int r = Pcap.findAllDevs(alldevs, errbuf);
            System.out.println(r);
            if (r != Pcap.OK) {
                System.err.printf("Can't read list of devices, error is %s", errbuf
                        .toString());
                return;
            }

            System.out.println("Network devices found:");
            int i = 0;
            int count=0;
            for (PcapIf device : alldevs) {
                String description =
                        (device.getDescription() != null) ? device.getDescription()
                        : "No description available";
                System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);
                
      //          if(i>0){
      //             if (openDeviceForCapture(alldevs,i-1,errbuf)) {
      //          return;
      //      } 
      //          }
                
                
                count++;
            }
            System.out.println("Device count is:"+count);
            System.out.println("choose the one device from above list of devices");
        //    int ch = new Scanner(System.in).nextInt();
       //     PcapIf device = null;
       //     if(count>0){
       //      for(int a=0;a<count;a++){
       //        device = alldevs.get(a);  
      //      }   
      //      }
            
            PcapIf device = alldevs.get(0);

            int snaplen = 64 * 1024;           // Capture all packets, no trucation
            int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
            int timeout = 20 * 1000;           // 10 seconds in millis

            //Open the selected device to capture packets
            final Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

            if (pcap == null) {
                System.err.printf("Error while opening device for capture: "
                        + errbuf.toString());
                return;
            }
            
            
            
            System.out.println("device opened");

            //Create packet handler which will receive packets
            PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
                Arp arp = new Arp();
                Http http = new Http();
                Ip4 ip = new Ip4();
                Tcp tcp = new Tcp();
                Payload payload = new Payload();
                Ethernet eth = new Ethernet();
                Udp udp = new Udp();

                @Override
                public void nextPacket(PcapPacket packet, String user) {
                    if(stop_track){
                       pcap.close(); 
                       return;
                    }
                    //Here i am capturing the ARP packets only,you can capture any packet that you want by just changing the below if condition
                    if (packet.hasHeader(tcp) && packet.hasHeader(http)) {
                        packet.getHeader(eth);
                        packet.getHeader(tcp);
                        packet.getHeader(ip);
                        if (tcp.destination() == 80 ||  tcp.destination() == 443) {
                            if (http.hasField(Http.Request.Accept) && http.fieldValue(Http.Request.Accept).contains("text/html")) {

                     //            dstIp = FormatUtils.ip(ip.destination());
                                   srcIp = FormatUtils.ip(ip.source());
                      //           dstMac = FormatUtils.mac(eth.destination());
                     //            srcMac = FormatUtils.mac(eth.source());
                                 host = http.fieldValue(Http.Request.Host);
                                 url = host + http.fieldValue(Http.Request.RequestUrl);
                                 referer = http.fieldValue(Http.Request.Referer);
                         //        connection = http.fieldValue(Http.Request.Connection);
                           //      requestVersion = http.fieldValue(Http.Request.RequestVersion);
                                 user_agent = http.fieldValue(Http.Request.User_Agent);
                                 content_type=http.fieldValue(Http.Request.Content_Type);
                           //      authorization=http.fieldValue(Http.Request.Authorization);
                          //       proxy_connection= http.fieldValue(Http.Request.Proxy_Connection);
                          
                                System.out.println("Host: " +host);
                         //       System.out.println("Source mac: " +srcMac);
                         //       System.out.println("Destination mac: " +dstMac);
                         //       System.out.println("Destination ip: " + dstIp);
                                System.out.println("Request: " + srcIp + " - " + url);
                                System.out.println("User_Agent: " +user_agent);
                         //       System.out.println("RequestVersion: " +requestVersion);
                         //       System.out.println("Proxy_Connection: " +proxy_connection);
                         //       System.out.println("Connection: " +connection);
                         //       System.out.println("Content_Type: " +content_type);
                         //       System.out.println("Authorization: " +authorization);
                                System.out.println("Referer: " + referer);
                                try {
                                    rd.sendTrackNetInfo(content_type, host, url, referer,user_agent);
                                } catch (IOException ex) {
                                    Logger.getLogger(TrackPacket.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                //      RecorderService.recordHttpRequest(srcMac, srcIp, dstIp, host, url, referer);
                      //          System.out.println("Date: " + http.fieldValue(Http.Request.Date));
                      //          System.out.println(packet.getHeader(http).fieldValue(Http.Request.Date));
                                //superFlowMap.nextPacket(packet, superFlowMap);
System.out.println("******************************************************************************************************************");
System.out.println("******************************************************************************************************************");
                            }
                        }
                        //         System.out.println("Hardware type" + arp.hardwareType());
                        //         System.out.println("Protocol type" + arp.protocolType());
                        //         System.out.println("Packet:" + arp.getPacket());
                        //     System.out.println("Name:" + arp.getName());
                        //    System.out.println("Description:" + arp.getDescription());
                        //   System.out.println("Description:" + arp.);
                        //      System.out.println("fieldValue:" + http.fieldValue(Http.Request.Host));
                        //      System.out.println("Description:" + http.getDescription());
                        //      System.out.println("Header:" + http.header());
                        //      System.out.println("Packet:" + http.getPacket());
                        //         System.out.println("Packet:" + http.);


                        //      final String content_length =     http.fieldValue(Response.Content_Length);  
                        //     final String response_code = http.fieldValue(Response.ResponseCode);  
                        //Find if the given packet is a Request/Response Pkt : First get the TCP header   
                        //      packet.getHeader(tcp);  
                        //     Integer int_tcp_source = new Integer(tcp.source());  
                        //     Integer int_tcp_destination = new Integer(tcp.destination());
                   //     System.out.println();
                    }
                }
            };
            //we enter the loop and capture the 10 packets here.You can  capture any number of packets just by changing the first argument to pcap.loop() function below
            pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, "jnetpcap rocks!");
            //Close the pcap
            pcap.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
   
    
}
