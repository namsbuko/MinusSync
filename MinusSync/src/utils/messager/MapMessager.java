/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.messager;

import java.util.Map;

/**
 *
 * @author burun
 */
public class MapMessager implements IMessager {

    private final Map<Integer, String> _messages;
    
    public MapMessager(Map<Integer, String> messages) {
        _messages = messages;
    }  
            
    @Override
    public String get(Integer message) {
        String mes = _messages.get(message);
        return mes == null ? "" : mes;
    }
    
}
