package cn.xbmchina.rpc.common;


import cn.xbmchina.rpc.entity.ClientRequest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultFuture {

    public static ConcurrentHashMap<Long, DefaultFuture> allFutures = new ConcurrentHashMap<>();
    public final Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Response response;

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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


}
