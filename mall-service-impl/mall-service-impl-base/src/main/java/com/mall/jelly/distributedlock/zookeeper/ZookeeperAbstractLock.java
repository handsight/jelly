package com.mall.jelly.distributedlock.zookeeper;

import org.I0Itec.zkclient.ZkClient;

public abstract class ZookeeperAbstractLock implements Lock {
    // 创建zk连接
    private static final String CONNECTSTRING = "127.0.0.1:2181";
    protected ZkClient zkClient = new ZkClient(CONNECTSTRING,100,100);
    protected static final String PATH = "/lock";

    // 获取锁资源 子类实现  如果获取锁成功，返回true,否则返回false
    protected abstract boolean tryLock();
    // 如果节点创建失败，进行等待，
    protected abstract void waitLock();


    @Override
    public void getLock() {
        //连接zkclient,在zk上创建一个lock节点，节点类型为临时节点
        //如果节点创建成功，直接执行业务逻辑，如果节点创建失败，等待
        if (tryLock()) {
            System.out.println("##获取lock锁的资源####");
        } else {
            // 进行等待
            //使用事件通知监听该节点是否被删除，如果删除的话重新进入获取锁的资源
            waitLock();
            // 重新获取锁资源
            getLock();
        }
    }
    @Override
    public void unLock() {
        if (zkClient != null) {
            zkClient.close();
            System.out.println("释放锁资源...");
        }
    }

}
