/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package minusstore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.Messager;
import minusapi.MinusFile;
import store.IFolder;
import utils.messager.*;

/**
 *
 * @author Admin
 */
public class MinusStoreFile implements store.IFile{

    private static final String root = "/";
    //!
    private static final String DatePattern = "dd.MM.yyyy.HH.mm.ss";

    private final minusapi.MinusFile _thisFile;
    private String _Id;
    private IFolder _parent;
    public MinusStoreFile (MinusFile file, String id, IFolder parent)
    {
        _thisFile = file;
        _Id = id;
        _parent = parent;
    }

    @Override
    public IFolder getParent() {
        return _parent;
    }

    @Override
    public String getId() {
        return _Id;
        
    }

    @Override
    public String getName() {
        return _thisFile.getName();
        
    }

    @Override
    public String getPath() {
        return null == _parent
                ? root
                : _parent.getPath();
    }

    @Override
    public Date getCreationDate() {
        try {
            return new SimpleDateFormat(DatePattern).parse(_thisFile.getUploaded());
            //"uploaded": "2011-09-09 14:28:52",
        } catch (ParseException ex) {
            Logger.getLogger(MinusStoreFile.class.getName()).log(Level.SEVERE,
                            utils.messager.Messager.getMessager(MinusStoreFile.class.getName()).get(Integer.SIZE), ex);
            return null;
        }
    }

    @Override
    public long getCreationAgo() {
        Calendar creationTime = Calendar.getInstance();
        creationTime.setTime(getCreationDate());
        return System.currentTimeMillis() - creationTime.getTimeInMillis();
    }

     @Override
    public Long getLastChange() {
        return getCreationAgo();
    }

    @Override
    public String getType() {
       return _thisFile.getMimeType();
    }

    @Override
    public int getSize() {
        return _thisFile.getSize();
    }




}
