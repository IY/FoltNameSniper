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

import java.io.*;
import java.util.ArrayList;

class Main {

    private static long start_time;
    private static int checks;
    private String[] names;
    private NameChecker nc;

    public static void main(String[] args) throws Exception {
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        Main main = new Main();
        while (main.nc == null || main.nc.getSession() == null) {
            main.nc = new NameChecker("gmail_address+" + ((int) (Math.random() * 40)) + "@gmail.com", session_id());
        }
        checks = 0;
        start_time = System.currentTimeMillis();
        if (args.length > 0) {
            System.out.println("Program ran with args, running file through check and register loop");
            main.names = main.loadFileFromPath(main.parseParams(args)[1]);
            System.out.println("Checking file...");
            int iterations = 0;
            while (main.checker(true)) {
                if (++iterations % 5 == 0) {
                    main.names = main.loadFileFromPath(main.parseParams(args)[1]);
                }
            }
        }
        boolean flag = true;
        while (flag) {
            String command;
            command = c.readLine("Command: ");
            if (command == null) {
                System.out.println("Something went wrong, stopping");
                return;
            }
            String[] str = main.parseParams(command.split(" "));
            switch (str[0]) {
                case "check":
                    checks = 0;
                    start_time = System.currentTimeMillis();
                    if (str.length > 1) {
                        if (str[1].contains("and register")) {
                            main.checker(true);
                            break;
                        }
                        main.nc.setName(str[1]);
                        switch (main.nc.check()) {
                            case 0:
                                System.out.println(main.nc.getName() + " is not available.");
                                break;
                            case 1:
                                System.out.println(main.nc.getName() + " is available.");
                                break;
                            case 2:
                                System.err.println(main.nc.getName() + " is invalid.");
                                break;
                            case 3:
                                System.out.println("Error checking " + main.nc.getName());
                                break;
                        }
                    } else {
                        main.checker(false);
                    }
                    break;
                case "snipe":
                    checks = 0;
                    start_time = System.currentTimeMillis();
                    if (str.length > 1) {
                        Registration reg;
                        if (c.readLine("Use existing account? (y/n): ").equalsIgnoreCase("y")) {
                            String session;
                            if (c.readLine("Use session ID? (y/n): ").equalsIgnoreCase("y")) {
                                reg = new Registration("", "", false);
                                reg.setEmail("");
                                session = c.readLine("Session: ");
                            } else {
                                String username = c.readLine("Username: ");
                                String password = c.readLine("Password: ");
                                if (password.equals("")) {
                                    password = session_id();
                                }
                                reg = new Registration(username, password, false);
                                reg.setEmail(username);
                                session = new SessionGenerator().generateSessionId(reg.getEmail(), reg.getPassword());
                            }
                            reg.setSession(session);
                            reg.registerName(str[1], true);
                            break;
                        } else {
                            reg = new Registration(getEmail(), session_id(), false);
                            while (reg.getEmail() == null) {
                                reg.registerEmail();
                            }
                            String session = new SessionGenerator().generateSessionId(reg.getEmail(), reg.getPassword());
                            reg.setSession(session);
                            reg.registerName(str[1], true);
                            break;
                        }
                    } else {
                        System.out.println("Enter a name to snipe.");
                    }
                    break;
                case "register":
                    if (str.length > 1) {
                        new Registration(getEmail(), session_id(), false).registerName(str[1], false);
                    } else {
                        System.out.println("Enter a name to register.");
                    }
                    break;
                case "monitor":
                    checks = 0;
                    start_time = System.currentTimeMillis();
                    Registration reg;
                    reg = new Registration(getEmail(), session_id(), true);
                    reg.getRecentNames();
                    while (true) {
                        reg.setEmail(null);
                        while (reg.getEmail() == null) {
                            reg.registerEmail();
                        }
                        reg.registerName(null, true);
                    }
                case "load":
                    if (str.length < 2) {
                        System.out.println("Include a path");
                        break;
                    }
                    main.names = main.loadFileFromPath(str[1]);
                    break;
                case "exit":
                    flag = false;
                    break;
                case "help":
                    System.out.println("Use \"check\" to check all loaded names");
                    System.out.println("Use \"check <name>\" to check a single name");
                    System.out.println("Use \"register <name>\" to register a name");
                    System.out.println("Use \"load <path>\" to load usernames from a list separated by new lines");
                    System.out.println("Use \"exit\" to exit");
                    break;
                default:
                    System.out.println("Unknown command. Type \"help\" to see commands");
            }
        }
        System.out.println("Goodbye");
    }

