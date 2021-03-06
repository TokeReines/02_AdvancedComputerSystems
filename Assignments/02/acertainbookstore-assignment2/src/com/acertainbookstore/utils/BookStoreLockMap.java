package com.acertainbookstore.utils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BookStoreLockMap {
    private final Map<Integer, ReadWriteLock> lockMap = new HashMap<>();
    private static final ReadWriteLock mapLock = new ReentrantReadWriteLock(true);

    public void readLock(List<Integer> isbns) {
        mapLock.writeLock().lock();

        for (Integer isbn : isbns) {
            ReadWriteLock lock = lockMap.get(isbn);
            lock.readLock().lock();
        }
        mapLock.writeLock().unlock();
    }

    public void readUnlock(List<Integer> isbns) {
        for(Integer isbn: isbns) {
            ReadWriteLock lock = lockMap.get(isbn);
            lock.readLock().unlock();
        }
    }

    public void writeLock(List<Integer> isbns) {
        mapLock.writeLock().lock();

        for (Integer isbn : isbns) {
            ReadWriteLock lock = lockMap.get(isbn);
            lock.writeLock().lock();
        }
        mapLock.writeLock().unlock();
    }

    public void writeUnlock(List<Integer> isbns) {
        for(Integer isbn: isbns) {
            ReadWriteLock lock = lockMap.get(isbn);
            lock.writeLock().unlock();
        }
    }

    public void deleteLocks(List<Integer> isbns) {
        mapLock.writeLock().lock();

        for(Integer isbn: isbns) {
            lockMap.remove(isbn);
        }
        mapLock.writeLock().unlock();
    }

    public void deleteAllLocks() {
        mapLock.writeLock().lock();
        lockMap.clear();
        mapLock.writeLock().unlock();
    }

    public void addLocks(List<Integer> isbns) {
        mapLock.writeLock().lock();
        for(Integer isbn: isbns) {
            lockMap.put(isbn, new ReentrantReadWriteLock(true));
        }
        mapLock.writeLock().unlock();
    }
}
