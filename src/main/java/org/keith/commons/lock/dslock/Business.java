package org.keith.commons.lock.dslock;

/**
 * @author wubin
 * @date 2017/11/07
 **/
public class Business implements DistributedLockHandler {
    @Override
    public void doSomeThing(DistributedLock distributedLock) {
        try{
            System.out.println(distributedLock.LOG_PREFIX_OF_THREAD+"do something...");
            Thread.sleep(2000);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
