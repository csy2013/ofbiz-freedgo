/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.base.util;

import javolution.util.FastList;
import javolution.util.FastSet;
import org.apache.commons.io.FileUtils;
import org.ofbiz.base.location.ComponentLocationResolver;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * File Utilities
 *
 */
public class FileUtil {

    public static final String module = FileUtil.class.getName();

    public static File getFile(String path) {
        return getFile(null, path);
    }

    public static File getFile(File root, String path) {
        if (path.startsWith("component://")) {
            try {
                path = ComponentLocationResolver.getBaseLocation(path).toString();
            } catch (MalformedURLException e) {
                Debug.logError(e, module);
                return null;
            }
        }
        return new File(root, localizePath(path));
    }

    /**
     * Converts a file path to one that is compatible with the host operating system.
     *
     * @param path The file path to convert.
     * @return The converted file path.
     */
    public static String localizePath(String path) {
        String fileNameSeparator = ("\\".equals(File.separator) ? "\\" + File.separator : File.separator);
        return path.replaceAll("/+|\\\\+", fileNameSeparator);
    }

    public static void writeString(String fileName, String s) throws IOException {
        writeString(null, fileName, s);
    }

    public static void writeString(String path, String name, String s) throws IOException {
        Writer out = getBufferedWriter(path, name);

        try {
            out.write(s + System.getProperty("line.separator"));
        } catch (IOException e) {
            Debug.logError(e, module);
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Debug.logError(e, module);
                }
            }
        }
    }

    /**
     * Writes a file from a string with a specified encoding.
     *
     * @param path
     * @param name
     * @param encoding
     * @param s
     * @throws IOException
     */
    public static void writeString(String path, String name, String encoding, String s) throws IOException {
        String fileName = getPatchedFileName(path, name);
        if (UtilValidate.isEmpty(fileName)) {
            throw new IOException("Cannot obtain buffered writer for an empty filename!");
        }

        try {
            FileUtils.writeStringToFile(new File(fileName), s, encoding);
        } catch (IOException e) {
            Debug.logError(e, module);
            throw e;
        }
    }

    public static void writeString(String encoding, String s, File outFile) throws IOException {
        try {
            FileUtils.writeStringToFile(outFile, s, encoding);
        } catch (IOException e) {
            Debug.logError(e, module);
            throw e;
        }
    }

    public static Writer getBufferedWriter(String path, String name) throws IOException {
        String fileName = getPatchedFileName(path, name);
        if (UtilValidate.isEmpty(fileName)) {
            throw new IOException("Cannot obtain buffered writer for an empty filename!");
        }

        return new BufferedWriter(new FileWriter(fileName));
    }

    public static OutputStream getBufferedOutputStream(String path, String name) throws IOException {
        String fileName = getPatchedFileName(path, name);
        if (UtilValidate.isEmpty(fileName)) {
            throw new IOException("Cannot obtain buffered writer for an empty filename!");
        }

        return new BufferedOutputStream(new FileOutputStream(fileName));
    }

    public static String getPatchedFileName(String path, String fileName) throws IOException {
        // make sure the export directory exists
        if (UtilValidate.isNotEmpty(path)) {
            path = path.replaceAll("\\\\", "/");
            File parentDir = new File(path);
            if (!parentDir.exists()) {
                if (!parentDir.mkdir()) {
                    throw new IOException("Cannot create directory for path: " + path);
                }
            }

            // build the filename with the path
            if (!path.endsWith("/")) {
                path = path + "/";
            }
            if (fileName.startsWith("/")) {
                fileName = fileName.substring(1);
            }
            fileName = path + fileName;
        }

        return fileName;
    }

    public static StringBuffer readTextFile(File file, boolean newline) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        StringBuffer buf = new StringBuffer();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));

            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
                if (newline) {
                    buf.append(System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            Debug.logError(e, module);
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Debug.logError(e, module);
                }
            }
        }

        return buf;
    }
    public static StringBuffer readTextFile(String fileName, boolean newline) throws IOException {
        File file = new File(fileName);
        return readTextFile(file, newline);
    }

    public static String readString(String encoding, File inFile) throws IOException {
        String readString = "";
        try {
            readString = FileUtils.readFileToString(inFile, encoding);
        } catch (IOException e) {
            Debug.logError(e, module);
            throw e;
        }
        return readString;
    }

    public static void searchFiles(List<File> fileList, File path, FilenameFilter filter, boolean includeSubfolders) throws IOException {
        // Get filtered files in the current path
        File[] files = path.listFiles(filter);
        if (files == null) {
            return;
        }

        // Process each filtered entry
        for (int i = 0; i < files.length; i++) {
            // recurse if the entry is a directory
            if (files[i].isDirectory() && includeSubfolders && !files[i].getName().startsWith(".")) {
                searchFiles(fileList, files[i], filter, true);
            } else {
                // add the filtered file to the list
                fileList.add(files[i]);
            }
        }
    }

    public static List<File> findFiles(String fileExt, String basePath, String partialPath, String stringToFind) throws IOException {
        if (basePath == null) {
            basePath = System.getProperty("ofbiz.home");
        }

        Set<String> stringsToFindInPath = FastSet.newInstance();
        Set<String> stringsToFindInFile = FastSet.newInstance();

        if (partialPath != null) {
           stringsToFindInPath.add(partialPath);
        }
        if (stringToFind != null) {
           stringsToFindInFile.add(stringToFind);
        }

        List<File> fileList = FastList.newInstance();
        FileUtil.searchFiles(fileList, new File(basePath), new SearchTextFilesFilter(fileExt, stringsToFindInPath, stringsToFindInFile), true);

        return fileList;
    }

    public static List<File> findXmlFiles(String basePath, String partialPath, String rootElementName, String xsdOrDtdName) throws IOException {
        if (basePath == null) {
            basePath = System.getProperty("ofbiz.home");
        }

        Set<String> stringsToFindInPath = FastSet.newInstance();
        Set<String> stringsToFindInFile = FastSet.newInstance();

        if (partialPath != null) stringsToFindInPath.add(partialPath);
        if (rootElementName != null) stringsToFindInFile.add("<" + rootElementName + " ");
        if (xsdOrDtdName != null) stringsToFindInFile.add(xsdOrDtdName);

        List<File> fileList = FastList.newInstance();
        FileUtil.searchFiles(fileList, new File(basePath), new SearchTextFilesFilter("xml", stringsToFindInPath, stringsToFindInFile), true);
        return fileList;
    }

    public static class SearchTextFilesFilter implements FilenameFilter {
        String fileExtension;
        Set<String> stringsToFindInFile = FastSet.newInstance();
        Set<String> stringsToFindInPath = FastSet.newInstance();

        public SearchTextFilesFilter(String fileExtension, Set<String> stringsToFindInPath, Set<String> stringsToFindInFile) {
            this.fileExtension = fileExtension;
            if (stringsToFindInPath != null) {
                this.stringsToFindInPath.addAll(stringsToFindInPath);
            }
            if (stringsToFindInFile != null) {
                this.stringsToFindInFile.addAll(stringsToFindInFile);
            }
        }

        public boolean accept(File dir, String name) {
            File file = new File(dir, name);
            if (file.getName().startsWith(".")) {
                return false;
            }
            if (file.isDirectory()) {
                return true;
            }

            boolean hasAllPathStrings = true;
            String fullPath = dir.getPath().replace('\\', '/');
            for (String pathString: stringsToFindInPath) {
                if (fullPath.indexOf(pathString) < 0) {
                    hasAllPathStrings = false;
                    break;
                }
            }

            if (hasAllPathStrings && name.endsWith("." + fileExtension)) {
                if (stringsToFindInFile.size() == 0) {
                    return true;
                }
                StringBuffer xmlFileBuffer = null;
                try {
                    xmlFileBuffer = FileUtil.readTextFile(file, true);
                } catch (FileNotFoundException e) {
                    Debug.logWarning("Error reading xml file [" + file + "] for file search: " + e.toString(), module);
                    return false;
                } catch (IOException e) {
                    Debug.logWarning("Error reading xml file [" + file + "] for file search: " + e.toString(), module);
                    return false;
                }
                if (UtilValidate.isNotEmpty(xmlFileBuffer)) {
                    boolean hasAllStrings = true;
                    for (String stringToFile: stringsToFindInFile) {
                        if (xmlFileBuffer.indexOf(stringToFile) < 0) {
                            hasAllStrings = false;
                            break;
                        }
                    }
                    return hasAllStrings;
                }
            } else {
                return false;
            }
            return false;
        }
    }

    /**
    *
    *
    * Search for the specified <code>searchString</code> in the given
    * {@link Reader}.
    *
    * @param reader A Reader in which the String will be searched.
    * @param searchString The String to search for
    * @return <code>TRUE</code> if the <code>searchString</code> is found;
    *         <code>FALSE</code> otherwise.
    * @throws IOException
    */
   public static boolean containsString(Reader reader, final String searchString) throws IOException {
       char[] buffer = new char[1024];
       int numCharsRead;
       int count = 0;
       while((numCharsRead = reader.read(buffer)) > 0) {
           for (int c = 0; c < numCharsRead; ++c) {
               if (buffer[c] == searchString.charAt(count)) count++;
               else count = 0;
               if (count == searchString.length()) return true;
           }
       }
       return false;
   }

   /**
    *
    *
    * Search for the specified <code>searchString</code> in the given
    * filename. If the specified file doesn't exist, <code>FALSE</code>
    * returns.
    *
    * @param fileName A full path to a file in which the String will be searched.
    * @param searchString The String to search for
    * @return <code>TRUE</code> if the <code>searchString</code> is found;
    *         <code>FALSE</code> otherwise.
    * @throws IOException
    */
   public static boolean containsString(final String fileName, final String searchString) throws IOException {
       File inFile = new File(fileName);
       if (inFile.exists()) {
           BufferedReader in = new BufferedReader(new FileReader(inFile));
           try {
               return containsString(in, searchString);
           } finally {
               if (in != null)in.close();
           }
       } else {
           return false;
       }
   }

    //      ------------------------------add by changsy ------------------------------------------





    public static boolean delete(String file) {
        return delete(new File(file));
    }

    public static boolean delete(File file) {
        if ((file != null) && file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    public static void deltree(String directory) {
        deltree(new File(directory));
    }

    public static void deltree(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] fileArray = directory.listFiles();

            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].isDirectory()) {
                    deltree(fileArray[i]);
                } else {
                    fileArray[i].delete();
                }
            }

            directory.delete();
        }
    }

    public static boolean exists(String fileName) {
        return exists(new File(fileName));
    }

    public static boolean exists(File file) {
        return file.exists();
    }


    public static String[] listFiles(String fileName) {
        if (fileName==null||fileName.equals("")) {
            return new String[0];
        }

        return listFiles(new File(fileName));
    }

    public static String[] listFiles(File file) {
        List<String> files = new ArrayList<String>();

        File[] fileArray = file.listFiles();

        for (int i = 0; (fileArray != null) && (i < fileArray.length); i++) {
            if (fileArray[i].isFile()) {
                files.add(fileArray[i].getName());
            }
        }

        return files.toArray(new String[files.size()]);
    }

    public static void mkdirs(String pathName) {
        File file = new File(pathName);

        file.mkdirs();
    }

    public static boolean move(String sourceFileName, String destinationFileName) {
        return move(new File(sourceFileName), new File(destinationFileName));
    }

    public static boolean move(File source, File destination) {
        if (!source.exists()) {
            return false;
        }

        destination.delete();

        return source.renameTo(destination);
    }




    public static List<String> toList(Reader reader) {
        List<String> list = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(reader);

            String line = null;

            while ((line = br.readLine()) != null) {
                list.add(line);
            }

            br.close();
        } catch (IOException ioe) {
        }

        return list;
    }

    public static List<String> toList(String fileName) {
        try {
            return toList(new FileReader(fileName));
        } catch (IOException ioe) {
            return new ArrayList<String>();
        }
    }

    public static Properties toProperties(FileInputStream fis) {
        Properties props = new Properties();

        try {
            props.load(fis);
        } catch (IOException ioe) {
        }

        return props;
    }

    public static Properties toProperties(String fileName) {
        try {
            return toProperties(new FileInputStream(fileName));
        } catch (IOException ioe) {
            return new Properties();
        }
    }

    public static String[] listDirs(String fileName) {
        return listDirs(new File(fileName));
    }

    public static String[] listDirs(File file) {
        List<String> dirs = new ArrayList<String>();

        File[] fileArray = file.listFiles();

        for (int i = 0; (fileArray != null) && (i < fileArray.length); i++) {
            if (fileArray[i].isDirectory()) {
                dirs.add(fileArray[i].getName());
            }
        }

        return dirs.toArray(new String[dirs.size()]);
    }


    public static byte[] getBytes(File file)   throws IOException {
        if ((file == null) || !file.exists()) {
            return null;
        }

        FileInputStream is = new FileInputStream(file);

        byte[] bytes = getBytes(is, (int) file.length());

        is.close();

        return bytes;
    }


    public static byte[] getBytes(InputStream is, int bufferSize) throws IOException {
        ByteArrayOutputStream bam = null;

        if (bufferSize <= 0) {
            bam = new ByteArrayOutputStream();
        } else {
            bam = new ByteArrayOutputStream(bufferSize);
        }

        boolean createBuffered = false;

        try {
            if (!(is instanceof BufferedInputStream)) {
                is = new BufferedInputStream(is);

                createBuffered = true;
            }

            int c = is.read();

            while (c != -1) {
                bam.write(c);

                c = is.read();
            }
        } finally {
            if (createBuffered) {
                is.close();
            }
        }

        bam.close();

        return bam.toByteArray();
    }
}
