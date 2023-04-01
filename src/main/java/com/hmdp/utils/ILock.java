package com.hmdp.utils;

public interface ILock {
    /**
     * try to get lock
     *
     * @param timeoutSec
     * @return
     */
    boolean tryLock(long timeoutSec);


    /**
     * unlock
     */
    void unLock();

}
