/**
 * Source: https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
 */
package nl.windesheim.codeparser.resources.singleton;

public class ThreadSafeSingleton {

    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton(){}

    public static synchronized ThreadSafeSingleton getInstance(){
        if(instance == null){
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }

}
