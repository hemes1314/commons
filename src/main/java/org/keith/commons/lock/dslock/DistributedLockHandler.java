package org.keith.commons.lock.dslock;

/**
 * @author wubin
 * @date 2017/11/07
 **/
public interface DistributedLockHandler {

    void doSomeThing(DistributedLock distributedLock);
}
