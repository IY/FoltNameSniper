

/*
 * Created with IntelliJ IDEA.
 * User: aaron
 * Project: RSNameFinder
 * Date: 3/16/14
 * Time: 12:12 AM
 */

/*
 * This program must be run in conjunction with the node.js based database provided in the archive as well as the
 * in-game script to listen for name change packets from the friend server. Instructions for running each of these are
 * included in their respective folders. If you need help setting up node on the droplets, getting the script running
 * in-game, additional information, etc., contact me on Skype: deejaypeaches
 */

package com.RSNameFinder;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

class SessionGenerator {

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36";

    public SessionGenerator() {
    }

    public String generateSessionId(String username, String password) {
        System.out.println("Generating login session ID...");
        try {
            username = URLEncoder.encode(username, "UTF-8");
            URL obj = new URL("https://secure.runescape.com/m=weblogin/login.ws");
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setConnectTimeout(30000);
            String urlParameters = "rem=on&username=" + username + "&password=" + password + "&submit=Log+In&mod=www&ssl=1&dest=account_settings.ws";
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            con.getResponseCode();
            String[] split = con.getURL().toString().split("/");
            for (String segment : split) {
                if (segment.contains("s=")) {
                    System.out.println("Login session ID generated.");
                    return segment;
                }
            }
            System.out.println("Login session ID not found in URL");
        } catch (NoRouteToHostException e) {
            System.out.println("NoRouteToHostException while generating login session ID");
        } catch (SocketTimeoutException e) {
            System.out.println("SocketTimeoutException while generating login session ID");
        } catch (SocketException e) {
            System.out.println("SocketException while generating login session ID");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException while generating login session ID");
        } catch (IOException e) {
            System.out.println("IOException while generating login session ID");
        }
        return null;
    }
}

