/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.messager;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author burun
 */
public class Messager {
    
    static private Map<String, IMessager> _messagers = 
                                               new HashMap<String, IMessager>();
    
    static private final IMessager _default = new DefaultMessager();
    
    static IMessager getMessager(String className) {
        IMessager m = _messagers.get(className);
        return m == null ? _default : m;
    }
    
    static void setMessager(String className, IMessager messager) {
        _messagers.put(className, messager);
    }
}
