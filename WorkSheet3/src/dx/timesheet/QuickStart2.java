/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxUser;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

    class Main1 {
    private static final String DEVELOPER_TOKEN = "weiv2e2RRH1cJecKT7esnu3ow2G0mugA";
    private static final int MAX_DEPTH = 1;
    private static final String CLIENT_ID = "aewhqy3tjz6oibnm9caa5xz1m56bmqif";
    private static final String CLIENT_SECRET = "XBdUGQMBULSv4tlSokWwwaAUWAMHzoFx";
    private static final String OAUTH2 ="https://naman.app.box.com";

    private Main1() { }

    public static void main(String[] args) {
        // Turn off logging to prevent polluting the output.
        //Logger.getLogger("com.box.sdk").setLevel(Level.OFF);

        //BoxAPIConnection api = new BoxAPIConnection(DEVELOPER_TOKEN);
        BoxAPIConnection api = new BoxAPIConnection(CLIENT_ID,CLIENT_SECRET);
       
        System.out.println("TOKENNNN  "+api.getRefreshToken());
             //BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();
//        System.out.format("Welcome, %s <%s>!\n\n", userInfo.getName(), userInfo.getLogin());

//        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
//        listFolder(rootFolder, 0);
    }

//    private static void listFolder(BoxFolder folder, int depth) {
//        for (BoxItem.Info itemInfo : folder) {
//            String indent = "";
//            for (int i = 0; i < depth; i++) {
//                indent += "    ";
//            }
//
//            System.out.println(indent + itemInfo.getName());
//            if (itemInfo instanceof BoxFolder.Info) {
//                BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
//                if (depth < MAX_DEPTH) {
//                    listFolder(childFolder, depth + 1);
//                }
//            }
//        }
//    }
}