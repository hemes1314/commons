package org.keith.commons.wms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class OrderLocateManager {

	// 并行执行定位的线程数
	private int THREAD_COUNT = 20;

	// 待处理的订单列表
	/*
	 * 队列：Queue接口与List、Set同一级别，都是继承了Collection接口
	 * 队列是一种特殊的线性表，它只允许在表的前端进行删除操作，而在表的后端进行插入操作 LinkedList实现了Queue接口
	 * Queue接口窄化了对LinkedList的方法的访问权限（即在方法中的参数类型如果是Queue时，就完全只能访问Queue接口所定义的方法
	 * 了，而不能直接访问 LinkedList的非Queue的方法）
	 */
	private final Queue<RichOrderCustomer> richOrderCustomerQueue;

	// 当前用户
	final User user;

	// 并发处理的SKU Map
	/*
	 * ConcurrentHashMap融合了HashTable和HashMap二者的优势。 HashTable是做了同步的，HashMap未考虑同步。
	 * 所以HashMap在单线程情况下效率较高。HashTable在的多线程情况下，同步操作能保证程序执行的正确性。
	 * 但是HashTable每次同步执行的时候都要锁住整个结构 ConcurrentHashMap锁的方式是稍微细粒度的。
	 * ConcurrentHashMap将hash表分为16个桶（默认值），诸如get,put,remove等常用操作只锁当前需要用到的桶。
	 * 原来只能一个线程进入，现在却能同时16个写线程进入（写线程才需要锁定，而读线程几乎不受限制），并发性的提升是显而易见的。
	 */
	/*
	 * newSetFromMap举例： Set is: [A, B, C] Map is: {A=true, B=true, C=true}
	 */
	private Set<String> locatingSkuSet = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

	public OrderLocateManager(List<RichOrderCustomer> richOrderCustomerList) { 
		this.richOrderCustomerQueue = new LinkedList<>(richOrderCustomerList);
		user = null;//WmsUserContextHolder.getUser(); 
	}

	public void init() { 
		// 商品ID集合 
		List<String> goodsIdList = new LinkedList<>(); 
		Set<String> goodsIdSet = new HashSet<>(); 
	
		// 双重循环,获取全部订单下的全部商品ID集合 
		for (RichOrderCustomer richOrder : richOrderCustomerQueue) { 
			for (OrderCustomerDetail detail : richOrder.getOrderCustomerDetails()) { 
				goodsIdSet.add(detail.getGoodsId()); 
			} 
		} 
		
		goodsIdList.addAll(goodsIdSet); 

		// 获取全部商品信息列表 
		List<WbGoods> wbGoodsList = new ArrayList<WbGoods>();//wbgoodsfacade.selectByGoodsIds(goodsIdList, user); 
		Map<String, WbGoods> wbGoodsMap = new HashMap<>();
		// 将List转成Map 
		for (WbGoods wbGoods : wbGoodsList) { 
			wbGoodsMap.put(wbGoods.getGoodsId(), wbGoods); 
		} 

		// 获取全部商品储区信息列表 
		List<WbGoodsStorage> wbGoodsStorageList = new ArrayList<>();//wbGoodsStorageFacade.selectByGoodsIds(goodsIdList, user); 
		Map<String, LinkedHashMap<String, WbGoodsStorage>> wbGoodsStorageMap = new HashMap<>();
		// 将List转成Map 
		for (WbGoodsStorage wbGoodsStorage : wbGoodsStorageList) { 
			if (!wbGoodsStorageMap.containsKey(wbGoodsStorage.getGoodsId())) { 
				wbGoodsStorageMap.put(wbGoodsStorage.getGoodsId(),new LinkedHashMap<String, WbGoodsStorage>()); 
			} 
			Map<String, WbGoodsStorage> map = wbGoodsStorageMap.get(wbGoodsStorage.getGoodsId()); 
			map.put(wbGoodsStorage.getStorageType(), wbGoodsStorage); 
		} 

		// 拣选分区数量不多,直接全部查出放入内存 
		List<Object> wbPartitionStorageList = new ArrayList<>();//wbPartitionStorageFacade.selectAll(user); 
	}

	public List<OrderCustomer> doLocate() throws Exception { 
		/* 
		* ListenableFuture来自Google的Guava扩展包 
		* 传统JDK中的Future通过异步的方式计算返回结果:在多线程运算中可能或者可能在没有结束返回结果 
		* ListenableFuture继承自Future，允许我们添加回调函数在线程运算完成时返回值或者方法执行完成立即返回。 
		*/ 
		final List<ListenableFuture<OrderCustomer>> futures = new ArrayList<>(richOrderCustomerQueue.size()); 

		/* 
		* Executors.newFixedThreadPool(int)创建固定大小的线程池ExecutorService 
		*/ 
		/* 
		* 对应JDK中的 ExecutorService.submit(Callable) 提交多线程异步运算的方式，Guava提供了ListeningExecutorService接口, 
		* 该接口返回 ListenableFuture而相应的 ExecutorService返回普通的 Future。 
		* 将 ExecutorService 转为 ListeningExecutorService，可以使用MoreExecutors.listeningDecorator(ExecutorService)进行装饰。 
		*/ 
		ListeningExecutorService exec = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREAD_COUNT));

		try { 
			// list如果有值就执行 
			/* 
			* Queue方法 
			* poll获取并移除此列表的头（第一个元素），相当于先get然后再remove掉，就是查看的同时，也将这个元素从容器中删除掉。 
			*/ 
			for (RichOrderCustomer richOrderCustomer = richOrderCustomerQueue.poll(); richOrderCustomer != null; richOrderCustomer = richOrderCustomerQueue.poll()) { 
				// 判断正在运行的订单内部是否有SKU冲突,不冲突才执行定位 
				if (addOrderInLocatingList(richOrderCustomer)) {//!!!!!!!!!!!!!!!!!!!!!!!! 
			
					// 执行多线程 
					/* 
					* 对应JDK中的 ExecutorService.submit(Callable)提交多线程异步运算的方式 
					* Guava提供了ListeningExecutorService.submit(Callable)执行多线程 
					*/ 
					ListenableFuture<OrderCustomer> future = exec.submit(new OrderLocateProcessor(richOrderCustomer, this)); 
				
					futures.add(future); 
				} else { 
					// 如果有SKU冲突,不执行定位,尾部加回list等候下次执行 
					/* 
					* Queue方法 
					* add当属于List 
					* offer当属于queue 
					*/ 
					richOrderCustomerQueue.offer(richOrderCustomer); 
					/* 
					* 该方法与sleep()类似，但不能由用户指定暂停多长时间 
					* yield()方法让同优先级的线程有执行的机会，当前线程从Running状态退到Runnable状态 
					*/ 
					Thread.yield(); 																
				} 
			} 
			List<OrderCustomer> result = new ArrayList<>(futures.size()); 
			for (ListenableFuture<OrderCustomer> each : futures) { 
				if (each.get() != null) { 
					result.add(each.get()); 
				} 
			} 
			return result; 
		} catch (Exception e) { 
			// TODO: 记录日志 
			//log.error("locate order error", e); 
			e.printStackTrace(); 
//			throw new WMSException(e);
		} finally { 
			exec.shutdown(); 
		}
		return null; 
	}

	/** 
	* 将SKU放入正在定位的列表中 
	* 
	* @param richOrderCustomer 
	* 订单信息 
	* @return true:放入成功, false:放入失败 
	*/ 
	public boolean addOrderInLocatingList(RichOrderCustomer richOrderCustomer) { 
		Set<String> addedGoodsIds = new HashSet<>(); 
	
		for (OrderCustomerDetail newDetail : richOrderCustomer.getOrderCustomerDetails()) { 
	
			if (addedGoodsIds.contains(newDetail.getGoodsId())) { 
				continue; 
			} 
		
			if (!locatingSkuSet.add(newDetail.getGoodsId())) { 
				locatingSkuSet.removeAll(addedGoodsIds); 
				return false; 
			} 
			addedGoodsIds.add(newDetail.getGoodsId()); 
		} 
		return true; 
	}

	/** 
	* 清除正在处理的SKU信息 
	* 
	* @param richOrderCustomer 
	* 订单信息 
	*/ 
	public void removeOrderInLocatingList(RichOrderCustomer richOrderCustomer) { 
		locatingSkuSet.removeAll(Collections2.transform( 
			richOrderCustomer.getOrderCustomerDetails(), 
			new Function<OrderCustomerDetail, String>() { 
				@Override 
				public String apply(final OrderCustomerDetail input) { 
					return input.getGoodsId(); 
				}
			}
		)); 
	}
}

