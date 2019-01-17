package com.example.demo.utils;

public class Lock {

	Thread thread = null;
	private boolean flag = false;
	private int count = 0;

	public synchronized void lock() {
		while (flag && thread != Thread.currentThread()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread = Thread.currentThread();
		flag = true;
		count++;
	}

	public synchronized void unlock() {
		if (thread == Thread.currentThread()) {
			count--;
			if (count == 0) {
				flag = false;
				this.notify();
			}
		}
	}

}
