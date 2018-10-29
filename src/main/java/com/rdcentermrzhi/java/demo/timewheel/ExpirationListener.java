package com.rdcentermrzhi.java.demo.timewheel;

public interface ExpirationListener<E> {  
    
    /** 
     * Invoking when a expired event occurs. 
     *  
     * @param expiredObject 
     */  
    void expired(E expiredObject);  
      
}  
