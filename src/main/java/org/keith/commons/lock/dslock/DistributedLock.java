package org.keith.commons.lock.dslock;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import java.util.List;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

/**
 * @author wubin
 * @date 2017/11/07
 **/
public class DistributedLock implements Watcher {
    private long threadId;
    private ZooKeeper zk = null;
    private String selfPath;
    private String waitPath;
    public String LOG_PREFIX_OF_THREAD;
    private int SESSION_TIMEOUT = 10000;
    private String GROUP_PATH = "/disLocks";
    private String SUB_PATH = "/disLocks/sub";
    private final String CONNECTION_STRING = "127.0.0.1:2181";

    private DistributedLockHandler handler;

    //确保连接zk成功；
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public DistributedLock(String lockName) throws IOException, InterruptedException, KeeperException {
        this.threadId = Thread.currentThread().getId();
        LOG_PREFIX_OF_THREAD = "【Thread"+threadId+"】";
        SUB_PATH = GROUP_PATH + "/" + lockName;

        // 建立连接
        createConnection(CONNECTION_STRING, SESSION_TIMEOUT);
        // 建立节点
        //GROUP_PATH不存在的话，由一个线程创建即可；
        synchronized (SUB_PATH){
            createPath(GROUP_PATH, "该节点由线程" + threadId + "创建", true);
        }

        selfPath = zk.create(SUB_PATH,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(LOG_PREFIX_OF_THREAD+"创建锁路径:"+selfPath);
    }

    public void excute() throws KeeperException, InterruptedException {

        lock();

        handler.doSomeThing(this);

        unLock();
    }

    public void lock() throws KeeperException, InterruptedException {
        if(zk.exists(this.selfPath,false) == null){
            System.out.println(LOG_PREFIX_OF_THREAD+"本节点已不在了...");
            return;
        }
        System.out.println(LOG_PREFIX_OF_THREAD + "获取锁成功，赶紧干活！");


    }

    public void unLock() throws KeeperException, InterruptedException {
        System.out.println(LOG_PREFIX_OF_THREAD + "删除本节点："+selfPath);
        zk.delete(this.selfPath, -1);
        releaseConnection();
    }

    public boolean tryLock () throws KeeperException, InterruptedException {
        return checkMinPath();
    }

    /**
     * 检查自己是不是最小的节点
     * @return
     */
    private boolean checkMinPath() throws KeeperException, InterruptedException {
        List<String> subNodes = zk.getChildren(GROUP_PATH, false);
        Collections.sort(subNodes);
        // 获取节点在子节点列表中的位置
        int index = subNodes.indexOf( selfPath.substring(GROUP_PATH.length()+1));
        switch (index){
            case -1:{
                System.out.println(LOG_PREFIX_OF_THREAD+"本节点已不在了..."+selfPath);
                return false;
            }
            case 0:{
                System.out.println(LOG_PREFIX_OF_THREAD+"子节点中，我果然是老大"+selfPath);
                return true;
            }
            default:{
                // 如果不是最小编号则取前一个节点，判断是否已删除，已删除则递归判断是否最小
                // 未删除则返回非最小节点
                this.waitPath = GROUP_PATH +"/"+ subNodes.get(index - 1);
                System.out.println(LOG_PREFIX_OF_THREAD+"获取子节点中，排在我前面的"+waitPath);
                try{
                    zk.getData(waitPath, true, new Stat());
                    return false;
                }catch(KeeperException e){
                    if(zk.exists(waitPath,false) == null){
                        System.out.println(LOG_PREFIX_OF_THREAD+"子节点中，排在我前面的"+waitPath+"已失踪，幸福来得太突然?");
                        return checkMinPath();
                    }else{
                        throw e;
                    }
                }
            }

        }

    }

    @Override
    public void process(WatchedEvent event) {
        if(event == null){
            return;
        }
        Event.KeeperState keeperState = event.getState();
        Event.EventType eventType = event.getType();
        if ( Event.KeeperState.SyncConnected == keeperState) {
            if ( Event.EventType.None == eventType ) {
                System.out.println( LOG_PREFIX_OF_THREAD + "成功连接上ZK服务器" );
                connectedSemaphore.countDown();
            }else if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)) {
                System.out.println(LOG_PREFIX_OF_THREAD + "收到情报，排我前面的家伙已挂，我是不是可以出山了？");
                try {
                    if(tryLock()){
                        excute();
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if ( Event.KeeperState.Disconnected == keeperState ) {
            System.out.println( LOG_PREFIX_OF_THREAD + "与ZK服务器断开连接" );
//        } else if ( Event.KeeperState.AuthFailed == keeperState ) {
//            LOG.info( LOG_PREFIX_OF_THREAD + "权限检查失败" );
        } else if ( Event.KeeperState.Expired == keeperState ) {
            System.out.println( LOG_PREFIX_OF_THREAD + "会话失效" );
        }
    }

    /**
     * 创建ZK连接
     * @param connectString	 ZK服务器地址列表
     * @param sessionTimeout Session超时时间
     */
    public void createConnection( String connectString, int sessionTimeout ) throws IOException, InterruptedException {
        zk = new ZooKeeper( connectString, sessionTimeout, this);
        connectedSemaphore.await();
    }

    /**
     * 关闭ZK连接
     */
    public void releaseConnection() {
        if ( this.zk !=null ) {
            try {
                this.zk.close();
            } catch ( InterruptedException e ) {}
        }
        System.out.println(LOG_PREFIX_OF_THREAD + "释放连接");
    }

    /**
     * 创建节点
     * @param path 节点path
     * @param data 初始数据内容
     * @return
     */
    public boolean createPath( String path, String data, boolean needWatch) throws KeeperException, InterruptedException {
        if(zk.exists(path, needWatch)==null){
            System.out.println( LOG_PREFIX_OF_THREAD + "节点创建成功, Path: "
                    + this.zk.create( path,
                    data.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT )
                    + ", content: " + data );
        }
        return true;
    }

    // CreateMode notes:
    /**
     * 持久节点：节点创建后，会一直存在，不会因客户端会话失效而删除；
     */
//    PERSISTENT (0, false, false),

    /**
     * 持久顺序节点：基本特性与持久节点一致，创建节点的过程中，zookeeper会在其名字后自动追加一个单调增长的数字后缀，作为新的节点名；
     */
//    PERSISTENT_SEQUENTIAL (2, false, true),

    /**
     *  临时节点：客户端会话失效或连接关闭后，该节点会被自动删除，且不能再临时节点下面创建子节点，否则报如下错：org.apache.zookeeper.KeeperException$NoChildrenForEphemeralsException；
     */
//    EPHEMERAL (1, true, false),

    /**
     * 临时顺序节点：基本特性与临时节点一致，创建节点的过程中，zookeeper会在其名字后自动追加一个单调增长的数字后缀，作为新的节点名；
     */
//    EPHEMERAL_SEQUENTIAL (3, true, true);


    public int getSESSION_TIMEOUT() {
        return SESSION_TIMEOUT;
    }

    public void setSESSION_TIMEOUT(int SESSION_TIMEOUT) {
        this.SESSION_TIMEOUT = SESSION_TIMEOUT;
    }

    public String getGROUP_PATH() {
        return GROUP_PATH;
    }

    public void setGROUP_PATH(String GROUP_PATH) {
        this.GROUP_PATH = GROUP_PATH;
    }

    public String getSUB_PATH() {
        return SUB_PATH;
    }

    public void setSUB_PATH(String SUB_PATH) {
        this.SUB_PATH = SUB_PATH;
    }

    public String getCONNECTION_STRING() {
        return CONNECTION_STRING;
    }

    public DistributedLockHandler getHandler() {
        return handler;
    }

    public void setHandler(DistributedLockHandler handler) {
        this.handler = handler;
    }
}
