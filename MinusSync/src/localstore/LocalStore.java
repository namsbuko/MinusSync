/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package localstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import store.*;

/**
 *
 * @author burun
 */
public class LocalStore implements IStore{

    private static final String DirDel = "/";
    private static final String Sync = ".sync" + DirDel;
    private static final String DatePattern = "dd.MM.yyyy.HH.mm.ss";
    
    private static final String Self = ".self" + DirDel;
    private static final String Id = "id"  + DirDel;
    private static final String DateCreation = "date"  + DirDel;
    private static final String Type = "type"  + DirDel;
    
    private File _root;
    
    private static String getId() {
        // TODO may be bag
        return Long.toHexString(System.currentTimeMillis());
    }
    
    private static boolean makeDir(String path) {
        boolean res = new File(path).mkdir();
        if (!res) {
            Logger.getLogger(LocalStore.class.getName())
                                         .log(Level.SEVERE, "dir don't create");
        }
        return res;        
    }
            
    private static boolean createFile(String path) {
        boolean res = false;
        try {
            File file = new File(path);
            
            if (!file.createNewFile()){
                Logger.getLogger(LocalStore.class.getName())
                                        .log(Level.SEVERE, "file don't create");
            } else {
                res = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(LocalStore.class.getName())
                                             .log(Level.SEVERE, "bad path", ex);
        }
        return res;
    }
    
    private static String readItemProperty(String path, String item, String prop) {
        String res = null;
        File file = new File(path + Sync, item + DirDel + prop);
        if (file.exists()) {
            String[] prs = file.list();
            if (prs.length == 1) {
                res = prs[0];
            } else {
                Logger.getLogger(LocalStore.class.getName())
                                      .log(Level.SEVERE, "too many properties");
            }
        } else {
            Logger.getLogger(LocalStore.class.getName())
                          .log(Level.SEVERE, "folder's property doesn't exist");
        }
        
        return res;
    }
    
    private static IFile readFile(File file, IFolder parent) {
        IFile res = null;
        try {
            String id = readItemProperty(parent.getPath(), file.getName(), Id);
            String type = 
                       readItemProperty(parent.getPath(), file.getName(), Type);
            String date = readItemProperty(parent.getPath(), file.getName(), 
                                           DateCreation);
            if (id != null && type != null && date != null) {
                Date cr = new SimpleDateFormat(DatePattern).parse(date);
                String path = parent.getPath() + file.getName();
                res = new LocalFile(file, id, parent, path, type, cr);
            }
        } catch (ParseException ex) {
            Logger.getLogger(LocalStore.class.getName())
                                             .log(Level.SEVERE, "bad date", ex);
        }
        return res;
    }
    
    private static IFolder readFolder(File file, IFolder parent) {
        IFolder res = null;
        try {
            String id = readItemProperty(parent.getPath(), file.getName(), Id);
            String date = readItemProperty(parent.getPath(), file.getName(), 
                                           DateCreation);
            if (id != null && date != null) {
                Date cr = new SimpleDateFormat(DatePattern).parse(date);
                String path = parent.getPath() + file.getName();
                res = new LocalFolder(file, id, parent, path, cr);
            }
        } catch (ParseException ex) {
            Logger.getLogger(LocalStore.class.getName())
                                             .log(Level.SEVERE, "bad date", ex);
        }
        return res;
    }
    
    private boolean addFileInfo(IFile file) {
        String dir = _root.getAbsolutePath() + file.getParent().getPath()
                     + Sync + file.getName() + DirDel;
        DateFormat df = new SimpleDateFormat(DatePattern);
        String date = df.format(file.getCreationDate());
        return makeDir(dir) && makeDir(dir + Id) 
               && createFile(dir + Id + file.getId())
               && makeDir(dir + DateCreation)
               && createFile(dir + DateCreation + date)
               && makeDir(dir + Type) 
               && createFile(dir + Type + file.getType());    
    }

    private boolean addFolderInfo(IFolder folder) {
        String dir = _root.getAbsolutePath() + folder.getPath() + Sync;
        DateFormat df = new SimpleDateFormat(DatePattern);
        
        return makeDir(dir + Self) && makeDir(dir + Self + Id) 
               && createFile(dir + Self + Id + folder.getId())
               && makeDir(dir + Self + DateCreation)
               && createFile(dir + Self + DateCreation 
                             + df.format(folder.getCreationDate()));   
    }
    
    
    
    // public section 
    
    @Override
    public boolean addFolder(IFolder folder) {
        String dir = _root.getAbsolutePath() + folder.getPath() + Sync;
        
        return makeDir(dir) && addFolderInfo(folder);    
    }

    @Override
    public Collection<IFolder> getFolders(IFolder folder) {
        File dir = new File(_root.getAbsolutePath() + folder.getPath());
        
        List<IFolder> lst = new ArrayList<IFolder>();
        for (File file: dir.listFiles()) {
            if (file.isDirectory()) {
                IFolder f = readFolder(file, folder);
                if (f != null) {
                    lst.add(f);
                }
            }
        }
        
        return lst;
    }

    @Override
    public boolean deleteFolder(IFolder folder) {
        return new File(_root, folder.getPath()).delete();
    }

    @Override
    public boolean deleteFile(IFile file) {
        return new File(_root, file.getPath()).delete();
    }

    @Override
    public OutputStream getFileStream(IFile file) {
        OutputStream res = null;
        try {
            res = new FileOutputStream(new File(_root, file.getPath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LocalStore.class.getName())
                      .log(Level.SEVERE, "file with file's path not found", ex);
        }
        
        return res;
    }

    @Override
    public Collection<IFile> getFiles(IFolder folder) {
        File dir = new File(_root.getAbsolutePath() + folder.getPath());
        
        List<IFile> lst = new ArrayList<IFile>();
        for (File file: dir.listFiles()) {
            if (file.isFile()) {
                IFile f = readFile(file, folder);
                if (f != null) {
                    lst.add(f);
                }
            }
        }
        
        return lst;
    }

    @Override
    public boolean addFile(IFile file, InputStream in) {
        boolean res = false;
        try {
            OutputStreamWriter wr = new FileWriter(new File(file.getPath()));
            int b;
            while ((b = in.read()) != -1) {
                wr.write(b);
            }
            wr.flush();
            res = addFileInfo(file);   
        } catch (IOException ex) {
            Logger.getLogger(LocalStore.class.getName())
                  .log(Level.SEVERE, "can't create File with file's path ", ex);
        }
        
        return res;
    }    
    
    /**
     * 
     * @param file add to store
     * @param parent of file, if file store in root, folder parent == null
     * @return file if add success? otherwise null 
     */
    public IFile addFileToStore(File file, IFolder parent) {
        IFile res = null;
        
        try {
            String type = URLConnection
                         .guessContentTypeFromStream(new FileInputStream(file));
            res = new LocalFile(file, getId(), parent, parent.getPath(), type);
            if (!addFileInfo(res)) res = null;
        } catch (IOException ex) {
            Logger.getLogger(LocalStore.class.getName())
                                             .log(Level.SEVERE, "bad file", ex);
        }        
        
        return res;
    }
      
    /**
     * 
     * @param folder add to store
     * @param parent of folder, if file store in root, parent == null
     * @return if add success folder, otherwise null
     */
    public IFolder addFolderToStore(File folder, IFolder parent) {
        IFolder res = null;
        
        res = new LocalFolder(folder, getId(), parent, 
                              parent.getPath() + folder.getName() + DirDel);
       
        return addFolderInfo(res) ? res : null;
    }
}
