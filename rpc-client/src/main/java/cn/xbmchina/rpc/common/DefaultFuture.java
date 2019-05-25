package cn.xbmchina.rpc.common;


import cn.xbmchina.rpc.entity.ClientRequest;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultFuture {

    public static Map<Long, DefaultFuture> allFutures = new ConcurrentHashMap<>();
    public final Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Response response;
    private long timeout;
    private long startTime = System.currentTimeMillis();

    public DefaultFuture(ClientRequest request) {
        allFutures.put(request.getId(), this);
    }

    public Response get() {

        lock.lock();
        try {
            while (!done()) {

                condition.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return this.response;
    }

    public Response get(long time) {

        lock.lock();
        try {
            while (!done()) {

                condition.await(time, TimeUnit.MILLISECONDS);
                if ((System.currentTimeMillis() - startTime) > time) {
                    System.out.println("请求超时！");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return this.response;
    }

    public static void receive(Response response) {
        DefaultFuture df = allFutures.get(response.getId());
        if (df != null) {
            Lock lock = df.lock;
            lock.lock();
            try {
                df.setResponse(response);
                df.condition.signal();
                allFutures.remove(df);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }


    private boolean done() {
        if (this.response != null) {
            return true;
        }
        return false;
    }

    static class  FutureThread extends  Thread {

        @Override
        public void run() {
            Set<Long> ids = allFutures.keySet();
            for (Long id: ids) {
                DefaultFuture df = allFutures.get(id);
                if (df == null) {
                    allFutures.remove(df);
                }else {
                    if ((System.currentTimeMillis() - df.getStartTime() > df.getTimeout())) {
                        Response resp = new Response();
                        resp.setId(id);
                        resp.setStatus(504);
                        resp.setResult("链路请求超时!");
                        receive(resp);
                    }
                }
            }
        }
    }

    static  {
        FutureThread futureThread = new FutureThread();
        futureThread.setDaemon(true);
        futureThread.start();
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getStartTime() {
        return startTime;
    }

}
