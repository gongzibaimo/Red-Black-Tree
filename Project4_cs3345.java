package Project4_cs3345;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author mhuan
 */
public class Project4_cs3345 {
    private static final String mInsert = "Insert";
    private static final String mPrint = "PrintTree";
    private static final String mContain = "Contains";

    // convert boolean to camelcase
    static String camelCase(boolean b){
        String result = String.valueOf(b);
        return Character.toUpperCase(result.charAt(0)) + result.substring(1);
    }

    public static void main(String[] args) {
        // check if the argument is suitable
        if(args.length == 2){
            // read file
            Scanner fIn = null;
            // create a outputfile
            File fOut = new File(args[1]);
            // print stuff into the output file
            PrintWriter writer = null;
            String[] temp = null;
            String type = "";


            try{

                fIn = new Scanner(new File(args[0])); //you can put the input file and output file in the setting for args[0] and args[1]

                writer = new PrintWriter(fOut);
                // create an object
                RedBlackTree<String> mTree = new RedBlackTree<String>();

                BufferedReader test = null;
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(args[0])));
                type = reader.readLine();
                if((reader.getLineNumber() <=1) && type != null) {
                    if ((!type.equals("Integer")) && (!type.equals("String"))) {
                        writer.println("Only works for objects Integers and Strings");
                    } else {

                        // if there does not exist an output file, then create one
                        if (!fOut.exists()) {
                            fOut.createNewFile();
                        }
                        // read the input file line by line
                        while (fIn.hasNextLine()) {
                            String line = fIn.nextLine().trim();

                            if ((line.indexOf(mInsert) == 0)) {
                                try {
                                    temp = line.split(":", 2);
                                    String getKey = temp[1];
                                    //int key = Integer.parseInt(line.split(":", 2)[1]);
                                    // split the line to two part, divided from ":", e.g "insert:9", then devide to "insert" and "9"
                                    //int key = Integer.parseInt(line.split(":", 2)[1]);
                                    try {
                                        writer.println(camelCase(mTree.insert(getKey)));
                                        try {
                                            // throw exception if there exist a space
                                            if (line.contains(" ")) {
                                                throw new NumberFormatException();
                                            }
                                        } catch (NumberFormatException n) {
                                            writer.println("Error: space detected");
                                        }
                                    } catch (IllegalArgumentException e) {
                                        writer.println(e.getMessage());
                                    }
                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    writer.println("Error in Line: " + line);
                                }

                            }
                            else if ((line.indexOf(mPrint) == 0)) {
                                    writer.println(mTree.toString());
                            }
                            else if ((line.indexOf(mContain) == 0)) {
                                //int key = Integer.parseInt(line.substring(line.indexOf(mContain) + mContain.length()));
                                //int key = Integer.parseInt(line.substring(mContain.length()+1));
                                temp = line.split(":", 2);
                                String getKey = temp[1];
                                try {
                                    writer.println(camelCase(mTree.contains(getKey)));
                                } catch (IllegalArgumentException e) {
                                    writer.println(e.getMessage());
                                }
                            }
                            else if(((type.equals("String"))|| (type.equals("Integer"))) && (reader.getLineNumber() <=1) ){
                                continue;
                            }
                            else {
                                writer.println("Error in Line: " + line);
                            }
                        }
                        fIn.close();
                        ;
                        writer.close();
                        System.out.println("Output written to file: " + args[1]);
                    }
                }
            }
            catch(FileNotFoundException f){
                System.out.println(f.getMessage());
            }
            catch(IOException i){
                i.printStackTrace();
            }
        }
        else{
            System.out.println("Error: Cannot find the input file");
        }
    }
}
