package org.keith.commons.memory.size;

import java.lang.instrument.Instrumentation;

public class ObjectSize {
	private static volatile Instrumentation instru;
	 
    public static void premain(String args, Instrumentation inst) {
        instru = inst;
    }
 
    /**
     * get object byte size
     * @param object
     * @return
     */
    public static Long getSizeOf(Object object) {
        if (instru == null) {
            throw new IllegalStateException("Instrumentation is null");
        }
        return instru.getObjectSize(object);
    }
}
