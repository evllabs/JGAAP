package com.jgaap.backend;

import java.util.LinkedList;
/**
 * 
 * Work is queued up and workers being as it comes in
 * 
 * @author Michael Ryan
 * @since 5.0.1
 */
@SuppressWarnings("rawtypes")
public final class WorkQueue {

	private final int nThreads;
	private final PoolWorker[] threads;
	private final LinkedList queue;

	public WorkQueue(int nThreads) {
		this.nThreads = nThreads;
		queue = new LinkedList();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	@SuppressWarnings("unchecked")
	public void execute(Object r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notifyAll();
		}
	}

	public boolean isRunning() {
		for (int i = 0; i < nThreads; i++) {
			if (threads[i].isAlive())
				return true;
		}
		return false;
	}

	private class PoolWorker extends Thread {
		public void run() {
			Runnable r;

			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}
					Object o = queue.removeFirst();
					if (o instanceof Runnable)
						r = (Runnable) o;
					else {
						break;
					}
				}

				// If we don't catch RuntimeException,
				// the pool could leak threads
				try {
					r.run();
				} catch (RuntimeException e) {
					// You might want to log something here
				}
			}
		}
	}

}