    static String getEmail() {
        // can get away with one, but multiple addresses are ideal. nothing special required.
        String[] emails = {
                "folt1@gmail.com",
                "folt2@gmail.com",
                "folt3@gmail.com",
                "folt4@gmail.com",
                "folt5@gmail.com",
                "folt6@gmail.com",
                "folt7@gmail.com",
                "folt8@gmail.com"
        };
        return emails[(int) (Math.random() * emails.length)];
    }

    static String session_id() {
        return "password";
    }

    String[] parseParams(String[] params) {
        String[] arrayOfStr = new String[(params.length > 1) ? 2 : 1];
        arrayOfStr[0] = params[0];
        if (arrayOfStr.length == 1) {
            return arrayOfStr;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < params.length; ++i) {
            sb.append(params[i]);
            if (i < params.length - 1) {
                sb.append(" ");
            }
        }
        arrayOfStr[1] = sb.toString();
        return arrayOfStr;
    }

    String[] loadFileFromPath(String path) {
        System.out.println("Loading in names from " + path + "...");
        BufferedReader br = null;
        ArrayList<String> names = new ArrayList<>();
        String currentline;
        int count = 0;
        try {
            br = new BufferedReader(new FileReader(path));
            while ((currentline = br.readLine()) != null) {
                names.add(currentline.trim());
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(path + " not found.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(count + " names loaded from " + path + ".");
        String[] nameArray;
        nameArray = names.toArray(new String[names.size()]);
        return nameArray;
    }

    boolean checker(boolean register) {
        int length = names.length;
        if (length == 0) {
            System.err.println("No names loaded.");
            return false;
        }
        ArrayList<String> available = new ArrayList<>();
        for (int i = 0; i < length; ++i) {
            nc.setName(names[i]);
            switch (nc.check()) {
                case 0:
                    ++checks;
                    break;
                case 1:
                    ++checks;
                    if (register) {
                        try {
                            new Registration(getEmail(), session_id(), false).registerName(nc.getName(), false);
                        } catch (NoClassDefFoundError e) {
                            System.out.println("Error loading Registration class");
                            --i;
                            break;
                        }
                    } else {
                        available.add(nc.getName());
                    }
                    //System.out.println(available.toString());
                    break;
                case 2:
                    System.err.println(nc.getName() + " is invalid.");
                    break;
                case 3:
                    --i;
                    break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int ms = perHour(checks);
            System.out.println("Checking... (" + (i + 1) + "/" + length + ") \"" + nc.getName() + "\" - average " + ms + "/h");
        }
        if (!register && available.size() > 0) {
            try {
                String filename = System.currentTimeMillis() + "_available.txt";
                save(filename, available);
                System.out.println("Wrote names to " + filename);
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }
            System.out.println(available.toString());
        }
        return true;
    }

    void save(String fileName, ArrayList<String> available) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("accounts" + File.separatorChar + fileName));
        for (String name : available)
            pw.println(name);
        pw.close();
    }

    int perHour(int total) {
        long time = ((System.currentTimeMillis() - start_time) / 1000L);
        if (time < 1L) {
            time = 1L;
        }
        return ((int) ((total * 60L * 60L) / time));
    }
}

