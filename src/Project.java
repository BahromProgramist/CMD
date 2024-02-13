import java.io.*;
import java.util.Scanner;

public class Project {
    static StringBuilder currentPath = new StringBuilder("C:/");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome! ");
        String cmd = "";

        while (!cmd.equals("quit") && !cmd.equals("exit")) {
            System.out.print(currentPath.toString() + " : ");
            cmd = sc.nextLine();
            switch (cmd) {
                case "dir" -> {
                    dir();
                }
                default -> {
                    if (cmd.startsWith("cd")) {
                        String subFolder = cmd.split(" ")[1];

                        if (subFolder.equals("..")) {
                            int last = currentPath.lastIndexOf("/");
                            currentPath = new StringBuilder(currentPath.substring(0, last));
                        } else {
                            gotoFolder(subFolder);
                        }
                    } else if (cmd.startsWith("mkdir")) {
                        mkdir(cmd);
                    } else if (cmd.startsWith("rmdir")) {
                        rmdir(cmd);
                    } else if (cmd.startsWith("copy")) {
                        String[] file = cmd.split(" ");
                        String in = file[1];
                        String out = file[2];
                        copy(in, out);
                    } else if (cmd.startsWith("rename")) {
                        String[] file = cmd.split(" ");
                        String in = file[1];
                        String out = file[2];
                        rename(in, out);
                    } else if (cmd.startsWith("cat")) {
                        String filename = cmd.split(" ")[1];
                        cat(filename);
                    } else if (cmd.startsWith("rm")) {
                        String name = cmd.split(" ")[1];
                        rm(name);
                    } else if (cmd.startsWith("touch")) {
                        String name = cmd.split(" ")[1];
                        touch(name);
                    }
                }
            }
        }
        System.out.println("Good bye!");
    }

    private static void touch(String filename) {
        File file = new File(currentPath + "/" + filename);
        long cp = System.currentTimeMillis();
        if (file.exists()) {
            file.setLastModified(cp);
            System.out.println("File modified: " + file.lastModified());
        }else {
            System.out.println("File does not exist");
        }
    }

    private static void rm(String delete) {
        File file = new File(currentPath + "/" + delete);
        if (file.delete())
            System.out.println("Delete successfully");
        else
            System.out.println("Error");
    }

    private static void cat(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(currentPath + "/" + filename));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void rename(String oldName, String newName) {
        File oldFile = new File(currentPath + "/" + oldName);
        File newFile = new File(currentPath + "/" + newName);

        if (oldFile.renameTo(newFile))
            System.out.println("Renamed successfully");
        else
            System.out.println("Error");
    }


    private static void rmdir(String cmd) {
        String s = cmd.split(" ")[1];
        File file = new File(currentPath.toString());
        File[] f = file.listFiles();
        assert f != null;
        for (File f1 : f) {
            if (f1.getName().equals(s)) {
                f1.delete();
                System.out.println("o`chirildi");
            }
        }
    }

    private static void mkdir(String cmd) {
        String s = cmd.split(" ")[1];
        File file = new File(currentPath + "/" + s);
        if (s.endsWith("txt")) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            file.mkdir();
        }

    }

    private static void dir() {
        File file = new File(currentPath.toString());
        String[] files = file.list();

        assert files != null;
        for (String s : files) {
            System.out.println(s);
        }
    }

    private static void gotoFolder(String subFolder) {
        File file = new File(currentPath + "/" + subFolder);

        if (!file.isDirectory()) {
            System.out.println("Error");
        } else {
            currentPath.append("/").append(subFolder);
        }
    }

    public static void copy(String source, String destination) {
        InputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(currentPath.toString() + "/" + source);
            out = new FileOutputStream(currentPath.toString() + "/" + destination);
            out.write(in.readAllBytes());
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
