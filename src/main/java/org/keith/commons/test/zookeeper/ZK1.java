package org.keith.commons.test.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZK1 {
	private static String connectString = "192.168.204.128:2181";
	private static int sessionTimeout = 999999;

	public static void main(String[] args) throws Exception {
		Watcher watcher = new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("���������¼���" + event);
			}
		};
		final ZooKeeper zookeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
		System.out.println("������ӣ�" + zookeeper);
		final byte[] data = zookeeper.getData("/zk1", watcher, null);
		System.out.println("��ȡ��ֵ��" + new String(data));
		zookeeper.close();
	}
}