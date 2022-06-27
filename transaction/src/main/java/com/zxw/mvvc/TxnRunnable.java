package com.zxw.mvvc;

@FunctionalInterface
public interface TxnRunnable {
    void run(Txn txn);
}

