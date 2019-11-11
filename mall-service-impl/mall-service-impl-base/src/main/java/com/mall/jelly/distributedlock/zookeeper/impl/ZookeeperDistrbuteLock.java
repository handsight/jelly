package com.mall.jelly.distributedlock.zookeeper.impl;

import com.mall.jelly.distributedlock.zookeeper.ZookeeperAbstractLock;
import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZookeeperDistrbuteLock extends ZookeeperAbstractLock {
    private CountDownLatch countDownLatch = null;
    @Override
    protected boolean tryLock() {
        try {
            //创建临时节点，创建成功返回true,创建失败就返回false
            zkClient.createEphemeral(PATH);
            return true;
        } catch (Exception e) {
//			e.printStackTrace();
            return false;
        }
    }
    @Override
    protected void waitLock() {
        IZkDataListener izkDataListener = new IZkDataListener() {
            //3有节点被删除
            @Override
            public void handleDataDeleted(String path) throws Exception {
                // 3.1唤醒被等待的线程
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
            //有节点被修改
            @Override
            public void handleDataChange(String path, Object data) throws Exception {
            }
        };
        // 2进行监听事件通知
        zkClient.subscribeDataChanges(PATH, izkDataListener);
        if (zkClient.exists(PATH)) {  //1如果存在节点
            countDownLatch = new CountDownLatch(1); //1.1计数器总数1
            try {
                countDownLatch.await();  //1.2等待
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 4删除事件监听
        zkClient.unsubscribeDataChanges(PATH, izkDataListener);
    }

}