/*
 * Callable接口类似于Runnable，从名字就可以看出来了，
 * 但是Runnable不会返回结果，并且无法抛出返回结果的异常，而Callable功能更强大一些，被线程执行后，可以返回值，
 * 这个返回值可以被Future拿到，也就是说，Future可以拿到异步执行任务的返回值
 */
class OrderLocateProcessor implements Callable<OrderCustomer> {

	@Autowired
    private DataSourceTransactionManager transactionManager;
	
	private final OrderLocateManager orderLocateManager;

//	private Log log = LogFactory.getLog(getClass());

	private RichOrderCustomer richOrderCustomer;

	private User user;

	public OrderLocateProcessor(RichOrderCustomer richOrderCustomer, OrderLocateManager orderLocateManager) {

		this.richOrderCustomer = richOrderCustomer;
		this.user = orderLocateManager.user;
		this.orderLocateManager = orderLocateManager;
	}

	/*
	 * Callable规定的方法是call(),Runnable规定的方法是run()
	 * Callable使用submit()方法加入线程池运行,Runnable使用ExecutorService的execute()方法
	 * Callable的任务执行后可返回值,而Runnable的任务是不能返回值(是void) call()方法可以抛出异常，run()方法不可以
	 * 运行Callable任务可以拿到一个Future对象，表示异步计算的结果。它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。
	 * 通过Future对象可以了解任务执行情况，可取消任务的执行，还可获取执行结果。
	 * ListenableFuture继承自Future，我们添加回调函数在线程运算完成时返回值或者方法执行完成【立即】返回。
	 */
	public OrderCustomer call() throws Exception { 
		// 当前线程数据源存放类 
//		DataSourceContextHolder.setDataSourceName("shardingDatabase"); 
	
		// 获得事务状态 
		DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
		TransactionStatus status = transactionManager.getTransaction(def); 
	
		try { 
			// 在多线程中装载sharding需要的对象参数User 
//			WmsUserContextHolder.setUser(user); 
	
			// 执行定位 
			OrderCustomer orderCustomer = null;//locateOneOrder(richOrderCustomer); 
		
			// 事务提交 
			transactionManager.commit(status); 
		
			return orderCustomer; 
		} catch (Throwable t) { 
//			log.error("execute locate order error", t); 
			t.printStackTrace(); 
			// 事务回滚 
			transactionManager.rollback(status); 
//			throw new WMSException(t); 
		} finally { 
			// 成功执行完一个订单,将订单从队列中移除 
			orderLocateManager.removeOrderInLocatingList(richOrderCustomer); 
			try { 
				// 清空sharding相关对象参数User 
				//WmsUserContextHolder.clearUser(); 
			} catch (Exception ignored) { 
			} 
		}
		return null; 
	}
}
