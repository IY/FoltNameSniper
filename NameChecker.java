
/*
 * Created with IntelliJ IDEA.
 * User: aaron
 * Project: RSNameFinder
 * Date: 3/7/14
 * Time: 11:37 AM
 */

/*
 * This program must be run in conjunction with the node.js based database provided in the archive as well as the
 * in-game script to listen for name change packets from the friend server. Instructions for running each of these are
 * included in their respective folders. If you need help setting up node on the droplets, getting the script running
 * in-game, additional information, etc., contact me on Skype: deejaypeaches
 */

package com.RSNameFinder;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

class NameChecker {

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36";

    private final String email;
    private final String password;
    private String session;
    private String name;
    private int errors;
    private int timeouts;

    public NameChecker(String email, String password) {
        this.email = email;
        this.password = password;
        this.session = new SessionGenerator().generateSessionId(email, password);
        this.errors = 0;
        this.timeouts = 0;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int check() {
        if (name.length() > 12) {
            return 2;
        }
        if (errors > 5 || timeouts > 20) {
            session = new SessionGenerator().generateSessionId(email, password);
            errors = 0;
            timeouts = 0;
        }
        String status = "";
        try {
//            URL obj = new URL("https://secure.runescape.com/m=account-creation/check_displayname.ajax");
//            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("User-Agent", USER_AGENT);
//            con.setRequestProperty("Host", "secure.runescape.com");
//            con.setRequestProperty("Referer", "https://secure.runescape.com/m=account-creation/create_account_funnel.ws");
//            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//            con.setDoOutput(true);
//            con.setReadTimeout(5000);
//            con.setConnectTimeout(5000);
//            String urlParameters = "displayname=" + name + "&noNameSuggestions=true";
//            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//            wr.writeBytes(urlParameters);
//            wr.flush();
//            wr.close();
//            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            status = br.readLine();
//            br.close();
//            String[] response;
//            String[][] keyValuePairs = null;
//            if (status.contains("displayNameIsValid")) {
//                status = status.replace("{", "").replace("}", "").replace("\"", "");
//                response = status.split(",");
//                keyValuePairs = new String[response.length][2];
//                for (int i = 0; i < keyValuePairs.length; i++) {
//                    keyValuePairs[i] = response[i].split(":");
//                }
//            }
//            if (keyValuePairs == null) {
//                return 3;
//            }
//            for (String[] keyValuePair : keyValuePairs) {
//                if (keyValuePair[0].equals("displayNameIsValid")) {
//                    if (keyValuePair[1].equals("true")) {
//                        return 1;
//                    } else {
//                        return 0;
//                    }
//                }
//            }
//            return 3;


            String urlString = "https://secure.runescape.com/m=displaynames/" + session + "/check_name.ws?displayname=" + name;
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.addRequestProperty("Host", "secure.runescape.com");
            conn.addRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.addRequestProperty("Referer", "https://secure.runescape.com/m=displaynames/c=0000000000/name.ws");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            status = br.readLine();
            br.close();
        } catch (NoRouteToHostException e) {
            System.out.println("NoRouteToHostException while checking availability");
        } catch (SocketTimeoutException e) {
            System.out.println("SocketTimeoutException while checking availability");
            timeouts++;
            errors = 0;
            return 3; // Timeouts are not expected but not fatal
        } catch (SocketException e) {
            System.out.println("SocketException while checking availability");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException while checking availability");
        } catch (IOException e) {
            System.out.println("IOException while checking availability");
        }
//        try {
//            Thread.sleep(100L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if (status == null) {
            return 3;
        }
        switch (status) { // Reset errors when we receive expected responses
            case "NOK":
                errors = 0;
                timeouts = 0;
                return 0;
            case "OK":
                errors = 0;
                timeouts = 0;
                return 1;
            case "NONAME":
                errors = 0;
                timeouts = 0;
                return 2;
            default:
                errors++;
                return 3;
        }
    }
}

